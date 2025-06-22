package friends.autonomous;

import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;


public class GeneratedPaths {

    public static PathBuilder builder = new PathBuilder();

    public static PathChain line1 = builder
            .addPath(
                    new BezierCurve(
                            new Point(8.700, 65.100, Point.CARTESIAN),
                            new Point(16.824, 75.618, Point.CARTESIAN),
                            new Point(32.750, 73.990, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();

    public static PathChain line2 = builder
            .addPath(
                    new BezierCurve(
                            new Point(32.750, 73.990, Point.CARTESIAN),
                            new Point(35.000, 70.000, Point.CARTESIAN),
                            new Point(20.000, 33.000, Point.CARTESIAN),
                            new Point(46.492, 43.598, Point.CARTESIAN),
                            new Point(55.899, 33.829, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();

    public static PathChain line3 = builder
            .addPath(
                    new BezierCurve(
                            new Point(55.899, 33.829, Point.CARTESIAN),
                            new Point(58.000, 24.000, Point.CARTESIAN),
                            new Point(20.000, 23.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(120), Math.toRadians(90))
            .build();

    public static PathChain line4 = builder
            .addPath(
                    new BezierCurve(
                            new Point(20.000, 23.000, Point.CARTESIAN),
                            new Point(68.000, 31.000, Point.CARTESIAN),
                            new Point(63.000, 14.500, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(90))
            .build();

    public static PathChain line5 = builder
            .addPath(
                    new BezierLine(
                            new Point(63.000, 14.500, Point.CARTESIAN),
                            new Point(20.000, 14.000, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(90))
            .build();

    public static PathChain line6 = builder
            .addPath(
                    new BezierCurve(
                            new Point(20.000, 14.000, Point.CARTESIAN),
                            new Point(64.000, 16.000, Point.CARTESIAN),
                            new Point(63.000, 9.000, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(90))
            .build();

    public static PathChain line7 = builder
            .addPath(
                    new BezierLine(
                            new Point(63.000, 9.000, Point.CARTESIAN),
                            new Point(20.000, 8.000, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(90))
            .build();

    public static PathChain line8 = builder
            .addPath(
                    new BezierLine(
                            new Point(20.000, 8.000, Point.CARTESIAN),
                            new Point(10.000, 35.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
            .build();

    public static PathChain line9 = builder
            .addPath(
                    new BezierLine(
                            new Point(10.000, 35.000, Point.CARTESIAN),
                            new Point(32.000, 70.000, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();
}
