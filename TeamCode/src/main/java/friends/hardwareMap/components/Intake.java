package friends.hardwareMap.components;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import friends.hardwareMap.HardwareMap;
import friends.helper.Colours;
import friends.helper.MotorControl.DrawerPIDFConstants;
import friends.helper.MotorControl.PIDController;

public class Intake {
    public Colours ViewedColour = Colours.NONE;
    private final Servo servo;
    private final DcMotorEx drawerMotor;
    private final DcMotorEx intakeMotor;
    private Colours colour;
    private final ColorSensor sensor;
    private boolean pieceHeld;
    private final PIDController pid = new PIDController(DrawerPIDFConstants.KP, DrawerPIDFConstants.KI, DrawerPIDFConstants.KD, DrawerPIDFConstants.tolerance);

    private int target = 0;

    private final int MAX_POSITION = 224;

    public Intake(HardwareMap map){
        this.servo = map.IntakeServo;
        this.sensor = map.ColorSensor;
        this.drawerMotor = map.DrawerSlideMotor;
        this.intakeMotor = map.IntakeMotor;
        this.sensor.enableLed(true);
        colour = Colours.RED;
    }

    public void standby(){
        servo.setPosition(1);
        intakeMotor.setPower(0);
    }

    public void ready() {
        // Get current colour from sensor
        ViewedColour = Colours.fromSensor(sensor);

        if(ViewedColour == colour) {
            pieceHeld = true;
            standby();
            return;
        } else {
            pieceHeld = false;
            servo.setPosition(0);
        }

        intakeMotor.setPower(0.7);
    }

    public void cycle() {
        colour = colour.next();
    }

    public Colours getColour() {
        return colour;
    }

    public void spit() {
        pieceHeld = false;
        intakeMotor.setPower(-1);
    }

    public void slideOut(){
        target = MAX_POSITION;
    }

    public void slideIn(){
        target = 0;
    }

    public void slideToPos(int t){ target = Math.max(Math.min(224, t), 0); }

    /// NEED TO CALL THIS IN THE LOOP
    public void runSlidePID() {
        drawerMotor.setPower(pid.PIDControl(drawerMotor.getCurrentPosition(), target));
    }

    public int getMax() { return MAX_POSITION; }

    //Use this to test eventually
    public void slideOutWithSetPower(double powerFromStick){
        drawerMotor.setPower(powerFromStick * 0.6);
    }
}
