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
        return 800;
    }

    public int score(){
        //SCORING POS
        readyAtScoring = false;
        return 100000; // some number
    }

    /// Wall Position
    /// Returns new viper position
    public int readyToWall() {
        rightArmServo.setPosition(1);
        leftArmServo.setPosition(0);
        wristServo.setPosition(1);
        clawServo.setPosition(0);
        readyAtWall = true;
        readyAtScoring = false;
        return 600;
    }

    public int wall(){
        //WALL PICKUP POS (prob just moving vipers up but I'll check)

        readyAtWall = false;
        return 100000; //Some number
    }
    public void openClaw(){
        clawServo.setPosition(1);
    }

    public void closeClaw(){
        clawServo.setPosition(0);
    }

    public int wallAction(){
        if(readyAtWall){return wall();}
        else{return readyToWall();}
    }

    public int scoreAction(){
        if(readyAtScoring){return score();}
        else{return readyToScore();}
    }
}