package friends.hardwareMap.components;

import com.qualcomm.robotcore.hardware.Servo;

import java.util.Optional;

import friends.hardwareMap.HardwareMap;
import friends.helper.Count;

public class Arm {
    private final Servo rightArmServo;
    private final Servo leftArmServo;
    private final Servo clawServo;
    private final Count viper_target;

    public Arm(HardwareMap map, Optional<Count> t) {
        rightArmServo = map.RightArmServo;
        leftArmServo = map.LeftArmServo;
        clawServo = map.Claw;
        viper_target = t.orElse(new Count());
    }

    /// Scoring Position
    /// Returns New Viper Position
    public void readyToScore() {
        rightArmServo.setPosition(0.8);
        leftArmServo.setPosition(0.2);
        viper_target.value = 0;
    }

    public void score(){
        rightArmServo.setPosition(0.8);
        leftArmServo.setPosition(0.2);
        viper_target.value = 1000;
    }

    /// Wall Position
    /// Returns new viper position
    public void readyToWall() {
        rightArmServo.setPosition(0.18);
        leftArmServo.setPosition(0.82);
        viper_target.value = 200;
    }

    public void fromWall() {
        viper_target.value = 700;
    }

    public void closeClaw(){
        clawServo.setPosition(0.35);
    }

    public void looseClaw() {
        clawServo.setPosition(0.27);
    }

    public void openClaw(){
        clawServo.setPosition(0);
    }
}
