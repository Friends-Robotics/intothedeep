package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.ComponentState;
import friends.helper.GamepadEx;
import static friends.helper.GamepadButton.*;

@TeleOp(name = "UP THE QUAKERS", group = "Testing")
public class UpTheBossTeleOp extends LinearOpMode {

    @Override
    public void runOpMode(){
        HardwareMap map = new HardwareMap(hardwareMap);

        Servo right_arm = map.RightArmServo;
        Servo left_arm = map.LeftArmServo;

        Servo claw = map.Claw;
        Servo wrist = map.Wrist;

        float ARM_PICKUP = 1f;
        float ARM_DEPOSIT = 0.3f;

        float CLAW_OPEN = 0;
        float CLAW_CLOSE = 1;

        float WRIST_UP = 0;
        float WRIST_DOWN = 1;

        GamepadEx primary = new GamepadEx(gamepad1);

        ///  MACRO ONE   : Pickup Position -> Open Claw
        ///  MACRO TWO   : Close Claw -> Flip Servo -> Deposit Position

        // MACRO ONE
        primary.pressed(CROSS, (gamepad, reader) -> {
            // Set pickup position for both arm and claw
            right_arm.setPosition(ARM_PICKUP);
            left_arm.setPosition(ARM_PICKUP);
            wrist.setPosition(WRIST_UP);
        });

        // MACRO TWO
        primary.pressed(CIRCLE, (gamepad, reader) -> {
            // Set deposit position for both arm and claw
            right_arm.setPosition(ARM_DEPOSIT);
            left_arm.setPosition(ARM_DEPOSIT);
            wrist.setPosition(WRIST_DOWN);
        });

        // Close Claw
        primary.pressed(TRIANGLE, (gamepad, reader) -> {
            claw.setPosition(CLAW_CLOSE);
        });

        // Open Claw
        primary.pressed(SQUARE, (gamepad, reader) -> {
            claw.setPosition(CLAW_OPEN);
        });

        telemetry.addData("Status", "Initialised HardwareMap");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            primary.update();

            telemetry.update();
        }
    }

    private void waitUntil(boolean predicate) {
        while(!predicate) {
            continue;
        }
    }
}

