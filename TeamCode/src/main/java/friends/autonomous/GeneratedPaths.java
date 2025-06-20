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
                            new Point(60.804, 22.200, Point.CARTESIAN),
                            new Point(23.000, 23.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();
}

