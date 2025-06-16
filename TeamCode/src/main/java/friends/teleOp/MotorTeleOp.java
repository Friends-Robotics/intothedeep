package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import friends.hardwareMap.HardwareMap;
import friends.helper.Count;
import friends.helper.gamepad.GamepadEx;
import friends.helper.MotorControl.PIDFController;
import friends.helper.MotorControl.SlidePIDFConstants;

import static friends.helper.gamepad.GamepadButton.*;

@TeleOp(name="Movement", group="Linear OpMode")
public class MotorTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Create hardware map
        HardwareMap map = new HardwareMap(hardwareMap, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Initialised HardwareMap");

        GamepadEx primary = new GamepadEx(gamepad1);

        telemetry.addData("Status","Initialised GamepadEx");
        PIDFController viperpidf = new PIDFController(SlidePIDFConstants.KP, SlidePIDFConstants.KI, SlidePIDFConstants.KD, SlidePIDFConstants.KF);

        Count viperTarget = new Count();

        primary.pressed(A, () -> viperTarget.value = 2000);
        primary.pressed(B, () -> viperTarget.value = 100);

        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            primary.update();

            double power = viperpidf.PIDControl(map.RightViperMotor.getCurrentPosition(), (int)viperTarget.value);
            map.LeftViperMotor.setPower(power);
            map.RightViperMotor.setPower(power);

            telemetry.update();
        }
    }
}
