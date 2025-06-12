package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import friends.helper.MotorControl.PIDFConstants;

import friends.hardwareMap.HardwareMap;
import friends.helper.MotorControl.PIDFController;
import friends.helper.GamepadEx;
import static friends.helper.GamepadButton.*;

@TeleOp(name = "PIDF Control", group = "Testing")
public class PIDFTesting extends LinearOpMode {

    @Override
    public void runOpMode(){
        HardwareMap map = new HardwareMap(hardwareMap);

        PIDFConstants constants = new PIDFConstants();

        PIDFController controller = new PIDFController(constants);

        GamepadEx primary = new GamepadEx(gamepad1);

        // KEY => RIGHT TRIGGER
        // FUN => Set Viper Slide Motor Powers Using PIDF
        // Maximum Value Of 2000
        primary.bind(RIGHT_TRIGGER, (gamepad, reader) -> {
            map.LeftViperMotor.setPower(controller.PIDControl(
                map.LeftViperMotor.getCurrentPosition(),
                2000));

            map.RightViperMotor.setPower(controller.PIDControl(
                map.RightViperMotor.getCurrentPosition(),
                2000));
        });

        // KEY => LEFT TRIGGER
        // FUN => Set Viper Slide Motor Powers Using PIDF
        // Maximum Value Of 1000
        primary.bind(B, (gamepad, reader) -> {
            map.LeftViperMotor.setPower(controller.PIDControl(
                map.LeftViperMotor.getCurrentPosition(),
                1000));

            map.RightViperMotor.setPower(controller.PIDControl(
                map.RightViperMotor.getCurrentPosition(),
                1000));
        });

        // KEY => X
        // FUN => Set Viper Slide Motor Powers To FeedForwardControl
        primary.bind(X, (gamepad , reader) -> {
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
