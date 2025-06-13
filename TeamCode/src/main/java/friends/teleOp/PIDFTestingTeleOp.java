package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import friends.helper.MotorControl.PIDFConstants;

import friends.hardwareMap.HardwareMap;
import friends.helper.MotorControl.PIDFController;
import friends.helper.GamepadEx;
import static friends.helper.GamepadButton.*;

@TeleOp(name = "Viper Slide PIDF", group = "Testing")
public class PIDFTestingTeleOp extends LinearOpMode {

    @Override
    public void runOpMode(){
        HardwareMap map = new HardwareMap(hardwareMap);

        PIDFConstants constants = new PIDFConstants();

        PIDFController controller = new PIDFController(constants);

        GamepadEx primary = new GamepadEx(gamepad1);

        // KEY => RIGHT TRIGGER
        // FUN => Set Viper Slide Motor Power to 1
        primary.down(RIGHT_TRIGGER, (gamepad, reader) -> {
            map.LeftViperMotor.setPower(controller.PIDControl(
                map.LeftViperMotor.getCurrentPosition(),
                2000));

            map.RightViperMotor.setPower(controller.PIDControl(
                map.RightViperMotor.getCurrentPosition(),
                2000));
        });

        primary.up(RIGHT_TRIGGER, (gamepad, reader) -> {
            if(gamepad.left_trigger > 0) return;
            map.RightViperMotor.setPower(0);
            map.LeftViperMotor.setPower(0);
        });

        // KEY => RIGHT TRIGGER
        // FUN => Set Viper Slide Motor Power to 1
        primary.down(LEFT_TRIGGER, (gamepad, reader) -> {
            map.LeftViperMotor.setPower(controller.PIDControl(
                    map.LeftViperMotor.getCurrentPosition(),
                    -2000));

            map.RightViperMotor.setPower(controller.PIDControl(
                    map.RightViperMotor.getCurrentPosition(),
                    -2000));
        });

        primary.up(LEFT_TRIGGER, (gamepad, reader) -> {
            if(gamepad.right_trigger > 0) return;
            map.RightViperMotor.setPower(0);
            map.LeftViperMotor.setPower(0);
        });

        // KEY => X
        // FUN => Set Viper Slide Motor Powers To FeedForwardControl
        primary.down(X, (gamepad , reader) -> {
            map.LeftViperMotor.setPower(controller.FeedForwardControl());
            map.RightViperMotor.setPower(controller.FeedForwardControl());
        });

        telemetry.addData("Status", "Initialised HardwareMap");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            primary.update(gamepad1);

            telemetry.update();
        }
    }
}
