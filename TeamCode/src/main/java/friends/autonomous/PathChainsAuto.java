package friends.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Optional;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
import friends.helper.Count;
import friends.helper.MotorControl.PIDFController;
import friends.helper.MotorControl.ViperPIDFConstants;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

/**
 * This is an example auto that showcases movement and control of two servos autonomously.
 * It is a 0+4 (Specimen + Sample) bucket auto. It scores a neutral preload and then pickups 3 samples from the ground and scores them before parking.
 * There are examples of different ways to build paths.
 * A path progression method has been created and can advance based on time, position, or other factors.
 *
 * @author Baron Henderson - 20077 The Indubitables
 * @version 2.0, 11/28/2024
 */

@Autonomous(name = "AA CHAINS Auto", group = "Auto")
public class PathChainsAuto extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;

    private final Pose startPose = new Pose(8.503, 65.04, Math.toRadians(0));

    private Path scoreInitial, pickupOne, scoreOne, pickupTwo, scoreTwo, pickupThree, scoreThree;
    private PathChain sweeps;


    /** Build the paths for the auto (adds, for example, constant/linear headings while doing paths)
     * It is necessary to do this so that all the paths are built before the auto starts. **/
    public void buildPaths() {

        /* There are two major types of paths components: BezierCurves and BezierLines.
         *    * BezierCurves are curved, and require >= 3 points. There are the start and end points, and the control points.
         *    - Control points manipulate the curve between the start and end points.
         *    - A good visualizer for this is [this](https://pedro-path-generator.vercel.app/).
         *    * BezierLines are straight, and require 2 points. There are the start and end points.
         * Paths have can have heading interpolation: Constant, Linear, or Tangential
         *    * Linear heading interpolation:
         *    - Pedro will slowly change the heading of the robot from the startHeading to the endHeading over the course of the entire path.
         *    * Constant Heading Interpolation:
         *    - Pedro will maintain one heading throughout the entire path.
         *    * Tangential Heading Interpolation:
         *    - Pedro will follows the angle of the path such that the robot is always driving forward when it follows the path.
         * PathChains hold Path(s) within it and are able to hold their end point, meaning that they will holdPoint until another path is followed.
         * Here is a explanation of the difference between Paths and PathChains <https://pedropathing.com/commonissues/pathtopathchain.html> */

        /* This is our scorePreload path. We are using a BezierLine, which is a straight line. */
        scoreInitial = new Path(
                new BezierLine(
                        new Point(8.503, 64.040, Point.CARTESIAN),
                        new Point(38.50, 73.000, Point.CARTESIAN)
                )
        );
        scoreInitial.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

        PathBuilder builder = new PathBuilder();

        builder.addPath(
                        new BezierCurve(
                                new Point(38.50, 73.000, Point.CARTESIAN),
                                new Point(4.161, 19.538, Point.CARTESIAN),
                                new Point(62.774, 42.513, Point.CARTESIAN),
                                new Point(57.348, 27.125, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90));

        builder.addPath(
                        new BezierLine(
                                new Point(57.348, 27.125, Point.CARTESIAN),
                                new Point(21.528, 26.412, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90));

        builder.addPath(
                        new BezierCurve(
                                new Point(21.528, 26.412, Point.CARTESIAN),
                                new Point(68.450, 30.584, Point.CARTESIAN),
                                new Point(53.797, 14.382, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90));

        builder.addPath(
                        new BezierLine(
                                new Point(53.797, 14.382, Point.CARTESIAN),
                                new Point(22.000, 14.111, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90));

        builder.addPath(
                        new BezierCurve(
                                new Point(22.000, 14.111, Point.CARTESIAN),
                                new Point(66.935, 17.910, Point.CARTESIAN),
                                new Point(54.399, 9.831, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90));

        builder.addPath(
                        new BezierLine(
                                new Point(54.399, 9.831, Point.CARTESIAN),
                                new Point(22.000, 9.800, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(90));

        builder.addPath(
                new BezierCurve(
                        new Point(22.000, 9.800, Point.CARTESIAN),
                        new Point(16.931, 31.130, Point.CARTESIAN),
                        new Point(8.000, 31.000, Point.CARTESIAN)
                )
        ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0));

        sweeps = builder.build();

        scoreOne = new Path(new BezierCurve(
                new Point(8.000, 31.000, Point.CARTESIAN),
                new Point(38.75, 72.000, Point.CARTESIAN)
        ));
        scoreOne.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

        pickupTwo = new Path(new BezierCurve(
                new Point(38.75, 72.000, Point.CARTESIAN),
                new Point(7.800, 31.000, Point.CARTESIAN)
        ));
        pickupTwo.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

        scoreTwo = new Path(new BezierCurve(
                new Point(7.800, 31.000, Point.CARTESIAN),
                new Point(38.850, 71.000, Point.CARTESIAN)
        ));
        scoreTwo.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

        pickupThree = new Path(new BezierCurve(
                new Point(38.850, 71.000, Point.CARTESIAN),
                new Point(7.8000, 31.000, Point.CARTESIAN)
        ));
        pickupThree.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        scoreThree = new Path(new BezierCurve(
                new Point(7.500, 31.000, Point.CARTESIAN),
                new Point(38.950, 70.000, Point.CARTESIAN)
        ));
        scoreThree.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
    }

    /** This switch is called continuously and runs the pathing, at certain points, it triggers the action state.
     * Everytime the switch changes case, it will reset the timer. (This is because of the setPathState() method)
     * The followPath() function sets the follower to run the specific path, but does NOT wait for it to finish before moving on. */
    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(scoreInitial);
                arm.readyToScore();
                setPathState(1);
                break;
            case 1:
                if (pathTimer.getElapsedTimeSeconds() > 1.5) {
                    arm.score();
                }

                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 1.8) {
                    follower.followPath(sweeps, true);
                    arm.readyToWall();
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    arm.readyToWall();
                    setPathState(3);
                }
                break;
            case 3:
                arm.closeClaw();

                if (pathTimer.getElapsedTimeSeconds() > 0.2){
                    arm.fromWall();
                }

                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 0.3) {
                    follower.followPath(scoreOne, true);
                    arm.readyToScore();
                    arm.looseClaw();
                    setPathState(4);
                }
                break;

            case 4:
                if(pathTimer.getElapsedTimeSeconds() > 2 && pathTimer.getElapsedTimeSeconds() < 2.2) {
                    arm.closeClaw();
                }

                if(pathTimer.getElapsedTimeSeconds() > 2.2) {
                    arm.score();
                }

                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 2.8) {
                    follower.followPath(pickupTwo);
                    setPathState(5);
                }
                break;

            case 5:
                if(pathTimer.getElapsedTimeSeconds() < 1) {
                    arm.readyToWall();
                }
                if(pathTimer.getElapsedTimeSeconds() > 1.3) {
                    arm.closeClaw();
                }
                if(pathTimer.getElapsedTimeSeconds() > 1.7) {
                    arm.fromWall();
                }

                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 3) {
                    follower.followPath(scoreTwo, true);
                    arm.readyToScore();
                    arm.looseClaw();
                    setPathState(6);
                }
                break;

            case 6:
                if(pathTimer.getElapsedTimeSeconds() > 2 && pathTimer.getElapsedTimeSeconds() < 2.5) {
                    arm.closeClaw();
                }

                if(pathTimer.getElapsedTimeSeconds() > 2.5) {
                    arm.score();
                }

                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 2.8) {
                    follower.followPath(pickupThree);
                    setPathState(7);
                }
                break;

            case 7:
                if(pathTimer.getElapsedTimeSeconds() < 1.5) {
                    arm.readyToWall();
                }
                if(pathTimer.getElapsedTimeSeconds() > 2) {
                    arm.closeClaw();
                }
                if(pathTimer.getElapsedTimeSeconds() > 2.5) {
                    arm.fromWall();
                }

                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 3) {
                    follower.followPath(scoreThree, true);
                    arm.readyToScore();
                    arm.looseClaw();
                    setPathState(8);
                }
                break;

            case 8:
                if(pathTimer.getElapsedTimeSeconds() > 2 && pathTimer.getElapsedTimeSeconds() > 2.5) {
                    arm.closeClaw();
                }

                if(pathTimer.getElapsedTimeSeconds() > 2.5) {
                    arm.score();
                }

                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 2.8) {
                    setPathState(-1);
                }
                break;
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {

        // These loop the movements of the robot
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();

        double power = viperpidf.PIDControl(map.RightViperMotor.getCurrentPosition(), (int)target.value);
        map.LeftViperMotor.setPower(power);
        map.RightViperMotor.setPower(power);

        // Hold in the intake
        map.DrawerSlideMotor.setTargetPosition(0);

        if(map.RightViperMotor.getCurrentPosition() > 865){
            arm.openClaw();
        }

    }

    private HardwareMap map;
    private Arm arm;
    private Count target = new Count();
    private PIDFController viperpidf = new PIDFController(ViperPIDFConstants.KP, ViperPIDFConstants.KI, ViperPIDFConstants.KD, ViperPIDFConstants.KF);

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();

        map = new HardwareMap(hardwareMap);
        arm = new Arm(map, Optional.of(target));
        arm.closeClaw();

        map.DrawerSlideMotor.setTargetPosition(0);
        map.DrawerSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        map.DrawerSlideMotor.setPower(0.1);
    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {
    }
}
