package friends.hardwareMap.components;

import friends.hardwareMap.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;

public class Hang {
    private final Servo leftHangServo;
    private final Servo rightHangServo;

    public Hang(HardwareMap map) {
        this.leftHangServo = map.LeftHangServo;
        this.rightHangServo = map.RightHangServo;
    }

    public void setLatch() {
        leftHangServo.setPosition(0.1);
        rightHangServo.setPosition(0.21);
    }

    public void setUnlatch() {
        leftHangServo.setPosition(0.35);
        rightHangServo.setPosition(0.08);
    }

    public void powerOff() {
        if (leftHangServo instanceof PwmControl) ((PwmControl)leftHangServo).setPwmDisable();
        if (rightHangServo instanceof PwmControl) ((PwmControl)rightHangServo).setPwmDisable();
    }

    public void powerOn() {
        if (leftHangServo instanceof PwmControl) ((PwmControl)leftHangServo).setPwmEnable();
        if (rightHangServo instanceof PwmControl) ((PwmControl)rightHangServo).setPwmEnable();
    }
}
