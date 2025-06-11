package friends.helper.MotorControl;

public class PIDFConstants {
    public double KP = 0;
    public double KI = 0;
    public double KD = 0;
    public double KF = 0;

    public PIDFConstants(double kp, double ki, double kd, double kf) {
        KP = kp;
        KI = ki;
        KD = kd;
        KF = kf;
    }

    public PIDFConstants() {
        KP = 0;
        KI = 0;
        KD = 0;
        KF = 0;
    }
}
