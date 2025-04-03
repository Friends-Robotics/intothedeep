package bifunctors.hardwaremap.components;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private final Servo clawServo;

    private boolean isOpen = false;

    public Claw(Servo claw_servo) {
        clawServo = claw_servo;
        isOpen = false;
        clawClose();
    }

    public boolean IsOpen() {
        return isOpen;
    }

    public void clawClose() {
        clawServo.setPosition(1);
        isOpen = false;
    }

    public void clawOpen() {
        clawServo.setPosition(0.35);
        isOpen = true;
    }

    public void setClaw(double pos) {
        clawServo.setPosition(pos);
    }
}