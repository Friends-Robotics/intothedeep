package CtrAltDelete.hardwaremap.components;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private final Servo clawServo;
    private final Servo wristServo;
    private boolean isOpen = false;

    public Claw(Servo claw_servo, Servo wrist_Servo) {
        clawServo = claw_servo;
        isOpen = false;
        clawClose();
        wristServo = wrist_Servo;
    }

    public boolean IsOpen() {
        return isOpen;
    }

    public void clawClose() {
        clawServo.setPosition(0.6);
        isOpen = false;
    }

    public void clawOpen() {
        clawServo.setPosition(0.4);
        isOpen = true;
    }

    public void wallWristPos(){
        wristServo.setPosition(0);
    }

    public void hangWristPos(){
        wristServo.setPosition(1);
    }

    public void setClaw(double pos) {
        clawServo.setPosition(pos);
    }
}