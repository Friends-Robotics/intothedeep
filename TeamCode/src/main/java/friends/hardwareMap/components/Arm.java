package friends.hardwareMap.components;

import com.qualcomm.robotcore.hardware.Servo;

import friends.hardwareMap.HardwareMap;

public class Arm {
    private final Servo rightArmServo;
    private final Servo leftArmServo;
    private final Servo wristServo;
    private final Servo clawServo;
    private Boolean readyAtWall;
    private Boolean readyAtScoring;

    public Arm(HardwareMap map) {
        rightArmServo = map.RightArmServo;
        leftArmServo = map.LeftArmServo;
        wristServo = map.Wrist;
        clawServo = map.Claw;
    }

    /// Scoring Position
    /// Returns New Viper Position
    public int readyToScore() {
        rightArmServo.setPosition(0.3);
        leftArmServo.setPosition(0.7);
        wristServo.setPosition(0);
        clawServo.setPosition(1);
        readyAtScoring = true;
        readyAtWall = false;
        return 0;
    }

    public int score(){
        rightArmServo.setPosition(0.25);
        leftArmServo.setPosition(0.75);
        readyAtScoring = false;
        return 0; // some number
    }

    /// Wall Position
    /// Returns new viper position
    public int readyToWall() {
        rightArmServo.setPosition(0.95);
        leftArmServo.setPosition(0.05);
        wristServo.setPosition(1);
        clawServo.setPosition(0);
        readyAtWall = true;
        readyAtScoring = false;
        return 600;
    }

    public int wall(){
        readyAtWall = false;
        return 1000; //Some number
    }
    public void openClaw(){
        clawServo.setPosition(1);
    }

    public void closeClaw(){
        clawServo.setPosition(0);
    }

    public int wallAction(){
        return readyAtwall ? wall() : readyToWall();
    }

    public int scoreAction(){
        return readyAtScoring ? score() : readyToScore();
    }
}
