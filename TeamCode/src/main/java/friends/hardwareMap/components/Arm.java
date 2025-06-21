package friends.hardwareMap.components;

import com.qualcomm.robotcore.hardware.Servo;

import java.util.Optional;

import friends.hardwareMap.HardwareMap;
import friends.helper.Count;

public class Arm {
    private final Servo rightArmServo;
    private final Servo leftArmServo;
    private final Servo wristServo;
    private final Servo clawServo;
    private final Count target;

    public Arm(HardwareMap map, Optional<Count> t) {
        rightArmServo = map.RightArmServo;
        leftArmServo = map.LeftArmServo;
        wristServo = map.Wrist;
        clawServo = map.Claw;
        target = t.orElse(new Count());

        scoreWrist();
    }

    /// Scoring Position
    /// Returns New Viper Position
    public void readyToScore() {
        rightArmServo.setPosition(0.8);
        leftArmServo.setPosition(0.2);
        target.value = 0;
    }

    public void score(){
        rightArmServo.setPosition(0.8);
        leftArmServo.setPosition(0.2);
        target.value = 900;
    }

    /// Wall Position
    /// Returns new viper position
    public void readyToWall() {
        rightArmServo.setPosition(0.18);
        leftArmServo.setPosition(0.82);
        target.value = 300;
    }

    public void wall() {
        target.value = 700;
    }

    public void closeClaw(){ clawServo.setPosition(0); }
    public void openClaw(){
        clawServo.setPosition(1);
    }

    public void wallWrist() {
        wristServo.setPosition(0);
    }
    public void scoreWrist() {
        wristServo.setPosition(1);
    }
}
