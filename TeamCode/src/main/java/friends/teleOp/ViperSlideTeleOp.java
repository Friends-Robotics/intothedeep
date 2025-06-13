package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import friends.hardwareMap.HardwareMap;
import friends.helper.GamepadEx;
import static friends.helper.GamepadButton.*;

@TeleOp(name = "Viper Slide", group = "Testing")
public class ViperSlideTeleOp extends LinearOpMode {

    @Override
    public void runOpMode(){
        HardwareMap map = new HardwareMap(hardwareMap);

        GamepadEx primary = new GamepadEx(gamepad1);

        // KEY => RIGHT TRIGGER
        // FUN => Set Viper Slide Motor Powers To 1
        primary.down(RIGHT_TRIGGER, (gamepad, reader) -> {
            map.RightViperMotor.setVelocity(2000);
            map.LeftViperMotor.setVelocity(2000);
//            map.RightViperMotor.setPower(1);
//            map.LeftViperMotor.setPower(1);
            telemetry.addData("Right Current Power", map.RightViperMotor.getPower());
            telemetry.addData("Left Current Power", map.LeftViperMotor.getPower());
        });

        primary.up(RIGHT_TRIGGER, (gamepad, reader) -> {
            if(gamepad.left_trigger > 0) return;
            map.RightViperMotor.setVelocity(0);
            map.LeftViperMotor.setVelocity(0);
        });

        // KEY => LEFT TRIGGER
        // FUN => Set Viper Slide Motor Powers To -1
        primary.down(LEFT_TRIGGER, (gamepad, reader) -> {
            map.RightViperMotor.setPower(-2000);
            map.LeftViperMotor.setPower(-2000);
            telemetry.addData("Right Current Velocity", map.RightViperMotor.getVelocity());
            telemetry.addData("Left Current Velocity", map.LeftViperMotor.getVelocity());
        });

        primary.up(LEFT_TRIGGER, (gamepad, reader) -> {
            if(gamepad.right_trigger > 0) return;
            map.RightViperMotor.setVelocity(0);
            map.LeftViperMotor.setVelocity(0);
        });

        // KEY => RIGHT BUMPER
        // FUN => Set Viper Slide Motor Powers To 0.5
        primary.down(RIGHT_BUMPER, (gamepad, reader) -> {
            map.RightViperMotor.setPower(0.5);
            map.LeftViperMotor.setPower(0.5);
            telemetry.addData("Right Current Power", map.RightViperMotor.getPower());
            telemetry.addData("Left Current Power", map.LeftViperMotor.getPower());
        });


        // KEY => LEFT BUMPER
        // FUN => Set Viper Slide Motor Powers To -0.5
        primary.down(LEFT_BUMPER , (gamepad, reader) -> {
            map.RightViperMotor.setPower(-0.5);
            map.LeftViperMotor.setPower(-0.5);
            telemetry.addData("Right Current Power", map.RightViperMotor.getPower());
            telemetry.addData("Left Current Power", map.LeftViperMotor.getPower());
        });

        // KEY => A, CROSS
        // FUN => Set Hang Servos to max position if A pressed, else minimum position
        primary.down(A, (gamepad, reader) -> {
            map.RightHangServo.setPosition(1);
            map.LeftHangServo.setPosition(1);
        });

        primary.up(A, (gamepad, reader) -> {
            map.RightHangServo.setPosition(0);
            map.LeftHangServo.setPosition(0);
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
