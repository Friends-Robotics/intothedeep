package bifunctors.helper.MotorControl;

public class SlidePIDFController extends PIDController{
    double KF;
    public SlidePIDFController(double KP, double KI, double KD, double KF)
    {
        super(KP, KI, KD);
        this.KF = KF;
    }
    public double FeedForwardControl(){
        return KF; //I know this looks dumb but there's other versions of PIDF controllers that don't have this simple of a calculation so I'm fine with this.
    }
}
