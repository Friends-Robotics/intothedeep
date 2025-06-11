package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Mecanum;
import friends.helper.GamepadEx;

import static friends.helper.GamepadButton.*;

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
                map.Mew);

        telemetry.addData("Status", "Initialised Mecanum");

        GamepadEx primary = new GamepadEx(gamepad1);

        telemetry.addData("Status","Initialised GamepadEx");

        final int[] count = {0};

        primary.bind(A, (c, reader) -> {
            if(!reader.justPressed()) return;

            m.PowerMultiplier = ++count[0] % 2 == 0 ? 1 : 0.5;
        });

        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            m.Move(gamepad1);

            telemetry.addData("Power mulitplier", m.PowerMultiplier);

            primary.update(gamepad1);

            telemetry.update();
        }
    }
}
