package friends.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import friends.hardwaremap.CtrlAltDefeatHardwareMap;
import friends.helper.MotorControl.OutakePIDFConstants;
import friends.helper.MotorControl.SlidePIDFController;

@TeleOp
public class ScoringPosTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        CtrlAltDefeatHardwareMap map = new CtrlAltDefeatHardwareMap(hardwareMap);
        SlidePIDFController controller = new SlidePIDFController(OutakePIDFConstants.KP, OutakePIDFConstants.KI, OutakePIDFConstants.KD, OutakePIDFConstants.KF);
        waitForStart();

        int target = 0;
        while(opModeIsActive()){

            if(gamepad1.touchpad){
                map.LeftArmServo.setPosition(0);
                map.RightArmServo.setPosition(1);
                target = 800;
            }
            else if(gamepad1.ps){
                map.LeftArmServo.setPosition(0.7);
                map.RightArmServo.setPosition(0.3);
                target = 600;
            }

            if(gamepad1.dpad_up){
                map.LeftArmServo.setPosition(1);
                map.RightArmServo.setPosition(0);
            }
            else if(gamepad1.dpad_down){
                map.LeftArmServo.setPosition(0);
                map.RightArmServo.setPosition(1);
            }

            if(gamepad1.right_bumper){
                target += 10;
            }
            else if(gamepad1.left_bumper){
                target -= 10;
            }

            if(gamepad1.cross) {
                map.LeftArmServo.setPosition(map.LeftArmServo.getPosition() + 0.1);
                map.RightArmServo.setPosition(map.RightArmServo.getPosition() - 0.1);
            }
            else if(gamepad1.circle){
                map.LeftArmServo.setPosition(map.LeftArmServo.getPosition() -0.1);
                map.RightArmServo.setPosition(map.RightArmServo.getPosition() + 0.1);
            }
            else if(gamepad1.triangle){
                map.LeftArmServo.setPosition(map.LeftArmServo.getPosition() -0.01);
                map.RightArmServo.setPosition(map.RightArmServo.getPosition() + 0.01);
            }
            else if(gamepad1.square){
                map.LeftArmServo.setPosition(map.LeftArmServo.getPosition() + 0.01);
                map.RightArmServo.setPosition(map.RightArmServo.getPosition() - 0.01);
            }

            if(gamepad2.dpad_up){
                map.IntakeServo.setPosition(map.IntakeServo.getPosition() + 0.01);
            }
            else if(gamepad2.dpad_down){
                map.IntakeServo.setPosition(map.IntakeServo.getPosition() - 0.01);
            }

            double power = controller.PIDControl(map.RightViperSlide.getCurrentPosition(), target);
            map.LeftViperSlide.setPower(power);
            map.RightViperSlide.setPower(power);

            telemetry.addData("left servo pos", map.LeftArmServo.getPosition());
            telemetry.addData("right servo pos", map.RightArmServo.getPosition());
            telemetry.addData("viper", map.RightViperSlide.getCurrentPosition());
            telemetry.addData("servo", map.IntakeServo.getPosition());
            telemetry.update();
            sleep(100);
        }
    }
}
