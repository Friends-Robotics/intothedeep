package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import friends.hardwareMap.CtrlAltDefeatHardwareMap;

@Disabled
@TeleOp
public class DrawerSlideTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        CtrlAltDefeatHardwareMap map = new CtrlAltDefeatHardwareMap(hardwareMap);

        // Optional: Set target position tolerance if needed
        map.test.setTargetPositionTolerance(10); // Ticks

        // Default to manual control mode
        map.test.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            // Manual control using left stick Y (invert as needed)
            map.test.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            map.test.setPower(0.9 * -gamepad2.left_stick_y);

            // When CROSS (X/A) is pressed, go to target position
            if (gamepad2.cross) {
                // Set the target position
                map.test.setTargetPosition(-1); // Adjust this target value as needed

                // Switch to RUN_TO_POSITION mode
                map.test.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                // Set power
                map.test.setPower(0.9);

                // Wait until motor reaches the position
                while (opModeIsActive() && map.test.isBusy()) {
                    telemetry.addData("Moving to target", map.test.getTargetPosition());
                    telemetry.addData("Current position", map.test.getCurrentPosition());
                    telemetry.update();
                    sleep(50); // Reduce loop spam
                }

                // Stop the motor
                map.test.setPower(0);

                // Switch back to manual control mode
                map.test.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            // Show telemetry
            telemetry.addData("Current position", map.test.getCurrentPosition());
            telemetry.addData("Gamepad2 cross", gamepad2.cross);
            telemetry.addData("Is busy", map.test.isBusy());
            telemetry.addData("Target pos tolerance", map.test.getTargetPositionTolerance());
            telemetry.update();
        }
    }
}
