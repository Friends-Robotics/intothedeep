package friends.hardwareMap.components;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import friends.hardwareMap.HardwareMap;
import friends.helper.Colours;

public class Intake {
    private final Servo servo;
    private final DcMotorEx drawerMotor;
    private final DcMotorEx intakeMotor;
    private final ColorSensor sensor;

    private Colours targetColour;
    private Colours viewedColour = Colours.NONE;

    public Intake(HardwareMap map){
        this.servo = map.IntakeServo;
        this.sensor = map.ColorSensor;
        this.drawerMotor = map.DrawerSlideMotor;
        this.intakeMotor = map.IntakeMotor;
        this.sensor.enableLed(true);

        targetColour = Colours.RED;

        standby();
    }

    public void standby(){
        servo.setPosition(0.65);
        intakeMotor.setPower(0);
    }

    public void ready() {
        // Get current colour from sensor
        viewedColour = Colours.fromSensor(sensor);

        if(viewedColour == targetColour) {
            standby();
            return;
        } else {
            servo.setPosition(0.35);
        }

        intakeMotor.setPower(0.7);
    }

    public void spit() {
        intakeMotor.setPower(-0.7);
    }

    public void cycle() {
        targetColour = targetColour.next();
    }

    public void slideOutWithSetPower(Gamepad gamepad){
        drawerMotor.setPower(-gamepad.left_stick_y);
    }

    public Colours getTargetColour() {
        return targetColour;
    }

    public Colours getViewedColour() {
        return viewedColour;
    }
}
