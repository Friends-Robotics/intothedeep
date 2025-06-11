package friends.helper.MotorControl;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    double KP;
    double KI;
    double KD;

    public PIDController(PIDFConstants constants) {
        this.KP = constants.KP;
        this.KI = constants.KI;
        this.KD = constants.KD;
    }

    ElapsedTime timer = new ElapsedTime();
    private double integralSum = 0;
    private int lastError = 0;

    public double PIDControl(int state, int max) {
        int error = max - state;
        double derivative = (error - lastError) / timer.seconds();
        integralSum += (error * timer.seconds());
        lastError = error;

        timer.reset();

        return (KP * error) + (KI * integralSum) + (KD * derivative);
    }
}
