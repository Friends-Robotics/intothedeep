package friends.hardwaremap.components;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import friends.helper.MotorControl.DrawerPIDFConstants;
import friends.helper.MotorControl.PIDController;

public class Intake {

//    private final Servo intakeServo;
    private final DcMotorEx drawerMotor;
    private final DcMotorEx intakeMotor;
    private final ColorSensor colorSensor;
    private String searchingColor = "red";
    private boolean pieceHeld;
    private final PIDController drawerPID = new PIDController(DrawerPIDFConstants.KP, DrawerPIDFConstants.KI, DrawerPIDFConstants.KD);

    public Intake(Servo s, DcMotorEx m, ColorSensor c, DcMotorEx dm){
//        intakeServo = s;
        intakeMotor = m;
        colorSensor = c;
        colorSensor.enableLed(true);
        drawerMotor = dm;
    }

    //NEEDS TO BE LOOPED
    public void StandbyPosition(){
        //intakeServo.setPosition(1);
        intakeMotor.setPower(0);
        drawerMotor.setPower(drawerPID.PIDControl(drawerMotor.getCurrentPosition(), 0));
    }

    //NEEDS TO BE LOOPED
    public void ReadyPosition(Gamepad gp){
//        drawerMotor.setPower(drawerPID.PIDControl(drawerMotor.getCurrentPosition(), 80));
        if(!pieceHeld) {
            intakeMotor.setPower(0.5);
            if (DetermineColour(GetHSV()).equals(searchingColor)) {
                pieceHeld = true;
                StandbyPosition();
            }
        }
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

        if (hue < 20 || hue > 340) {
            return "red";
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
                gp.setLedColor(255,0, 0, Gamepad.LED_DURATION_CONTINUOUS);
                searchingColor = "red";
                break;
            default:
                searchingColor = "none";
            break;
        }
    }

    public void SendTelemetry(Telemetry telemetry){
        telemetry.addLine("Colour Sensor")
                .addData("Piece Held: ", pieceHeld)
                .addData("Searching Colour: ", searchingColor);
//        telemetry.addLine("Servo")
//                .addData("Position: ", intakeServo.getPosition());
        telemetry.addLine("DC Motor")
                .addData("Power: ", intakeMotor.getPower());
    }

    public void SpinMotor(){
        pieceHeld = false;
        intakeMotor.setPower(-0.9);
    }
}
