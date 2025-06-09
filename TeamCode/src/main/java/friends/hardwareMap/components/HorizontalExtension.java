package friends.hardwareMap.components;

import com.qualcomm.robotcore.hardware.Servo;

public class HorizontalExtension {
    private final Servo rightExtendServo;
    private final Servo leftExtendServo;

    private boolean isExtended;

    public HorizontalExtension(Servo right_extension_servo, Servo left_extension_servo) {
        rightExtendServo = right_extension_servo;
        leftExtendServo = left_extension_servo;
        extensionIn();
    }

    public boolean IsExtended() {
        return isExtended;
    }

    public void toggleExtension() {
        if(!isExtended) extensionOut();
        else extensionIn();
    }

    public void setExtension(double percentage) {
        rightExtendServo.setPosition(0.4 * percentage);
        isExtended = percentage != 0;
    }

    public void extensionOut() {
        rightExtendServo.setPosition(0.4);
        leftExtendServo.setPosition(0.4);
        isExtended = true;
    }

    public void extensionIn() {
        rightExtendServo.setPosition(0);
        leftExtendServo.setPosition(0);
        isExtended = false;
    }
}