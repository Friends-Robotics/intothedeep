package bifunctors.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bifunctors.hardwaremap.HardwareMap;
import bifunctors.helper.GamepadEx;
import bifunctors.helper.Mecanum;
import static bifunctors.helper.GamepadEx.primary;
import static bifunctors.helper.GamepadEx.secondary;
import static bifunctors.helper.GamepadEx.GamepadButton.*;

    /*
        -----------------------------------------------------------------------
        | Gamepad Input     | Result                | Affected Components     |
        -----------------------------------------------------------------------

        -----------------------------------------------------------------------
        | RIGHT STICK       |                       |                         |
        -----------------------------------------------------------------------
        | LEFT STICK        |                       |                         |
        -----------------------------------------------------------------------
        | A / CROSS         |                       |                         |
        --------------------+-----------------------+--------------------------
        | B / CIRCLE        |                       |                         |
        --------------------+-----------------------+--------------------------
        | X / SQUARE        |                       |                         |
        --------------------+-----------------------+--------------------------
        | Y / TRIANGLE      |                       |                         |
        --------------------+-----------------------+--------------------------
        | RIGHT BUMPER      |                       |                         |
        --------------------+-----------------------+--------------------------
        | LEFT BUMPER       |                       |                         |
        --------------------+-----------------------+--------------------------
        | RIGHT TRIGGER     |                       |                         |
        --------------------+-----------------------+--------------------------
        | LEFT TRIGGER      |                       |                         |
        --------------------+-----------------------+--------------------------
    */

@TeleOp(name="Competition", group="Linear OpMode")
public class CompetitionTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Create hardware map
        HardwareMap teamHardwareMap = new HardwareMap(hardwareMap);

        telemetry.addData("Status", "Initialised HardwareMap");

        // Create mecanum drive
        Mecanum m = Mecanum.Init(
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
