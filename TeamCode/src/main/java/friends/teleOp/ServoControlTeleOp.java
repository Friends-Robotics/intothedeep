package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import friends.helper.Count;
import friends.helper.gamepad.GamepadEx;
import pedroPathing.examples.Triangle;

import static friends.helper.gamepad.GamepadButton.*;

@TeleOp(name="Servo Testing", group="Testing")
public class ServoControlTeleOp extends LinearOpMode {
    final String servoName = "LHS";

    @Override
    public void runOpMode() {
        Servo servo = hardwareMap.get(Servo.class, servoName);
        GamepadEx primary = new GamepadEx(gamepad1);

        Count pos = new Count();

        primary.pressed(DPAD_DOWN, () -> pos.value -= 0.05);
        primary.pressed(DPAD_UP, () -> pos.value += 0.05);
        primary.pressed(DPAD_LEFT, () -> pos.value -= 0.1);
        primary.pressed(DPAD_RIGHT, () -> pos.value += 0.1);
        primary.pressed(TRIANGLE, () -> pos.value += 0.01);
        primary.pressed(CROSS, () -> pos.value -= 0.01);

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
            telemetry.addLine("TRIANGLE -> + 0.01");
            telemetry.addLine("CROSS LEFT -> - 0.01");

            telemetry.addData("Servo Pos", servo.getPosition());

            telemetry.update();
        }
    }
}

