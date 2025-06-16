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
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "Autobots", group = "Competition")
public class BasicAuto extends OpMode {
    private final Pose startPose = new Pose(9.52894729958441, 111.08571428571429, Math.toRadians(0));  // Starting position
    private HardwareMap map;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private Follower follower;

    @Override
    public void init() {
        pathTimer = new Timer();
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();

        map = new HardwareMap(hardwareMap);
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
            double[] points = path.getPoints();
            if(points.length % 2 != 0) continue;

            ArrayList<Point> path_points = new ArrayList<Point>();

            for(int i = 0; i < points.length; i+=2) {
                path_points.add(new Point(points[i], points[i+1], Point.CARTESIAN));
            }

            paths.put(path, follower.pathBuilder().addPath(new BezierCurve(path_points)).setTangentHeadingInterpolation().build());
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
        }
    }

    public void setPathState(AutoPaths path) {
        currentPath = path;
        pathTimer.resetTimer();
    }
}