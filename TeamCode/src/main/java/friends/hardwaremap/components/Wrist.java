package friends.hardwaremap.components;

import com.qualcomm.robotcore.hardware.Servo;

public class Wrist {
    private final Servo rightArmServo;
    private final Servo leftArmServo;

    private boolean isMax = false;
    private boolean isDown = false;

    public Wrist(Servo right_arm_servo, Servo left_arm_servo) {
        rightArmServo = right_arm_servo;
        leftArmServo = left_arm_servo;

        up();
    }

    public void max() {
        rightArmServo.setPosition(0.035);
        leftArmServo.setPosition(0.035);
        isMax = true;
        isDown = false;
    }

    public void up() {
        rightArmServo.setPosition(0.185);
        leftArmServo.setPosition(0.185);
        isDown = false;
        isMax = false;
    }

    public void down() {
        rightArmServo.setPosition(0.86);
        leftArmServo.setPosition(0.86);
        isDown = true;
        isMax = false;
    }

    public boolean getDown() {
        return isDown;
    }

    public boolean getMax() {
        return isMax;
    }

    public void toggleRotation() {
        if (isMax) {
            up();
            return;
        }

        if(!isDown) {
            down();
        } else {
            up();
        }
    }
}