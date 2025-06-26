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

import java.util.Optional;

@TeleOp(name="Viper Testing", group="Testing")
public class MotorTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Create hardware map
        HardwareMap map = new HardwareMap(hardwareMap, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        map.RightViperMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        map.LeftViperMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        map.RightViperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        map.LeftViperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Arm arm = new Arm(map, Optional.empty());
        telemetry.addData("Status", "Initialised HardwareMap");

        GamepadEx primary = new GamepadEx(gamepad1);

        telemetry.addData("Status","Initialised GamepadEx");
        PIDFController viperpidf = new PIDFController(ViperPIDFConstants.KP, ViperPIDFConstants.KI, ViperPIDFConstants.KD, ViperPIDFConstants.KF);
        Count viper_target = new Count();

        primary.pressed(CROSS, () -> { viper_target.value += 100; telemetry.speak("100"); });
        primary.pressed(CIRCLE, () -> { viper_target.value -= 100; telemetry.speak("-100");});

        primary.pressed(X, () -> { viper_target.value += 20; telemetry.speak("20"); });
        primary.pressed(Y, () -> { viper_target.value -= 20; telemetry.speak("-20"); });

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
