package friends.hardwareMap.components;

import com.qualcomm.robotcore.hardware.Servo;

public class HorizontalExtension {
    private final Servo rightExtendServo;
    private final Servo leftExtendServo;

    private boolean isExtended;

    public HorizontalExtension(Servo right_extension_servo, Servo left_extension_servo) {
        rightExtendServo = right_extension_servo;
        leftExtendServo = left_extension_servo;
        in();
    }
    
    public void in() {
        
    }
    
    public void out() {
        
    }
}