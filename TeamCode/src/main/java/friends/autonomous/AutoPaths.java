package friends.autonomous;

import com.pedropathing.pathgen.PathChain;

import java.util.Optional;

public enum AutoPaths {
    SCORE_INITIAL(Optional.of(GeneratedPaths.line1)),

    SETUP_SWEEP_ONE(Optional.of(GeneratedPaths.line2)),

    SWEEP_ONE(Optional.of(GeneratedPaths.line3)),

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
