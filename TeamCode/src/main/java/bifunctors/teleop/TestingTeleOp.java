package bifunctors.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bifunctors.hardwaremap.HardwareMap;
import bifunctors.hardwaremap.TestingHardwareMap;
import bifunctors.helper.GamepadEx;
import bifunctors.hardwaremap.components.Mecanum;
import static bifunctors.helper.GamepadEx.primary;
import static bifunctors.helper.GamepadEx.GamepadButton.*;

@TeleOp(name="Testing", group="OpMode")
public class TestingTeleOp extends OpMode {

    private final TestingHardwareMap teamHardwareMap = new TestingHardwareMap(hardwareMap);
    private Mecanum m;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialised HardwareMap");

        // Create mecanum drive
        m = new Mecanum(
                teamHardwareMap.FrontRightWheel,
                teamHardwareMap.FrontLeftWheel,
                teamHardwareMap.BackRightWheel,
                teamHardwareMap.BackLeftWheel,
                0.5
        );

        telemetry.addData("Status", "Initialised Mecanum");
        telemetry.update();

        // Assign GamepadEx Here
        GamepadEx.initGamepads(gamepad1, gamepad2);

        primary.bind(A, (c, p) -> {
            telemetry.addLine("Pressed Key 'A'");
        });

    }

    @Override
    public void loop() {
        m.Move(gamepad1);

        telemetry.update();
    }
}
