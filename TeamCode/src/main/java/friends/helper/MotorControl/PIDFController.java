package friends.helper.MotorControl;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDFController extends PIDController {
    double KF;

    public PIDFController(double kp, double ki, double kd, double kf, double tolerance) {
        super(kp, ki, kd, tolerance);
        KF = kf;
    }

    public PIDFController(double kp, double ki, double kd, double kf) {
        super(kp, ki, kd);
        KF = kf;
    }

    @Override
    public double PIDControl(int state, int max) {
        return KF + super.PIDControl(state, max);
    }
    public double feedForwardControl(){
        return KF;
    }
}