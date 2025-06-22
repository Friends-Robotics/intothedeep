package friends.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
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

@Autonomous(name = "AAA Stupid Auto", group = "Examples")
public class ExampleBasicAuto extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    /** This is the variable where we store the state of our auto.
     * It is used by the pathUpdate method. */
    private int pathState;

    /* Create and Define Poses + Paths
     * Poses are built with three constructors: x, y, and heading (in Radians).
     * Pedro uses 0 - 144 for x and y, with 0, 0 being on the bottom left.
     * (For Into the Deep, this would be Blue Observation Zone (0,0) to Red Observation Zone (144,144).)
     * Even though Pedro uses a different coordinate system than RR, you can convert any roadrunner pose by adding +72 both the x and y.
     * This visualizer is very easy to use to find and create paths/pathchains/poses: <https://pedro-path-generator.vercel.app/>
     * Lets assume our robot is 18 by 18 inches
     * Lets assume the Robot is facing the human player and we want to score in the bucket */

    /** Start Pose of our robot */
    private final Pose startPose = new Pose(8.7, 65.1, Math.toRadians(0));

    private Path scoreInitial, pickupOne;
    private PathChain setupOne, sweepOne, setupTwo, sweepTwo, setupThree, sweepThree;

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
                        new Point(38.50, 72.000, Point.CARTESIAN)
                )
        );
        scoreInitial.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

        /* Here is an example for Constant Interpolation
        scorePreload.setConstantInterpolation(startPose.getHeading()); */

        /* This is our scorePickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */

        setupOne = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(38.500, 72.000, Point.CARTESIAN),
                                new Point(17.367, 15.015, Point.CARTESIAN),
                                new Point(76.161, 43.417, Point.CARTESIAN),
                                new Point(68.382, 24.965, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();

        sweepOne = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(68.382, 24.965, Point.CARTESIAN),
                                new Point(21.528, 26.412, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();

        setupTwo = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(21.528, 26.412, Point.CARTESIAN),
                                new Point(77.427, 27.497, Point.CARTESIAN),
                                new Point(56.261, 14.291, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();

        sweepTwo = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(56.261, 14.291, Point.CARTESIAN),
                                new Point(22.000, 14.111, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();

        setupThree = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(22.000, 14.111, Point.CARTESIAN),
                                new Point(71.276, 16.824, Point.CARTESIAN),
                                new Point(57.889, 9.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();

        sweepThree = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(57.889, 9.500, Point.CARTESIAN),
                                new Point(22.000, 9.500, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();



        /* This is our park path. We are using a BezierCurve with 3 points, which is a curved line that is curved based off of the control point */
        pickupOne = new Path(
                new BezierCurve(
                        new Point(22.000, 9.500, Point.CARTESIAN),
                        new Point(45.045, 32.744, Point.CARTESIAN),
                        new Point(9.000, 31.000, Point.CARTESIAN)));

        pickupOne.setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0));
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

                /* You could check for
                - Follower State: "if(!follower.isBusy() {}"
                - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
                - Robot Position: "if(follower.getPose().getX() > 36) {}"
                */

                if(pathTimer.getElapsedTimeSeconds() > 1.5) {
                    arm.score();
                }

                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 2) {
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(setupOne,true);
                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy()) {
                    follower.followPath(sweepOne,true);
                    setPathState(3);
                }
                break;
            case 3:
                if(!follower.isBusy()) {
                    follower.followPath(setupTwo,true);
                    setPathState(4);
                }
                break;
            case 4:
                if(!follower.isBusy()) {
                    follower.followPath(sweepTwo,true);
                    setPathState(5);
                }
                break;
            case 5:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    follower.followPath(setupThree,true);
                    setPathState(6);
                }
                break;
            case 6:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup3Pose's position */
                if(!follower.isBusy()) {
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(sweepThree, true);
                    setPathState(7);
                }
                break;
            case 7:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are parked */
                    follower.followPath(pickupOne,true);
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