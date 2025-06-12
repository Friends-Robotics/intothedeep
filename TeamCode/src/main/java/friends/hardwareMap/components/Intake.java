package friends.hardwareMap.components;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private final Servo intakeServo;
    private final DcMotorEx intakeMotor;
    private final ColorSensor colorSensor;
    private String searchingColor;
    private boolean pieceHeld;

    public Intake(Servo servo, DcMotorEx motor, ColorSensor sensor){
        intakeServo = servo;
        intakeMotor = motor;
        colorSensor = sensor;
        colorSensor.enableLed(true);
    }

    public void StandbyPosition(){
        intakeServo.setPosition(1);
        intakeMotor.setPower(0);
    }

    public void ReadyPosition(Gamepad gp){
        if(pieceHeld){
            if(DetermineColour(GetHSV()).equals(searchingColor) && !gp.cross){
                intakeServo.setPosition(0.5);
                intakeMotor.setPower(-0.9);
            }
            pieceHeld = false;
        }
        else {
            if(!DetermineColour(GetHSV()).equals(searchingColor) && !gp.cross) {
                intakeServo.setPosition(0);
                intakeMotor.setPower(0.9);
            }
            pieceHeld = true;
        }
        StandbyPosition();
    }

    public float[] GetHSV(){
        int r = colorSensor.red();
        int b = colorSensor.blue();
        int g = colorSensor.green();

        float[] hsv = new float[3];

        Color.RGBToHSV(r,g,b, hsv);
        return hsv;
    }

    private String DetermineColour(float[] hsv){
        float hue = hsv[0];
        float sat = hsv[1];
        float val = hsv[2];

        if (sat < 0.3 || val < 0.2) {
            return "none";
        }

        if (hue < 30 || hue > 330) {
            return "red";
        } else if (hue >= 30 && hue <= 70) {
            return "yellow";
        } else if (hue >= 180 && hue <= 250) {
            return "blue";
        } else {
            return "none";
        }
    }

    public void CycleColours(Gamepad gp){
        switch(searchingColor){
            case "red":
                gp.setLedColor(0, 0, 255, Gamepad.LED_DURATION_CONTINUOUS);
                searchingColor = "blue";
                break;
            case "blue":
                gp.setLedColor(255, 255, 0, Gamepad.LED_DURATION_CONTINUOUS);
                searchingColor = "yellow";
                break;
            case "yellow":
                gp.setLedColor(255, 0, 0, Gamepad.LED_DURATION_CONTINUOUS);
                searchingColor = "red";
                break;
            default:
                searchingColor = "none";
            break;
        }
    }
}
