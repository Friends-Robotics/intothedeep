package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Mecanum;
import friends.helper.Check;
import friends.helper.GamepadEx;

import static friends.helper.GamepadButton.*;

@TeleOp(name="Movement", group="Linear OpMode")
public class MotorTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Create hardware map
        HardwareMap map = new HardwareMap(hardwareMap, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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

        Check check = new Check();

        primary.pressed(TOUCHPAD, (gamepad) -> {
            check.value = !check.value;

            m.PowerMultiplier = check.value ? 1 : 0.5;
            // Red if power is 1, blue if power is 0.5
            gamepad.setLedColor(255 * (check.value ? 1 : 0),
                    0,
                    255 * (check.value ? 0 : 1),
                    -1);
        });

        primary.down(A, () -> map.HorizontalMotor.setPower(1));
        primary.up(A, () -> map.HorizontalMotor.setPower(0));

        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            m.Move(gamepad1);
            primary.update();

            telemetry.addData("Power multiplier", m.PowerMultiplier);

            telemetry.addData("Front Right Direction:", map.FrontRightMotor.getDirection());
            telemetry.addData("Front Left Direction:", map.FrontLeftMotor.getDirection());
            telemetry.addData("Back Right Direction:", map.BackRightMotor.getDirection());
            telemetry.addData("Back Left Direction:", map.BackLeftMotor.getDirection());

            telemetry.update();
        }
    }
}
