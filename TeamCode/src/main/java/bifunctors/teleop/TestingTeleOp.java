package bifunctors.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bifunctors.hardwaremap.HardwareMap;
import bifunctors.helper.GamepadEx;
import bifunctors.hardwaremap.components.Mecanum;
import static bifunctors.helper.GamepadEx.primary;
import static bifunctors.helper.GamepadEx.GamepadButton.*;

@TeleOp(name="Testing", group="Linear OpMode")
public class TestingTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Create hardware map
        HardwareMap teamHardwareMap = new HardwareMap(hardwareMap);

        telemetry.addData("Status", "Initialised HardwareMap");

        // Create mecanum drive
        Mecanum m = new Mecanum(
                teamHardwareMap.FrontRightMotor,
                teamHardwareMap.FrontLeftMotor,
                teamHardwareMap.BackRightMotor,
                teamHardwareMap.BackLeftMotor,
                0.5
        );

        telemetry.addData("Status", "Initialised Mecanum");
        telemetry.update();

        // Assign GamepadEx Here

        GamepadEx.initGamepads(gamepad1, gamepad2);

        primary.bind(A, (c, p) -> {
            telemetry.addLine("Pressed Key 'A'");
        });

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // m.Move(gamepad1);

            telemetry.update();
        }
    }
}
