package friends.autonomous;

public enum AutoPaths {
    SCORE_INITIAL(new double[][]{
        {8.700, 65.126},
        {17.367, 72.000},
        {10.492, 71.819},
        {37.500, 72.000},
    }, false),
    SETUP_SWEEP_ONE(new double[][]{
        {37.500, 72.000},
        {32.020, 14.111},
        {70.010, 25.869},
        {83.216, 24.060},
    }, false),
    SWEEP_ONE(new double[][]{
        {83.216, 24.060},
        {21.889, 22.975},
    }, true),
    SETUP_SWEEP_TWO(new double[][]{
        {21.889, 22.975},
        {51.739, 27.678},
        {70.915, 20.442},
        {75.799, 12.482},
        {82.673, 12.844},
    }, false),
    SWEEP_TWO(new double[][]{
        {82.673, 12.844},
        {21.709, 12.482},
    }, true),
    SETUP_SWEEP_THREE(new double[][]{
        {21.709, 12.482},
        {56.080, 15.739},
        {72.543, 11.759},
        {70.010, 7.598},
        {80.864, 7.598},
    }, false),
    SWEEP_THREE(new double[][]{
        {80.864, 7.598},
        {22.975, 7.417},
    }, true),
    SPECIMEN_ONE(new double[][]{
        {13.150, 8.084},
        {50.291, 12.121},
        {12.934, 34.491},
        {49.387, 32.201},
        {8.500, 32.000},
    }, false),
    SCORE_ONE(new double[][]{
        {8.500, 32.000},
        {8.141, 70.372},
        {37.500, 70.000},
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

    private final double[][] arr;
    private final boolean reverse;

    AutoPaths(double[][] array, boolean reverse) {
        arr = array;
        this.reverse = reverse;
    }

    public double[][] getCords() {
        return arr;
    }

    public boolean getReverse() {
        return reverse;
    }
}
