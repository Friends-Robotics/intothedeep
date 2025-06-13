package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static friends.helper.GamepadButton.*;
import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Intake;
import friends.helper.GamepadEx;

@TeleOp(name = "Intake", group = "Testing")
public class IntakeTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        HardwareMap map = new HardwareMap(hardwareMap);
        Intake intake = new Intake(map.IntakeServo, map.IntakeMotor, map.ColorSensor);

        GamepadEx primary = new GamepadEx(gamepad1);

        // KEY => Right Bumper
        // FUN => Set intake to standby position
        primary.down(RIGHT_BUMPER, (gamepad, reader) -> {
            intake.StandbyPosition();
        });

        // KEY => Left Bumper
        // FUN => Set intake to the ready position
        // Uses gamepad1
        primary.down(LEFT_BUMPER, (gamepad, reader) -> {
            intake.ReadyPosition(gamepad1);
        });

        // KEY => Touchpad
        // FUN => Cycles the selected colour
        // Uses gamepad1
        primary.down(TOUCHPAD, (gamepad, reader) -> {
            intake.CycleColours(gamepad1);
        });

        waitForStart();

        if (isStopRequested()) return;

        while(opModeIsActive()){
            primary.update();
            telemetry.update();
        }
    }
}
