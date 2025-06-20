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
                            new Point(17.367, 72.000, Point.CARTESIAN),
                            new Point(12.844, 70.191, Point.CARTESIAN),
                            new Point(35.000, 70.000, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();
}

