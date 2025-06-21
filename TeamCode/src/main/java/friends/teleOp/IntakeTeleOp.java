package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static friends.helper.gamepad.GamepadButton.*;

import android.graphics.Color;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Intake;
import friends.helper.Colours;
import friends.helper.gamepad.GamepadEx;

@TeleOp(name = "Intake Testing", group = "Testing")
public class IntakeTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {
        HardwareMap map = new HardwareMap(hardwareMap);
        Intake intake = new Intake(map);

        GamepadEx primary = new GamepadEx(gamepad1);

        primary.pressed(RIGHT_BUMPER, intake::slideOut);
        primary.pressed(LEFT_BUMPER, intake::slideIn);
        primary.pressed(CROSS, intake::cycle);

        primary.down(CIRCLE, intake::ready);
        primary.down(TOUCHPAD, intake::spit);

        primary.up(CIRCLE, (gamepad) -> {
            if(gamepad.touchpad) return;
            intake.standby();
        });

        waitForStart();

        if (isStopRequested()) return;

        while(opModeIsActive()){
            primary.update();
            primary.setColour(intake.getColour());

            float[] hsv = new float[3];
            Color.RGBToHSV(map.ColorSensor.red(), map.ColorSensor.blue(), map.ColorSensor.green(), hsv);

            telemetry.addData("Currently Viewed Colour", Colours.fromSensor(map.ColorSensor).toString());

            telemetry.addLine("Raw Values");
            telemetry.addData("R:", map.ColorSensor.red());
            telemetry.addData("G:", map.ColorSensor.green());
            telemetry.addData("B:", map.ColorSensor.blue());
            telemetry.addData("Luminance:", map.ColorSensor.alpha());

            telemetry.addLine("HSV Values");
            telemetry.addData("H:", hsv[0]);
            telemetry.addData("S:", hsv[1]);
            telemetry.addData("V:", hsv[2]);

            telemetry.addData("Currently Selected Colour", intake.getColour().toString());


            intake.runSlidePID();

            telemetry.update();
        }
    }
}
