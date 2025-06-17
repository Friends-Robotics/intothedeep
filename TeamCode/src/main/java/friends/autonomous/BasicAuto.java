package friends.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;
import java.util.EnumMap;

import static friends.autonomous.AutoPaths.*;
import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "Autobots", group = "Competition")
public class BasicAuto extends OpMode {
    private final Pose startPose = new Pose(9.52894729958441, 111.08571428571429, Math.toRadians(0));  // Starting position
    private HardwareMap map;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private Follower follower;
    private Arm arm;

    @Override
    public void init() {
        pathTimer = new Timer();
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();
        map = new HardwareMap(hardwareMap);
        arm = new Arm(map);
    }

    private final EnumMap<AutoPaths, PathChain> paths = new EnumMap<>(AutoPaths.class);
    private AutoPaths currentPath = AutoPaths.SCORE_INITIAL;

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
        telemetry.addData("Path State", currentPath.toString());
        telemetry.addData("Position", follower.getPose().toString());
        telemetry.update();
    }


    public void buildPaths() {
        for(AutoPaths path : AutoPaths.values()) {
            double[][] cords = path.getCords();
            ArrayList<Point> points = new ArrayList<>();

            for(int i = 0; i < cords.length; i++) {
                points.add(new Point(cords[i][0], cords[i+1][1], Point.CARTESIAN));
            }

            paths.put(path, follower.pathBuilder().addPath(new BezierCurve(points)).setTangentHeadingInterpolation().build());
        }
    }

    public void autonomousPathUpdate() {
        switch (currentPath) {
            case SCORE_INITIAL:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SCORE_INITIAL));
                    setPathState(SWEEP_ONE);
                }
                break;

            case SWEEP_ONE:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SWEEP_ONE), true);
                    setPathState(SWEEP_TWO);
                }
                break;

            case SWEEP_TWO:
                if(!follower.isBusy() ) {
                    follower.followPath(paths.get(SWEEP_TWO));
                    setPathState(SWEEP_THREE);
                }
                break;

            case SWEEP_THREE:
                if(!follower.isBusy()) {
                    follower.followPath(paths.get(SWEEP_THREE));
                    setPathState(SPECIMEN_ONE);
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