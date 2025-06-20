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
                            new Point(8.700, 65.126, Point.CARTESIAN),
                            new Point(17.300, 72.000, Point.CARTESIAN),
                            new Point(12.000, 70.000, Point.CARTESIAN),
                            new Point(35.000, 70.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();

    public static PathChain line2 = builder
            .addPath(
                    new BezierCurve(
                            new Point(35.000, 70.000, Point.CARTESIAN),
                            new Point(20.000, 33.000, Point.CARTESIAN),
                            new Point(43.000, 34.000, Point.CARTESIAN),
                            new Point(60.000, 33.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .setReversed(true)
            .build();

    public static PathChain line3 = builder
            .addPath(
                    new BezierCurve(
                            new Point(60.000, 33.000, Point.CARTESIAN),
                            new Point(73.912, 24.212, Point.CARTESIAN),
                            new Point(23.000, 23.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();

    public static PathChain line4 = builder
            .addPath(
                    new BezierCurve(
                            new Point(23.000, 23.000, Point.CARTESIAN),
                            new Point(72.091, 28.217, Point.CARTESIAN),
                            new Point(66.266, 14.928, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();

    public static PathChain line5 = builder
            .addPath(
                    new BezierLine(
                            new Point(66.266, 14.928, Point.CARTESIAN),
                            new Point(23.120, 14.564, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();

    public static PathChain line6 = builder
            .addPath(
                    new BezierCurve(
                            new Point(23.120, 14.564, Point.CARTESIAN),
                            new Point(67.722, 16.566, Point.CARTESIAN),
                            new Point(66.812, 8.192, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();

    public static PathChain line7 = builder
            .addPath(
                    new BezierLine(
                            new Point(66.812, 8.192, Point.CARTESIAN),
                            new Point(23.120, 7.646, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();

    public static PathChain line8 = builder
            .addPath(
                    new BezierCurve(
                            new Point(23.120, 7.646, Point.CARTESIAN),
                            new Point(39.323, 49.335, Point.CARTESIAN),
                            new Point(9.649, 47.332, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();

    public static PathChain line9 = builder
            .addPath(
                    new BezierLine(
                            new Point(9.649, 47.332, Point.CARTESIAN),
                            new Point(35.000, 67.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();
}