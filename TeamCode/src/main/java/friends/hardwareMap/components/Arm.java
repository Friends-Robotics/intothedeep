package friends.hardwareMap.components;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import friends.hardwareMap.HardwareMap;

public class Arm {
    private final DcMotorEx rightViper;
    private final DcMotorEx leftViper;
    private final Servo rightArmServo;
    private final Servo leftArmServo;
    private final Servo wristServo;
    private final Servo clawServo;

    public Arm(HardwareMap map) {
        rightViper = map.RightViperMotor;
        leftViper = map.LeftViperMotor;
        rightArmServo = map.RightArmServo;
        leftArmServo = map.LeftArmServo;
        wristServo = map.Wrist;
        clawServo = map.Claw;
    }

    /// Scoring Position
    /// Returns New Viper Position
    public int scoring() {
        rightArmServo.setPosition(0.3);
        leftArmServo.setPosition(0.7);
        wristServo.setPosition(0);
        clawServo.setPosition(0);

        return 800;
    }

    /// Wall Position
    /// Returns new viper position
    public int wall() {
        rightArmServo.setPosition(1);
        leftArmServo.setPosition(0);
        wristServo.setPosition(1);
        clawServo.setPosition(0);
        return 600;
    }
}