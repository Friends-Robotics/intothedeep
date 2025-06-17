package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
import friends.helper.Count;
import friends.helper.MotorControl.ViperPIDFConstants;
import friends.helper.gamepad.GamepadEx;
import friends.helper.MotorControl.PIDFController;

import static friends.helper.gamepad.GamepadButton.*;

@TeleOp(name="Viper Testing", group="Testing")
public class MotorTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Create hardware map
        HardwareMap map = new HardwareMap(hardwareMap, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        map.RightViperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        map.LeftViperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        map.RightViperMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        map.LeftViperMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Arm arm = new Arm(map);
        telemetry.addData("Status", "Initialised HardwareMap");

        GamepadEx primary = new GamepadEx(gamepad1);

        telemetry.addData("Status","Initialised GamepadEx");
        PIDFController viperpidf = new PIDFController(ViperPIDFConstants.KP, ViperPIDFConstants.KI, ViperPIDFConstants.KD, ViperPIDFConstants.KF);

        Count viper_target = new Count();
        Count right_target = new Count();
        right_target.value = 0.5;
        Count left_target = new Count();
        left_target.value = 0.5;

        Servo right = map.RightArmServo;
        Servo left = map.LeftArmServo;

        primary.pressed(CROSS, () -> viper_target.value += 50);
        primary.pressed(CIRCLE, () -> viper_target.value -= 50);

        primary.pressed(SQUARE, arm::score);
        primary.pressed(TRIANGLE, arm::wall);

        primary.pressed(RIGHT_BUMPER, arm::closeClaw);


        primary.pressed(X, () -> viper_target.value = 4000);
        primary.pressed(Y, () -> viper_target.value = 0);

        primary.pressed(DPAD_LEFT, () -> right_target.value += 0.05);
        primary.pressed(DPAD_RIGHT, () -> right_target.value -= 0.05);
        primary.pressed(DPAD_UP, () -> left_target.value += 0.05);
        primary.pressed(DPAD_DOWN, () -> left_target.value -= 0.05);

        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            primary.update();

            double power = viperpidf.PIDControl(map.RightViperMotor.getCurrentPosition(), (int)viper_target.value);
            map.LeftViperMotor.setPower(power);
            map.RightViperMotor.setPower(power);

            right.setPosition(1 - right_target.value);
            left.setPosition(left_target.value);

            telemetry.addData("target", viper_target.value);

            telemetry.addData("ticks", map.RightViperMotor.getCurrentPosition());
            telemetry.addData("ticks left", map.LeftViperMotor.getCurrentPosition());

            telemetry.addData("right servo", map.RightArmServo.getPosition());
            telemetry.addData("left servo", map.LeftArmServo.getPosition());

            telemetry.update();
        }
    }
}
