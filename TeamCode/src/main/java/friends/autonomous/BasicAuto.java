package friends.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.DashboardPoseTracker;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Optional;

import static friends.autonomous.AutoPaths.*;
import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
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


    @Override
    public void init() {
        pathTimer = new Timer();
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();
        dash = FtcDashboard.getInstance();
        map = new HardwareMap(hardwareMap);
        arm = new Arm(map, Optional.empty());
    }

    private final EnumMap<AutoPaths, PathChain> paths = new EnumMap<>(AutoPaths.class);
    private AutoPaths currentPath = AutoPaths.SCORE_INITIAL;
    private boolean stopped = false;

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
        Pose pose = follower.getPose();

        TelemetryPacket packet = new TelemetryPacket();
        Canvas canvas = packet.fieldOverlay();
        canvas.setStrokeWidth(2);
        canvas.setStroke("#00FF00");
        canvas.strokeCircle(pose.getX(), pose.getY(), 4);
        canvas.strokeLine(
                pose.getX(), pose.getY(),
                pose.getX() + Math.cos(pose.getHeading()) * 10,
                pose.getY() + Math.sin(pose.getHeading()) * 10
        );

        dash.sendTelemetryPacket(packet);
        telemetry.addData("Path State", currentPath.toString());
        telemetry.addData("Position", follower.getPose().toString());
        telemetry.update();
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

    public void autonomousPathUpdate() {
        if(stopped) return;
        switch (currentPath) {
            case SCORE_INITIAL:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SCORE_INITIAL), true);
                    setPathState(SETUP_SWEEP_ONE);
                }
                break;

            case SETUP_SWEEP_ONE:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SETUP_SWEEP_ONE), true);
                    setPathState(SWEEP_ONE);
                }

            case SWEEP_ONE:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SWEEP_ONE), true);
                    setPathState(SETUP_SWEEP_TWO);
                }
                break;

            case SETUP_SWEEP_TWO:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SETUP_SWEEP_TWO), true);
                    setPathState(SWEEP_TWO);
                }
                break;


            case SWEEP_TWO:
                if(!follower.isBusy() ) {
                    follower.followPath(paths.get(SWEEP_TWO), true);
                    setPathState(SETUP_SWEEP_THREE);
                }
                break;

            case SETUP_SWEEP_THREE:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SETUP_SWEEP_THREE), true);
                    setPathState(SWEEP_THREE);
                }
                break;


            case SWEEP_THREE:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SWEEP_THREE));
                    stopped = true;
                    return;
//                    setPathState(SPECIMEN_ONE);
                }
                break;

            case SPECIMEN_ONE:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SPECIMEN_ONE));
                    setPathState(SCORE_ONE);
                }
                break;
            case SCORE_ONE:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SCORE_ONE));
                    setPathState(SPECIMEN_TWO);
                }
                break;
            case SPECIMEN_TWO:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SPECIMEN_TWO));
                    setPathState(SCORE_TWO);
                }
                break;
            case SCORE_TWO:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SCORE_TWO));
                    setPathState(SPECIMEN_THREE);
                }
                break;
            case SPECIMEN_THREE:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SPECIMEN_THREE));
                    setPathState(SCORE_THREE);
                }
                break;
            case SCORE_THREE:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SCORE_THREE));
                    setPathState(PARK);
                }
                break;
            case PARK:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(PARK));
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
