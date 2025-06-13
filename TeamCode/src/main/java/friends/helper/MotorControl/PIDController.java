package friends.helper.MotorControl;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDController {
    double KP;
    double KI;
    double KD;

    public PIDController(double kp, double ki, double kd) {
        this.KP = kp;
        this.KI = ki;
        this.KD = kd;
    }

    ElapsedTime timer = new ElapsedTime();
    private double integralSum = 0;
    private int lastError = 0;

    public double PIDControl(Telemetry t, int state, int max) {
        int error = max - state;
        double derivative = (error - lastError) / timer.seconds();
        integralSum += (error * timer.seconds());
        lastError = error;

        t.addData("ERROR", error);
        t.addData("derivative", derivative);
        t.addData("integralSum", integralSum);

        timer.reset();

        return (KP * error) + (KI * integralSum) + (KD * derivative);
    }
}
