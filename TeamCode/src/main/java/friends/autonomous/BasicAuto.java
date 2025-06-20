package friends.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Optional;

import static friends.autonomous.AutoPaths.*;
import static friends.autonomous.PathType.*;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
import friends.helper.Count;
import friends.helper.MotorControl.PIDFController;
import friends.helper.MotorControl.ViperPIDFConstants;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "Autobots", group = "Competition")
public class BasicAuto extends OpMode {
    private final Pose startPose = new Pose(8.7, 65.12562814070351, Math.toRadians(0));  // Starting position
    private HardwareMap map;
    private Timer pathTimer, actionTimer, opmodeTimer;
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

        map = new HardwareMap(hardwareMap);
        arm = new Arm(map, Optional.of(target));
        map.DrawerSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        map.DrawerSlideMotor.setPower(0.1);
        arm.readyToScore();
        arm.closeClaw();
    }

    private AutoPaths currentPath;
    private boolean stopped = false;

    @Override
    public void start() {
        setPathState(SCORE_INITIAL);
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        double power = viperpidf.PIDControl(map.RightViperMotor.getCurrentPosition(), (int)target.value);
        map.LeftViperMotor.setPower(power);
        map.RightViperMotor.setPower(power);

        // Hold in the intake
        map.DrawerSlideMotor.setTargetPosition(0);
    }


    public void buildPaths() {
        for(AutoPaths path : AutoPaths.values()) {
            double[][] cords = path.getCords();
            ArrayList<Point> points = new ArrayList<>();

            for (double[] cord : cords) {
                points.add(new Point(cord[0], cord[1], Point.CARTESIAN));
                telemetry.addData("Added new point", cord[0]);
                telemetry.addData("Added new point", cord[1]);
            }

            paths.put(path, follower.pathBuilder().addPath(new BezierCurve(points)).setTangentHeadingInterpolation().setReversed(path.getReverse()).build());
            telemetry.update();
        }
    }

    private boolean isScoring = false;
    private boolean startScoring = false;

    public void autonomousPathUpdate() {
        if(stopped) return;
        switch (currentPath) {
            case SCORE_INITIAL:
                if(!follower.isBusy() && !startScoring) {
                    pathTimer.resetTimer();
                    follower.followPath(SCORE_INITIAL.getPathChain(), true);
                    telemetry.addLine("Currently Following");
                    telemetry.update();
                    startScoring = true;
                    // setPathState(SETUP_SWEEP_ONE);
                }
                if(pathTimer.getElapsedTimeSeconds() > 1.5 && startScoring) {
                    arm.score();
                    telemetry.addLine("Currently Scoring");
                    telemetry.update();
                    isScoring = true;
                }
                if(pathTimer.getElapsedTimeSeconds() > 1.8 && isScoring) {
                    arm.openClaw();
                    isScoring = false;
                    telemetry.addLine("Currently Currently Opening Claw");
                    telemetry.update();
                    setPathState(SPECIMEN_TWO);
                    stopped = true;
                }
                break;
                    setPathState(FINISH);
                }
                break;
        }
    }

    public void setPathState(AutoPaths path) {
        currentPath = path;
        pathTimer.resetTimer();
    }
}
