package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import friends.hardwareMap.HardwareMap;
import friends.helper.GamepadEx;
import friends.hardwareMap.components.Mecanum;

import static friends.helper.GamepadButton.*;

@TeleOp(name="Competition", group="Linear OpMode")
public class CompetitionTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Create hardware map
        // HardwareMap map = new HardwareMap(hardwareMap);

        telemetry.addData("Status", "Initialised HardwareMap");

        // Create mecanum drive
//        Mecanum m = new Mecanum(map.FrontRightMotor,
//                map.BackRightMotor,
//                map.BackLeftMotor,
//                map.FrontLeftMotor,
//                1,
//                map.Mew);

        telemetry.addData("Status", "Initialised Mecanum");
        telemetry.update();

        GamepadEx primary = new GamepadEx(gamepad1);

        primary.bind(A, (c, reader) -> {
            telemetry.addLine("A Pressed");
            gamepad1.setLedColor(255, 0, 0, -1);
        });

        primary.bindAlt(A, (c, reader) -> {
            telemetry.addLine("A Not Pressed");
            gamepad1.setLedColor(0, 255, 0, -1);
        });

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // m.Move(gamepad1);

            primary.update();

            telemetry.update();
        }
    }
}
