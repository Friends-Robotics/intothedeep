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
                    new BezierLine(
                            new Point(8.503, 64.040, Point.CARTESIAN),
                            new Point(35.000, 72.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();

    public static PathChain line2 = builder
            .addPath(
                    new BezierCurve(
                            new Point(35.000, 72.000, Point.CARTESIAN),
                            new Point(17.367, 15.015, Point.CARTESIAN),
                            new Point(76.161, 43.417, Point.CARTESIAN),
                            new Point(68.382, 24.965, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
            .build();

    public static PathChain line3 = builder
            .addPath(
                    new BezierLine(
                            new Point(68.382, 24.965, Point.CARTESIAN),
                            new Point(21.528, 26.412, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
            .build();

    public static PathChain line4 = builder
            .addPath(
                    new BezierCurve(
                            new Point(21.528, 26.412, Point.CARTESIAN),
                            new Point(77.427, 27.497, Point.CARTESIAN),
                            new Point(56.261, 14.291, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
            .build();

    public static PathChain line5 = builder
            .addPath(
                    new BezierLine(
                            new Point(56.261, 14.291, Point.CARTESIAN),
                            new Point(22.000, 14.111, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
            .build();

    public static PathChain line6 = builder
            .addPath(
                    new BezierCurve(
                            new Point(22.000, 14.111, Point.CARTESIAN),
                            new Point(71.276, 16.824, Point.CARTESIAN),
                            new Point(57.889, 9.500, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
            .build();

    public static PathChain line7 = builder
            .addPath(
                    new BezierLine(
                            new Point(57.889, 9.500, Point.CARTESIAN),
                            new Point(22.000, 9.500, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(90))
            .build();

    public static PathChain line8 = builder
            .addPath(
                    new BezierCurve(
                            new Point(22.000, 9.500, Point.CARTESIAN),
                            new Point(45.045, 32.744, Point.CARTESIAN),
                            new Point(9.000, 31.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
            .build();
}
