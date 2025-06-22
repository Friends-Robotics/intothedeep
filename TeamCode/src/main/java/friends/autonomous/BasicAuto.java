package friends.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Optional;

import static friends.autonomous.AutoPaths.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
import friends.helper.Count;
import friends.helper.MotorControl.PIDFController;
import friends.helper.MotorControl.ViperPIDFConstants;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "Autobots", group = "Competition")
public class BasicAuto extends OpMode {
    private final Pose startPose = new Pose(8.7, 65.1, Math.toRadians(0));  // Starting position
    private HardwareMap map;
    private Timer pathTimer;
    private Follower follower;
    private Arm arm;
    private FtcDashboard dash;

    private PIDFController viperpidf = new PIDFController(ViperPIDFConstants.KP, ViperPIDFConstants.KI, ViperPIDFConstants.KD, ViperPIDFConstants.KF);
    private Count target = new Count();

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        pathTimer = new Timer();
        pathTimer.resetTimer();
        dash = FtcDashboard.getInstance();

        telemetry.speak("BALLS");

        map = new HardwareMap(hardwareMap);
        arm = new Arm(map, Optional.of(target));
        map.DrawerSlideMotor.setTargetPosition(0);
        map.DrawerSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        map.DrawerSlideMotor.setPower(0.1);
        arm.closeClaw();
    }

    private AutoPaths currentPath = SCORE_INITIAL;
    private final Count stopped = new Count();

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        Telemetry t = dash.getTelemetry();
        t.addData("Current Path", currentPath.toString());

        telemetry.addData("Time", pathTimer.getElapsedTimeSeconds());
        telemetry.addData("Current Path", currentPath.toString());

        if(map.RightViperMotor.getCurrentPosition() > 865){
            arm.openClaw();
        }

        double power = viperpidf.PIDControl(map.RightViperMotor.getCurrentPosition(), (int)target.value);
        map.LeftViperMotor.setPower(power);
        map.RightViperMotor.setPower(power);

        // Hold in the intake
        map.DrawerSlideMotor.setTargetPosition(0);
    }

    private boolean isScoring = false;

    public void autonomousPathUpdate() {
        if(stopped.value == 1) return;
        switch (currentPath) {
            case SCORE_INITIAL:
                if(!follower.isBusy()) {
                    arm.readyToScore();
                    follower.followPath(currentPath.getPathChain(), true);
                    break;
                }

                if(pathTimer.getElapsedTimeSeconds() > 6 && !isScoring) {
                    arm.score();
                    isScoring = true;
                    break;
                }

                if(pathTimer.getElapsedTimeSeconds() > 8 && isScoring) {
                    arm.openClaw();
                    isScoring = false;
                    break;
                }
                break;

            case SETUP_ONE:
                if(!follower.isBusy()) {
                    follower.followPath(currentPath.getPathChain(), true);
                    setPathState(SWEEP_ONE);
                }
                break;

            case SWEEP_ONE:
                if(!follower.isBusy()) {
                    follower.followPath(currentPath.getPathChain(), true);
                    setPathState(SETUP_TWO);
                }
                break;

            case SETUP_TWO:
                if(!follower.isBusy()) {
                    follower.followPath(currentPath.getPathChain(), true);
                    setPathState(SWEEP_TWO);
                }
                break;

            case SWEEP_TWO:
                if(!follower.isBusy()) {
                    follower.followPath(currentPath.getPathChain(), true);
                    setPathState(SETUP_THREE);
                }
                break;
            case SETUP_THREE:
                if(!follower.isBusy()) {
                    follower.followPath(currentPath.getPathChain(), true);
                    setPathState(SWEEP_THREE);
                }
                break;
            case SWEEP_THREE:
                if(!follower.isBusy()) {
                    follower.followPath(currentPath.getPathChain(), true);
                    setPathState(PICKUP_ONE);
                }
                break;
            case PICKUP_ONE:
                if(!follower.isBusy()) {
                    follower.followPath(currentPath.getPathChain(), true);
                }

                if(pathTimer.getElapsedTimeSeconds() > 100) {
                    setPathState(NULL);
                }
                break;
            case NULL:
                if(follower.isBusy()) break;
                follower.breakFollowing();
                break;
        }
    }

    public void setPathState(AutoPaths path) {
        currentPath = path;
        pathTimer.resetTimer();
    }
}