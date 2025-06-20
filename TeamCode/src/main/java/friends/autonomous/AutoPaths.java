package friends.autonomous;

import com.pedropathing.pathgen.PathChain;

import java.util.Optional;

public enum AutoPaths {
<<<<<<< HEAD
    SCORE_INITIAL(new double[][]{
        {8.700, 65.126},
        {17.367, 72.000},
        {12.844, 70.191},
        {36.000, 70.000},
    }, false),
    SETUP_SWEEP_ONE(new double[][]{
        {36.000, 70.000},
        {26.231, 37.990},
        {42.513, 35.819},
        {63.497, 23.337},
    }, false),
    SWEEP_ONE(new double[][]{
        {63.497, 23.337},
        {22.070, 22.794},
    }, true),
    SETUP_SWEEP_TWO(new double[][]{
        {22.070, 22.794},
        {71.276, 28.040},
        {54.995, 15.015},
        {69.648, 12.844},
    }, false),
    SWEEP_TWO(new double[][]{
        {63.136, 12.482},
        {21.889, 12.302},
    }, true),
    SETUP_SWEEP_THREE(new double[][]{
        {21.709, 12.482},
        {62.231, 7.055},
    }, false),
    SWEEP_THREE(new double[][]{
        {62.231, 7.055},
        {22.975, 7.417},
    }, true),
    SPECIMEN_ONE(new double[][]{
        {21.889, 12.302},
        {57.709, 32.020},
        {17.000, 32.000},
    }, true),
    SCORE_ONE(new double[][]{
        {17.000, 32.000},
        {12.482, 66.573},
        {36.000, 68.000},
    }, false),
    SPECIMEN_TWO(new double[][]{
        {37.500, 70.000},
        {36.724, 32.382},
        {8.500, 32.000},
    }, false),
    SCORE_TWO(new double[][]{
        {8.500, 32.000},
        {11.397, 68.382},
        {37.500, 68.500},
    }, false),
    SPECIMEN_THREE(new double[][]{
        {37.500, 68.500},
        {34.553, 31.839},
        {8.500, 32.000},
    }, false),
    SCORE_THREE(new double[][]{
        {8.500, 32.000},
        {15.739, 66.030},
        {37.500, 66.000},
    }, false),
    PARK(new double[][]{
        {37.500, 66.000},
        {5.066, 59.713},
        {9.226, 56.985},
        {8.683, 16.462},
    }, false),
    FINISH(new double[][]{
        {37.500, 66.000},
        {5.066, 59.713},
        {9.226, 56.985},
        {8.683, 16.462},
    }, false);
=======
    SCORE_INITIAL(Optional.of(GeneratedPaths.line1)),
>>>>>>> refs/remotes/origin/comp

//    SETUP_SWEEP_ONE(GeneratedPaths.line2),
//
//    SWEEP_ONE(GeneratedPaths.line3),
//
//    SETUP_SWEEP_TWO(GeneratedPaths.line4),
//
//    SWEEP_TWO(GeneratedPaths.line5),
//
//    SETUP_SWEEP_THREE(GeneratedPaths.line6),
//
//    SWEEP_THREE(GeneratedPaths.line7),
//
//    SPECIMEN_ONE(GeneratedPaths.line5),
//
//    SCORE_ONE(GeneratedPaths.line6),
//
//    SPECIMEN_TWO(GeneratedPaths.line6),
//
//    SCORE_TWO(GeneratedPaths.line6),
//
//    SPECIMEN_THREE(GeneratedPaths.line6),
//
//    SCORE_THREE(GeneratedPaths.line6),
//
//    PARK(GeneratedPaths.line6),

    FINISH(Optional.empty());

    private final PathChain pathChain;

    AutoPaths(Optional<PathChain> pathChain) {
        this.pathChain = pathChain.orElse(new PathChain());
    }

    public PathChain getPathChain() { return pathChain; }
}
