package CtrAltDelete.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import CtrAltDelete.hardwaremap.CtrlAltDefeatHardwareMap;

@TeleOp(name = "ColourSensor", group = "Robot")
public class ColourSensor extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        CtrlAltDefeatHardwareMap map = new CtrlAltDefeatHardwareMap(hardwareMap);
        int searchingColour = CycleColours(0);
        Gamepad previousGamepad2 = new Gamepad();
        map.ColorSensor.enableLed(true);
        waitForStart();
        while(opModeIsActive()){
            previousGamepad2.copy(gamepad2);

            int r = map.ColorSensor.red();
            int b = map.ColorSensor.blue();
            int g = map.ColorSensor.green();

            double r_scaled = (double)r / (r + g + b) * 255;
            double g_scaled = (double)g / (r + g + b) * 255;
            double b_scaled = (double) b / (r + g + b) * 255;

            if(gamepad2.ps && !previousGamepad2.ps){
                searchingColour = CycleColours(searchingColour);
            }

            if(gamepad2.right_trigger > 0){
                if(map.DetermineColour(r_scaled, g_scaled, b_scaled) == searchingColour){
                    map.test.setPower(0);
                }
                else{
                    map.test.setPower(0.2);
                }
            }

            telemetry.addData("light ", ((OpticalDistanceSensor)map.ColorSensor).getLightDetected());
            telemetry.addData("red ", r_scaled);
            telemetry.addData("blue ", b_scaled);
            telemetry.addData("green ", g_scaled);
            telemetry.update();

            //red: r > 120 -> returns 1

            //green: r > 90, g > 80 -> returns 0

            //blue: b > 120 -> returns -1

            //NONE: returns -2
        }
    }
    private int CycleColours(int currentColour){
        switch(currentColour){
            case 1:
                gamepad2.setLedColor(0, 0, 255, Gamepad.LED_DURATION_CONTINUOUS);
                return -1;
            case -1:
                gamepad2.setLedColor(255, 255, 0, Gamepad.LED_DURATION_CONTINUOUS);
                return 0;
            case 0:
                gamepad2.setLedColor(255, 0, 0, Gamepad.LED_DURATION_CONTINUOUS);
                return 1;
            default: return -2;
        }
    }
}
