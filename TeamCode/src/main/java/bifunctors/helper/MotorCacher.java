package bifunctors.helper;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class MotorCacher {
    public double accepted_diff = 0.1;
    private double current_value;
    private final DcMotorEx cache;

    public MotorCacher(DcMotorEx motor) {
        cache = motor;
    }

    public MotorCacher(DcMotorEx motor, double diff) {
        cache = motor;
        accepted_diff = diff;
    }

    public void eval(double value) {
        if(current_value - value < accepted_diff) {
            return;
        }

        cache.setPower(value);
    }
}