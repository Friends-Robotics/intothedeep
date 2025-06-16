package friends.hardwareMap.components;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import friends.hardwareMap.HardwareMap;

public class Arm {
    private final Servo rightArmServo;
    private final Servo leftArmServo;
    private final Servo wristServo;
    private final Servo clawServo;

    public Arm(HardwareMap map) {
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
        clawServo.setPosition(1);
        return 800;
    }

    /// TODO PRE WALL POS NEEDED

    /// Wall Position
    /// Returns new viper position
    public int wall() {
        rightArmServo.setPosition(1);
        leftArmServo.setPosition(0);
        wristServo.setPosition(1);
        clawServo.setPosition(0);
        return 600;
    }

    public void openClaw(){
        clawServo.setPosition(1);
    }

    public void closeClaw(){
        clawServo.setPosition(0);
    }
}