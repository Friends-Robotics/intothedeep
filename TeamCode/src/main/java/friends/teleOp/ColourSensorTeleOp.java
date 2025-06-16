package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import static com.qualcomm.robotcore.hardware.Gamepad.LED_DURATION_CONTINUOUS;

import static friends.helper.gamepad.GamepadButton.*;
import static friends.helper.Colours.*;
import friends.hardwareMap.HardwareMap;
import friends.helper.Colours;
import friends.helper.gamepad.GamepadEx;

@TeleOp(name = "Colour Sensor", group = "Testing")
public class ColourSensorTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap map = new HardwareMap(hardwareMap);

        DcMotorEx motor = (DcMotorEx)map.GetTestingMotor();

        // Must be an array for cheeky ref type
        Colours[] colour = {YELLOW};

        GamepadEx primary = new GamepadEx(gamepad1);

        // KEY => Touchpad
        // FUN => Cycles the colour
        primary.pressed(TOUCHPAD, (gamepad) -> {
            colour[0] = colour[0].next();
            gamepad.setLedColor(colour[0].R(), colour[0].G(), colour[0].B(), LED_DURATION_CONTINUOUS);
        });

        // KEY => Right Trigger
        // FUN => Sets the power if colour is found
        primary.down(RIGHT_TRIGGER, () -> {
            Colours sensor_colour = Colours.fromSensor(map.ColorSensor);
            motor.setPower(sensor_colour == colour[0] ? 0.2 : 0);
        });

        waitForStart();
        if (isStopRequested()) return;

        while(opModeIsActive()){
            primary.update();

            telemetry.addData("Current Searching Colour", colour[0].name());
            telemetry.update();
        }
    }
}
