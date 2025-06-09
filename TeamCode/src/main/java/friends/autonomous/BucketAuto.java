package friends.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.*;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "PedroPathing Bucket", group = "Competition OpModes")
public class BucketAuto extends OpMode {
    private final Pose startPose = new Pose(9.52894729958441, 111.08571428571429, Math.toRadians(0));  // Starting position
    private Path scorePreload;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private Follower follower;

    HardwareMap teamHardwareMap;
    HorizontalExtension arm;
    VerticalExtension extension;
    Claw claw;

    private int pathState;

    @Override
    public void init() {
        pathTimer = new Timer();
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();

        teamHardwareMap = new HardwareMap(hardwareMap);

        // Create arm controller
        arm = new HorizontalExtension(
                teamHardwareMap.RightExtendServo,
                teamHardwareMap.LeftExtendServo
        );

        claw = new Claw(
                teamHardwareMap.ClawServo
        );

//
//        // Create viper slide controller
        extension = new VerticalExtension(
                teamHardwareMap.RightViperMotor,
                teamHardwareMap.LeftViperMotor,
                teamHardwareMap.ViperBucketServo
        );

        extension.RightViper.setTargetPosition(0);
        extension.LeftViper.setTargetPosition(0);
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
        telemetry.addData("Path State", pathState);
        telemetry.addData("Position", follower.getPose().toString());
        telemetry.update();
    }

    private final BezierLine StartingPath =
            new BezierLine(
                    new Point(9.096, 110.003, Point.CARTESIAN),
                    new Point(32.915, 110.003, Point.CARTESIAN)
            ) ;
    private final BezierLine ToFirstBucket =
            // Line 2
            new BezierLine(
                    new Point(32.915, 110.003, Point.CARTESIAN),
                    new Point(21.006, 129.059, Point.CARTESIAN)
            ) ;

    private final BezierLine ToFirstSample =
            new BezierLine(
                    new Point(17.758, 128.842, Point.CARTESIAN),
                    new Point(33.782, 108.920, Point.CARTESIAN)
            );

    private final BezierLine ToParking =
            new BezierLine(
                    new Point(21.006, 129.059, Point.CARTESIAN),
                    new Point(67.129, 98.959, Point.CARTESIAN)
            );
    private final BezierLine ToParkingPrecise =
            new BezierLine(
                    new Point(67.129, 98.959, Point.CARTESIAN),
                    new Point(65.397, 89.432, Point.CARTESIAN)
            );

    private final BezierLine RetreatBucketPrecise =
            new BezierLine(
                    new Point(19.273, 131.657, Point.CARTESIAN),
                    new Point(21.006, 129.059, Point.CARTESIAN)
            );

    private final BezierLine ToFirstBucketPrecise =
            new BezierLine(
                    new Point(21.006, 129.059, Point.CARTESIAN),
                    new Point(19.273, 131.657, Point.CARTESIAN)
            );

    private PathChain out_from_start, to_bucket_start, to_first_sample, to_parking, to_parking_precise, to_first_bucket_precise, retreat_first_bucket_precise;

    public void buildPaths() {
        // Path for scoring preload
        scorePreload = new Path(StartingPath);
        scorePreload.setTangentHeadingInterpolation();

        out_from_start = follower.pathBuilder()
                .addPath(StartingPath)
                .setTangentHeadingInterpolation()
                .build();

        to_bucket_start = follower.pathBuilder()
                .addPath(ToFirstBucket)
                .setTangentHeadingInterpolation()
                .build();

        to_first_sample = follower.pathBuilder()
                .addPath(ToFirstSample)
                .setTangentHeadingInterpolation()
                .build();

        to_parking = follower.pathBuilder()
                .addPath(ToParking).
                setTangentHeadingInterpolation()
                .setReversed(true)
                .build();

        to_parking_precise = follower.pathBuilder()
                .addPath(ToParkingPrecise)
                .setTangentHeadingInterpolation()
                .build();

        to_first_bucket_precise = follower.pathBuilder()
                .addPath(ToFirstBucketPrecise)
                .setTangentHeadingInterpolation()
                .build();

        retreat_first_bucket_precise = follower.pathBuilder()
                .addPath(RetreatBucketPrecise)
                .setTangentHeadingInterpolation()
                .setReversed(true)
                .build();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                if (!follower.isBusy()) {
                    follower.followPath(out_from_start, true);
                    claw.clawOpen();
                    setPathState(1);
                }
                break;

            case 1:
                if(!follower.isBusy()) {
                    follower.followPath(to_bucket_start, true);
                    setPathState(2);
                }
                break;

            case 2:
                extension.Textend(6000);
                if (!follower.isBusy()) {
                    setPathState(3);
                }
                break;

            case 3:
                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 4.5) {
                    follower.followPath(to_first_bucket_precise, true);
                    setPathState(4);
                }
                break;

            case 4:
                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 1) {
                    extension.depositBucket();
                    setPathState(5);
                }
                break;

            case 5:
                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 3) {
                    follower.followPath(to_parking, true);
                    setPathState(6);
                }
                break;

            case 6:
                if(pathTimer.getElapsedTimeSeconds() > 2)  {
                    extension.Textend(1920);
                    extension.receiveBucket();
                    setPathState(7);
                }
                break;

            case 7:
                if(!follower.isBusy()) {
                    follower.followPath(to_parking_precise, true);
                    setPathState(69);
                }
                break;

            case 69:
                if(!follower.isBusy()) {
                    setPathState(-1);
                }
                break;

        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
}