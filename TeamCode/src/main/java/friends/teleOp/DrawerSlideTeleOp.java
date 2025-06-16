package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.*;

import friends.hardwareMap.HardwareMap;
import friends.helper.gamepad.GamepadEx;
import static friends.helper.gamepad.GamepadButton.*;

@TeleOp(name="Drawer Slide", group="Testing")
public class DrawerSlideTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap map = new HardwareMap(hardwareMap, RUN_WITHOUT_ENCODER);

        // Use FrontRightWheel For Testing
        DcMotorEx motor = map.FrontRightMotor;

        // Optional: Set target position tolerance if needed
        motor.setTargetPositionTolerance(10);

        GamepadEx primary = new GamepadEx(gamepad1);

        // KEY => A
        // FUN => Changes Motor Mode to RUN_TO_POSITION and waits until arrived
        primary.pressed(A, () -> {
            // Why is this -1?
            motor.setTargetPosition(-1);
            motor.setMode(RUN_TO_POSITION);
            motor.setPower(0.9);

            // Gracefully terminate if op mode is stopped
            while(motor.isBusy() && opModeIsActive()) {
                telemetry.addData("Target Position", motor.getTargetPosition());
                telemetry.addData("Current Position", motor.getCurrentPosition());
                telemetry.update();
            }

            motor.setPower(0);
            motor.setMode(RUN_WITHOUT_ENCODER);
        });

        // KEY => Left Stick
        // FUN => Sets the power of the motor to the left stick position
        primary.down(LEFT_STICK, (gamepad) ->
                motor.setPower(-gamepad.left_stick_x));

        waitForStart();

        while (opModeIsActive()) {
            primary.update();

            telemetry.addData("Current position", motor.getCurrentPosition());
            telemetry.addData("Gamepad2 cross", gamepad2.cross);
            telemetry.addData("Busy status", motor.isBusy());
            telemetry.addData("Target position tolerance", motor.getTargetPositionTolerance());
            telemetry.update();
        }
    }
}
