package friends.teleOp;

import static com.qualcomm.robotcore.hardware.Gamepad.LED_DURATION_CONTINUOUS;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import static friends.helper.Colours.YELLOW;
import static friends.helper.gamepad.GamepadButton.*;
import friends.hardwareMap.HardwareMap;
import friends.helper.Colours;
import friends.helper.gamepad.GamepadEx;




// Right Bumper     Set Intake Position 0
// Left Bumper      Set Intake Position 1
// CROSS            Cycle Colour
// CIRCLE           Set Motor Power

@TeleOp(name = "Intake", group = "Testing")
public class IntakeTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap map = new HardwareMap(hardwareMap);
        DcMotor motor = map.GetTestingMotor();

        GamepadEx primary = new GamepadEx(gamepad1);

        Colours[] colour = {YELLOW};

        primary.down(RIGHT_BUMPER, () -> {});
        primary.down(LEFT_BUMPER, () -> {});

        primary.pressed(CROSS, (gamepad) -> {
            colour[0] = colour[0].next();
            gamepad.setLedColor(colour[0].R(), colour[0].G(), colour[0].B(), LED_DURATION_CONTINUOUS);
        });

        primary.down(CIRCLE, () -> {
            Colours sensor_colour = Colours.fromSensor(map.ColorSensor);
            motor.setPower(sensor_colour == colour[0] ? 0.2 : 0);
        });

        waitForStart();

        if (isStopRequested()) return;

        while(opModeIsActive()){
            primary.update();
            telemetry.update();
        }
    }
}
