package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Mecanum;
import friends.helper.Check;
import friends.helper.Colours;
import friends.helper.GamepadEx;
import friends.helper.MotorControl.RightViperPIDFConstants;
import friends.helper.MotorControl.PIDFController;

import static friends.helper.GamepadButton.*;

@TeleOp(name="Cheeky", group="Linear OpMode")
public class CheekyTeleOp extends LinearOpMode {
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

        PIDFController controller = new PIDFController(RightViperPIDFConstants.KP, RightViperPIDFConstants.KI, RightViperPIDFConstants.KD, RightViperPIDFConstants.KF);

        telemetry.addData("Status", "Initialised PIDF Controller");

        GamepadEx primary = new GamepadEx(gamepad1);
        GamepadEx secondary = new GamepadEx(gamepad2);

        telemetry.addData("Status","Initialised GamepadEx");

        ///  Primary

        Check speedCheck = new Check();

        primary.pressed(A, (gamepad, reader) -> {
            speedCheck.value = !speedCheck.value;
            m.PowerMultiplier = speedCheck.value ? 1 : 0.5;
            Colours col = speedCheck.value ? Colours.RED : Colours.BLUE;
            gamepad.setLedColor(col.R(), col.G(), col.B(), Gamepad.LED_DURATION_CONTINUOUS);
        });

        /// Secondary

        secondary.down(RIGHT_BUMPER, (gamepad, reader) -> {
            map.LeftViperMotor.setPower(controller.PIDControl(telemetry,
                    map.LeftViperMotor.getCurrentPosition(),
                    2000));
            map.RightViperMotor.setPower(controller.PIDControl(telemetry,
                    map.RightViperMotor.getCurrentPosition(),
                    2000));
        });

        secondary.up(RIGHT_BUMPER, (gamepad, reader) -> {
            if(gamepad.left_bumper) return;
            map.RightViperMotor.setPower(0);
            map.LeftViperMotor.setPower(0);
        });

        secondary.down(LEFT_BUMPER, (gamepad, reader) -> {
            map.LeftViperMotor.setPower(controller.PIDControl(telemetry,
                    map.LeftViperMotor.getCurrentPosition(),
                    -2000));
            map.RightViperMotor.setPower(controller.PIDControl(telemetry,
                    map.RightViperMotor.getCurrentPosition(),
                    -2000));
        });

        secondary.up(LEFT_BUMPER, (gamepad, reader) -> {
            if(gamepad.right_bumper) return;
            map.RightViperMotor.setPower(0);
            map.LeftViperMotor.setPower(0);
        });

        secondary.down(A, (gamepad, reader) -> {
            map.RightHangServo.setPosition(0);
            map.LeftHangServo.setPosition(0);
        });

        secondary.up(A, (gamepad, reader) -> {
            map.RightHangServo.setPosition(1);
            map.LeftHangServo.setPosition(1);
        });

        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            m.Move(gamepad1);
            primary.update();
            secondary.update();

            telemetry.update();
        }
    }
}
