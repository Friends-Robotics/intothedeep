package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import friends.hardwareMap.HardwareMap;
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

        telemetry.addData("Status", "Initialised HardwareMap");

        GamepadEx primary = new GamepadEx(gamepad1);

        telemetry.addData("Status","Initialised GamepadEx");
        PIDFController viperpidf = new PIDFController(OutakePIDFConstants.KP, OutakePIDFConstants.KI, OutakePIDFConstants.KD, OutakePIDFConstants.KF);

        Count viper_target = new Count();

        primary.pressed(A, () -> viper_target.value = 2000);
        primary.pressed(B, () -> viper_target.value = 100);

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
