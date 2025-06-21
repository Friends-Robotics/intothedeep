package friends.helper.MotorControl;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    double KP;
    double KI;
    double KD;
    double tolerance = 0;

    public PIDController(double kp, double ki, double kd, double tolerance) {
        this.KP = kp;
        this.KI = ki;
        this.KD = kd;
        this.tolerance = tolerance;
    }

    public PIDController(double kp, double ki, double kd) {
        this.KP = kp;
        this.KI = ki;
        this.KD = kd;
    }

    ElapsedTime timer = new ElapsedTime();
    private double integralSum = 0;
    private int lastError = 0;

    public double PIDControl(int state, int max) {
        int error = max - state;

        if (Math.abs(error) < tolerance) {
            integralSum = 0;
            return 0;
        }

        double derivative = (error - lastError) / timer.seconds();
        integralSum += (error * timer.seconds());
        lastError = error;

        timer.reset();

        return (KP * error) + (KI * integralSum) + (KD * derivative);
    }
}
