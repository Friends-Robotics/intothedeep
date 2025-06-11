package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import friends.helper.MotorControl.PIDFConstants;

import friends.hardwareMap.HardwareMap;
import friends.helper.MotorControl.PIDFController;
import friends.helper.GamepadEx;
import static friends.helper.GamepadButton.*;

@TeleOp(name = "PIDF Control Testing Ex", group = "Testing")
public class PIDFControlTestingEx extends LinearOpMode {

    @Override
    public void runOpMode(){
        HardwareMap map = new HardwareMap(hardwareMap);

        PIDFConstants constants = new PIDFConstants();

        //This could be a PIDFController too.
        PIDFController controller = new PIDFController(constants);

        GamepadEx primary = new GamepadEx(gamepad1);

        primary.bind(RIGHT_TRIGGER, (c, reader) -> {
            map.LeftViperMotor.setPower(controller.PIDControl(
                map.LeftViperMotor.getCurrentPosition(),
                2000));
            map.RightViperMotor.setPower(controller.PIDControl(
                map.RightViperMotor.getCurrentPosition(),
                2000));
        });

        primary.bind(B, (c, reader) -> {
            map.LeftViperMotor.setPower(controller.PIDControl(
                map.LeftViperMotor.getCurrentPosition(),
                1000));
            map.RightViperMotor.setPower(controller.PIDControl(
                map.RightViperMotor.getCurrentPosition(),
                1000));
        });

        primary.bind(X, (c , reader) -> {
            map.LeftViperMotor.setPower(controller.FeedForwardControl());
            map.RightViperMotor.setPower(controller.FeedForwardControl());
        });

        telemetry.addData("Status", "Initialised HardwareMap");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            primary.update();

            telemetry.update();
        }
    }
}
