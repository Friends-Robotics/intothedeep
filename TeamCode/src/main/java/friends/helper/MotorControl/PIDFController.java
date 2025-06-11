package friends.helper.MotorControl;

public class PIDFController extends PIDController {
    double KF;
    public PIDFController(PIDFConstants constants)
    {
        super(constants);
        KF = constants.KF;
    }
    public double FeedForwardControl(){
        return KF;
    }
}