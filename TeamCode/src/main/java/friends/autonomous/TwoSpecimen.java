
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

@Autonomous(name = "2 Specimen", group = "Comp")
public class TwoSpecimen extends OpMode {
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;

    private final Pose startPose = new Pose(8.5, 65, Math.toRadians(0));

    private Path scoreInitial, pickupSecond, scoreSecond, park;
    private PathChain sweeps;

    public void buildPaths() {
        scoreInitial = new Path(
                new BezierLine(
                        new Point(8.500, 65.000, Point.CARTESIAN),
                        new Point(38.000, 78.000, Point.CARTESIAN)
                )
        );
        scoreInitial.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

        pickupSecond = new Path(
                new BezierCurve(
                        new Point(38.000, 78.000, Point.CARTESIAN),
                        new Point(43.055, 23.698, Point.CARTESIAN),
                        new Point(8.500, 32.020, Point.CARTESIAN)
                )
        );
        pickupSecond.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

        scoreSecond = new Path(
                new BezierLine(
                        new Point(8.000, 32.020, Point.CARTESIAN),
                        new Point(38.000, 76.000, Point.CARTESIAN)
                )
        );
        scoreSecond.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

        park = new Path(
                new BezierLine(
                        new Point(38.000, 76.000, Point.CARTESIAN),
                        new Point(8, 32.020, Point.CARTESIAN)
                )
        );
        park.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));


        PathBuilder builder = new PathBuilder();

        builder.addPath(
                        new BezierLine(
                                new Point(37.000, 76.000, Point.CARTESIAN),
                                new Point(32.382, 40.161, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90));

        builder.addPath(
                        new BezierCurve(
                                new Point(32.382, 40.161, Point.CARTESIAN),
                                new Point(113.427, 23.879, Point.CARTESIAN),
                                new Point(18.000, 22.800, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90));

        builder.addPath(
                        new BezierLine(
                                new Point(18.000, 22.800, Point.CARTESIAN),
                                new Point(54.995, 24.422, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90));


        sweeps = builder.build();
    }


    /** This switch is called continuously and runs the pathing, at certain points, it triggers the action state.
     * Everytime the switch changes case, it will reset the timer. (This is because of the setPathState() method)
     * The followPath() function sets the follower to run the specific path, but does NOT wait for it to finish before moving on. */
    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(scoreInitial);
                arm.readyToScore();
                arm.looseClaw();
                setPathState(1);
                break;
            case 1:
                if (pathTimer.getElapsedTimeSeconds() > 1.5) {
                    arm.closeClaw();
                    arm.score();
                }

                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 2) {
                    follower.followPath(pickupSecond, true);
                    arm.openClaw();
                    arm.readyToWall();
                    setPathState(2);
                }
                break;

            case 2:
                if(!follower.isBusy()) {
                    follower.followPath(scoreSecond, true);
                    arm.readyToScore();
                    arm.looseClaw();
                    setPathState(3);

                }
                break;

            case 3:
                if(pathTimer.getElapsedTimeSeconds() > 1.5) {
                    arm.closeClaw();
                    arm.score();
                }

                if(pathTimer.getElapsedTimeSeconds() > 2) {
                    follower.followPath(park, true);
                    arm.openClaw();
                    arm.readyToWall();
                    setPathState(4);
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
