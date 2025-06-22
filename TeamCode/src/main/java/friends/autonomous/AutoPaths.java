package friends.autonomous;

import android.telephony.mbms.MbmsErrors;

import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;

import java.util.Optional;

public enum AutoPaths {
    SCORE_INITIAL(GeneratedPaths.line1),
    SETUP_ONE(GeneratedPaths.line2),
    SWEEP_ONE(GeneratedPaths.line3),
    SETUP_TWO(GeneratedPaths.line4),
    SWEEP_TWO(GeneratedPaths.line5),
    SETUP_THREE(GeneratedPaths.line6),
    SWEEP_THREE(GeneratedPaths.line7),
    PICKUP_ONE(GeneratedPaths.line8),
    NULL(new PathChain());

    private final PathChain pathChain;

    AutoPaths(PathChain pathChain) {
        this.pathChain = pathChain;
    }

    public PathChain getPathChain() { return pathChain; }
}
