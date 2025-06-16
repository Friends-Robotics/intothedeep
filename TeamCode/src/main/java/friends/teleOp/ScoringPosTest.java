package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static friends.helper.GamepadButton.*;
import friends.hardwareMap.HardwareMap;
import friends.helper.Count;
import friends.helper.GamepadEx;
import friends.helper.MotorControl.OutakePIDFConstants;
import friends.helper.MotorControl.PIDFController;

@TeleOp
public class ScoringPosTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap map = new HardwareMap(hardwareMap);
        PIDFController controller = new PIDFController(OutakePIDFConstants.KP, OutakePIDFConstants.KI, OutakePIDFConstants.KD, OutakePIDFConstants.KF);
        GamepadEx primary = new GamepadEx(gamepad1);
        GamepadEx secondary = new GamepadEx(gamepad2);

        final Count target = new Count();

        primary.down(TOUCHPAD, () -> {
            map.LeftArmServo.setPosition(0);
            map.RightArmServo.setPosition(1);
            target.value = 800;
        });

        primary.down(PLAYSTATION, () -> {
            map.LeftArmServo.setPosition(0.7);
            map.RightArmServo.setPosition(0.3);
            target.value = 600;
        });

        primary.down(DPAD_DOWN, () -> {
            map.LeftArmServo.setPosition(0);
            map.RightArmServo.setPosition(1);
        });
        primary.down(DPAD_UP, () -> {
            map.LeftArmServo.setPosition(1);
            map.RightArmServo.setPosition(0);
        });

        primary.pressed(RIGHT_BUMPER, () -> {
            target.value += 50;
        });

        primary.pressed(LEFT_BUMPER, () -> {
            target.value -= 50;
        });

        primary.pressed(CROSS, () -> {
            map.LeftArmServo.setPosition(map.LeftArmServo.getPosition() + 0.1);
            map.RightArmServo.setPosition(map.RightArmServo.getPosition() - 0.1);
        });
        primary.pressed(CIRCLE, () -> {
            map.LeftArmServo.setPosition(map.LeftArmServo.getPosition() -0.1);
            map.RightArmServo.setPosition(map.RightArmServo.getPosition() + 0.1);
        });
        primary.pressed(TRIANGLE, () -> {
            map.LeftArmServo.setPosition(map.LeftArmServo.getPosition() -0.01);
            map.RightArmServo.setPosition(map.RightArmServo.getPosition() + 0.01);
        });
        primary.pressed(SQUARE, () -> {
            map.LeftArmServo.setPosition(map.LeftArmServo.getPosition() + 0.01);
            map.RightArmServo.setPosition(map.RightArmServo.getPosition() - 0.01);
        });

        secondary.pressed(DPAD_UP, () -> map.IntakeServo.setPosition(map.IntakeServo.getPosition() + 0.01));
        secondary.pressed(DPAD_DOWN, () -> map.IntakeServo.setPosition(map.IntakeServo.getPosition() - 0.01));

        waitForStart();

        while(opModeIsActive()){
            primary.update();
            secondary.update();

            double power = controller.PIDControl(map.RightViperMotor.getCurrentPosition(), (int)target.value);
            map.LeftViperMotor.setPower(power);
            map.RightViperMotor.setPower(power);

            telemetry.addData("left servo pos", map.LeftArmServo.getPosition());
            telemetry.addData("right servo pos", map.RightArmServo.getPosition());
            telemetry.addData("viper", map.RightViperMotor.getCurrentPosition());
            telemetry.addData("servo", map.IntakeServo.getPosition());
            telemetry.update();
            sleep(100);
        }
    }
}