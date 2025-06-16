package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
import friends.helper.Count;
import friends.helper.MotorControl.OutakePIDFConstants;
import friends.helper.gamepad.GamepadEx;
import friends.helper.MotorControl.PIDFController;
import friends.helper.MotorControl.SlidePIDFConstants;

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

        telemetry.addData("Status", "Initialised HardwareMap");

        GamepadEx primary = new GamepadEx(gamepad1);

        telemetry.addData("Status","Initialised GamepadEx");
        PIDFController viperpidf = new PIDFController(OutakePIDFConstants.KP, OutakePIDFConstants.KI, OutakePIDFConstants.KD, OutakePIDFConstants.KF);

        Count viper_target = new Count();
        Count servo_target = new Count();

        Servo right = map.RightArmServo;
        Servo left = map.LeftArmServo;

        primary.pressed(A, () -> viper_target.value += 200);
        primary.pressed(B, () -> viper_target.value -= 200);

        primary.pressed(X, () -> viper_target.value = 4000);
        primary.pressed(Y, () -> viper_target.value = 0);

        primary.pressed(DPAD_LEFT, () -> servo_target.value += 0.1);
        primary.pressed(DPAD_RIGHT, () -> servo_target.value -= 0.1);
        primary.pressed(DPAD_UP, () -> servo_target.value += 0.05);
        primary.pressed(DPAD_DOWN, () -> servo_target.value -= 0.05);

        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            primary.update();

            double power = viperpidf.PIDControl(map.RightViperMotor.getCurrentPosition(), (int)viper_target.value);
            map.LeftViperMotor.setPower(power);
            map.RightViperMotor.setPower(power);

            telemetry.addData("target", viper_target.value);

            telemetry.update();
        }
    }
}
