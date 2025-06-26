package friends.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.DashboardPoseTracker;
import com.pedropathing.util.Drawing;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Optional;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
import friends.helper.Count;
import friends.helper.MotorControl.PIDFController;
import friends.helper.MotorControl.ViperPIDFConstants;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "4 Specimen", group = "Comp")
public class FourSpecimen extends OpMode {
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;

    private final Pose startPose = new Pose(8.503, 65.040, Math.toRadians(0));

    private Path scoreInitial, scoreOne, pickupTwo, scoreTwo, pickupThree, scoreThree;
    private PathChain sweeps;

    public void buildPaths() {
        scoreInitial = new Path(
            new BezierLine(
                    new Point(8.503, 64.040, Point.CARTESIAN),
                    new Point(38.000, 78.000, Point.CARTESIAN)
            )
        );
        scoreInitial.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

        PathBuilder builder = new PathBuilder();

        builder.addPath(
            new BezierCurve(
                    new Point(38.000, 78.000, Point.CARTESIAN),
                    new Point(4.161, 19.538, Point.CARTESIAN),
                    new Point(32.925, 48.844, Point.CARTESIAN),
                    new Point(56.080, 26.231, Point.CARTESIAN)
            )
        )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90));

        builder.addPath(
            new BezierLine(
                    new Point(68.020, 26.231, Point.CARTESIAN),
                    new Point(22.432, 26.231, Point.CARTESIAN)
            )
        )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90));

        builder.addPath(
            new BezierCurve(
                    new Point(22.432, 26.231, Point.CARTESIAN),
                    new Point(65.126, 30.754, Point.CARTESIAN),
                    new Point(54.000, 14.382, Point.CARTESIAN)
            )
        )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90));

        builder.addPath(
            new BezierLine(
                    new Point(54.000, 14.382, Point.CARTESIAN),
                    new Point(22.000, 14.111, Point.CARTESIAN)
            )
        )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90));

        builder.addPath(
            new BezierCurve(
                    new Point(22.000, 14.111, Point.CARTESIAN),
                    new Point(66.935, 17.910, Point.CARTESIAN),
                    new Point(54.000, 9.400, Point.CARTESIAN)
            )
        )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90));

        builder.addPath(
            new BezierLine(
                    new Point(54.000, 9.400, Point.CARTESIAN),
                    new Point(22.000, 9.400, Point.CARTESIAN)
            )
        )
            .setConstantHeadingInterpolation(Math.toRadians(90));

        builder.addPath(
            new BezierCurve(
                    new Point(22.000, 9.400, Point.CARTESIAN),
                    new Point(16.931, 31.130, Point.CARTESIAN),
                    new Point(8.500, 35.000, Point.CARTESIAN)
            )
        ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0));

        sweeps = builder.build();

        scoreOne = new Path(new BezierCurve(
                new Point(8.500, 35.000, Point.CARTESIAN),
                new Point(9.226, 64.221, Point.CARTESIAN),
                new Point(38.000, 76.500, Point.CARTESIAN)
        ));
        scoreOne.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

        pickupTwo = new Path(new BezierCurve(
                new Point(38.000, 76.500, Point.CARTESIAN),
                new Point(8.503, 63.678, Point.CARTESIAN),
                new Point(8.500, 35.000, Point.CARTESIAN)
        ));
        pickupTwo.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

        scoreTwo = new Path(new BezierCurve(
                new Point(8.500, 35.000, Point.CARTESIAN),
                new Point(8.864, 65.487, Point.CARTESIAN),
                new Point(38.000, 75.000, Point.CARTESIAN)
        ));
        scoreTwo.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

        pickupThree = new Path(new BezierCurve(
                new Point(38.000, 75.000, Point.CARTESIAN),
                new Point(9.045, 62.412, Point.CARTESIAN),
                new Point(8.500, 35.000, Point.CARTESIAN)
        ));
        pickupThree.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        scoreThree = new Path(new BezierCurve(
                new Point(8.500, 35.000, Point.CARTESIAN),
                new Point(8.503, 62.955, Point.CARTESIAN),
                new Point(38.000, 73.500, Point.CARTESIAN)
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

                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 2) {
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

                if (pathTimer.getElapsedTimeSeconds() > 0.2) {
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
                if(pathTimer.getElapsedTimeSeconds() > 2 && pathTimer.getElapsedTimeSeconds() < 2.5) {
                    arm.closeClaw();
                }

                if(pathTimer.getElapsedTimeSeconds() > 2.5) {
                    arm.score();
                }

                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 2.8) {
                    follower.followPath(pickupTwo);
                    arm.readyToWall();
                    setPathState(5);
                }
            break;

            case 5:
                if(pathTimer.getElapsedTimeSeconds() < 1.5) {
                    arm.readyToWall();
                }
                if(pathTimer.getElapsedTimeSeconds() > 2.0) {
                    arm.closeClaw();
                }
                if(pathTimer.getElapsedTimeSeconds() > 2.5) {
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

        if(map.RightViperMotor.getCurrentPosition() > 860){
            arm.openClaw();
        }

        Drawing.drawPoseHistory(dashboardPoseTracker, "#4CAF50");
        Drawing.drawRobot(follower.poseUpdater.getPose(), "#4CAF50");
        Drawing.sendPacket();
    }

    private DashboardPoseTracker dashboardPoseTracker;
    private Telemetry telemetryA;
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

        dashboardPoseTracker = new DashboardPoseTracker(follower.poseUpdater);

        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.addLine("This will print your robot's position to telemetry while "
            + "allowing robot control through a basic mecanum drive on gamepad 1.");
        telemetryA.update();

        Drawing.drawRobot(follower.poseUpdater.getPose(), "#4CAF50");
        Drawing.sendPacket();

        map = new HardwareMap(hardwareMap);
        arm = new Arm(map, Optional.of(target));
        arm.closeClaw();

        map.DrawerSlideMotor.setTargetPosition(0);
        map.DrawerSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        map.DrawerSlideMotor.setPower(0.1);
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }
}
