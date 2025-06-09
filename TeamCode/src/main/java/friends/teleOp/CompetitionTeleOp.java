package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import friends.hardwareMap.HardwareMap;
import friends.helper.GamepadEx;
import friends.hardwareMap.components.Mecanum;
import static friends.helper.GamepadEx.primary;
import static friends.helper.GamepadEx.GamepadButton.*;

@TeleOp(name="Competition", group="Linear OpMode")
public class CompetitionTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Create hardware map
        HardwareMap map = new HardwareMap(hardwareMap);

        telemetry.addData("Status", "Initialised HardwareMap");

        // Create mecanum drive
        Mecanum m = new Mecanum(map.FrontRightMotor,
                map.BackRightMotor,
                map.BackLeftMotor,
                map.FrontLeftMotor,
                1,
                map.Imu);

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
            m.Move(gamepad1);

            telemetry.update();
        }
    }
}
