package friends.autonomous;

// SCORE SPECIMEN
// SWEEP ONE
// SWEEP TWO
// SWEEP THREE
// PICK UP SPECIMEN
// SCORE ONE
// SCORE TWO
// SCORE THREE

public enum AutoPaths {
    SCORE_INITIAL(new double[]{}),
    SWEEP_ONE(new double[]{}),
    SWEEP_TWO(new double[]{}),
    SWEEP_THREE(new double[]{}),
    PICK_UP_SPECIMEN(new double[]{}),
    SCORE_ONE(new double[]{}),
    SCORE_TWO(new double[]{}),
    SCORE_THREE(new double[]{});

    private final double[] arr;

    AutoPaths(double[] array) {
        arr = array;
    }

    public double[] getPoints() {
        return arr;
    }
}
