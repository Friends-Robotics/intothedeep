package friends.teleop;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import friends.hardwaremap.CtrlAltDefeatHardwareMap;

@TeleOp(name = "ColourSensor", group = "Robot")
public class ColourSensor extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        CtrlAltDefeatHardwareMap map = new CtrlAltDefeatHardwareMap(hardwareMap);
        String searchingColour = CycleColours("yellow");
        Gamepad previousGamepad2 = new Gamepad();

        waitForStart();
        while(opModeIsActive()){
            previousGamepad2.copy(gamepad2);

            int r = map.ColorSensor.red();
            int b = map.ColorSensor.blue();
            int g = map.ColorSensor.green();

            float[] hsv = new float[3];

            Color.RGBToHSV(r,g,b, hsv);

            if(gamepad2.touchpad && !previousGamepad2.touchpad){
                searchingColour = CycleColours(searchingColour);
            }

            if(gamepad2.right_trigger > 0){
                if(DetermineColour(hsv).equals(searchingColour)){
                    map.DrawerSlide.setPower(0);
                }
                else{
                    map.DrawerSlide.setPower(0.2);
                }
            }

            telemetry.addData("light ", ((OpticalDistanceSensor)map.ColorSensor).getLightDetected());
            telemetry.addData("red ", r);
            telemetry.addData("blue ", b);
            telemetry.addData("green ", g);
            telemetry.addData("hue", hsv[0]);
            telemetry.addData("saturation", hsv[1]);
            telemetry.addData("value", hsv[2]);
            telemetry.update();

            //red: r > 120 -> returns 1

            //green: r > 90, g > 80 -> returns 0

            //blue: b > 120 -> returns -1

            //NONE: returns -2
        }
    }
    private String CycleColours(String currentColour){
        switch(currentColour){
            case "red":
                gamepad2.setLedColor(0, 0, 255, Gamepad.LED_DURATION_CONTINUOUS);
                return "blue";
            case "blue":
                gamepad2.setLedColor(255, 255, 0, Gamepad.LED_DURATION_CONTINUOUS);
                return "yellow";
            case "yellow":
                gamepad2.setLedColor(255, 0, 0, Gamepad.LED_DURATION_CONTINUOUS);
                return "red";
            default: return "none";
        }
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
}
