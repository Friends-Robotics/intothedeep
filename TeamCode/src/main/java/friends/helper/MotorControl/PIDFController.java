package friends.helper.MotorControl;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDFController extends PIDController {
    double KF;
    public PIDFController(double kp, double ki, double kd, double kf)
    {
        super(kp, ki, kd);
        KF = kf;
    }

    @Override
    public double PIDControl(Telemetry telemetry, int state, int max) {
        return KF + super.PIDControl(telemetry, state, max);
    }
    public double FeedForwardControl(){
        return KF;
    }
}