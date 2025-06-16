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
    SCORE_INITIAL(new double[][]{}),
    SWEEP_ONE(new double[][]{}),
    SWEEP_TWO(new double[][]{}),
    SWEEP_THREE(new double[][]{}),
    SPECIMEN_ONE(new double[][]{}),
    SCORE_ONE(new double[][]{}),
    SPECIMEN_TWO(new double[][]{}),
    SCORE_TWO(new double[][]{}),
    SPECIMEN_THREE(new double[][]{}),
    SCORE_THREE(new double[][]{}),
    PARK(new double[][]{}),
    FINISH(new double[][]{});

    private final double[][] arr;

    AutoPaths(double[][] array) {
        arr = array;
    }

    public double[][] getCords() {
        return arr;
    }
}
