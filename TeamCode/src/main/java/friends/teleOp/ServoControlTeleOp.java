package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Mecanum;
import friends.helper.Check;
import friends.helper.Count;
import friends.helper.GamepadEx;

import static friends.helper.GamepadButton.*;

@TeleOp(name="Servo Control", group="Testing")
public class ServoControlTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        String NAME = "PLACEHOLDER";
        Servo servo = hardwareMap.get(Servo.class, NAME);
        GamepadEx primary = new GamepadEx(gamepad1);

        Count pos = new Count();

        primary.pressed(DPAD_DOWN, (gamepad, reader) -> {
            pos.value -= 0.05;
            pos.value = Math.max(0, pos.value);
            servo.setPosition(pos.value);
        }) ;

        primary.pressed(DPAD_UP, (gamepad, reader) -> {
            pos.value += 0.05;
            pos.value = Math.min(1, pos.value);
            servo.setPosition(pos.value);
        }) ;


        primary.pressed(DPAD_LEFT, (gamepad, reader) -> {
            pos.value -= 0.1;
            pos.value = Math.max(0, pos.value);
            servo.setPosition(pos.value);
        }) ;

        primary.pressed(DPAD_RIGHT, (gamepad, reader) -> {
            pos.value += 0.1;
            pos.value = Math.min(1, pos.value);
            servo.setPosition(pos.value);
        }) ;


        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            primary.update();

            telemetry.addLine("DPAD UP -> + 0.05");
            telemetry.addLine("DPAD DOWN -> - 0.05");
            telemetry.addLine("DPAD RIGHT -> + 0.1");
            telemetry.addLine("DPAD LEFT -> - 0.1");

            telemetry.addData("Servo Pos", servo.getPosition());

            telemetry.update();
        }
    }
}

