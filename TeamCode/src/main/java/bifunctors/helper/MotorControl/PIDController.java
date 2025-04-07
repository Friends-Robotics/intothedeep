package bifunctors.helper.MotorControl;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    double KP;
    double KI;
    double KD;
    public PIDController(double KP, double KI, double KD) {
        this.KP = KP;
        this.KI = KI;
        this.KD = KD;
    }

    ElapsedTime timer = new ElapsedTime();
    double integralSum = 0;
    int lastError = 0;

    public double PIDControl(int state, int reference){
        int error = reference - state;
        double derivative = (error - lastError) /timer.seconds();
        integralSum += (error * timer.seconds());
        lastError = error;

        timer.reset();

        return (KP * error) + (KI * integralSum) + (KD * derivative);
    }
}
