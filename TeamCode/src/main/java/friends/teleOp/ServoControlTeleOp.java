package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import friends.helper.Count;
import friends.helper.gamepad.GamepadEx;

import static friends.helper.gamepad.GamepadButton.*;

@TeleOp(name="Servo Testing", group="Testing")
public class ServoControlTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // CLOSE 0.35
        // OPEN 0.7
        String NAME = "CL";
        Servo servo = hardwareMap.get(Servo.class, NAME);
        GamepadEx primary = new GamepadEx(gamepad1);

        Count pos = new Count();

        primary.pressed(DPAD_DOWN, () -> pos.value -= 0.05);
        primary.pressed(DPAD_UP, () -> pos.value += 0.05);
        primary.pressed(DPAD_LEFT, () -> pos.value -= 0.1);
        primary.pressed(DPAD_RIGHT, () -> pos.value += 0.1);

        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            primary.update();

            pos.value = Math.min(1, pos.value);
            pos.value = Math.max(0, pos.value);
            servo.setPosition(pos.value);

            telemetry.addLine("DPAD UP -> + 0.05");
            telemetry.addLine("DPAD DOWN -> - 0.05");
            telemetry.addLine("DPAD RIGHT -> + 0.1");
            telemetry.addLine("DPAD LEFT -> - 0.1");

            telemetry.addData("Servo Pos", servo.getPosition());

            telemetry.update();
        }
    }
}

