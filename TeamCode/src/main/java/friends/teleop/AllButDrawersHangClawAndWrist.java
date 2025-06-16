package friends.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import friends.hardwaremap.CtrlAltDefeatHardwareMap;
import friends.hardwaremap.components.Intake;
import friends.hardwaremap.components.Mecanum;
import friends.helper.MotorControl.OutakePIDFConstants;
import friends.helper.MotorControl.SlidePIDFController;

@TeleOp
public class AllButDrawersHangClawAndWrist extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        CtrlAltDefeatHardwareMap map = new CtrlAltDefeatHardwareMap(hardwareMap);
        Intake intake = new Intake(map.IntakeServo, map.IntakeMotor, map.ColorSensor, map.DrawerSlide);
        Mecanum mecanum = new Mecanum(map.FrontRightWheel, map.BackRightWheel, map.BackLeftWheel, map.FrontLeftWheel, 0.7, map.RobotIMU);
        SlidePIDFController slidePIDFController = new SlidePIDFController(OutakePIDFConstants.KP, OutakePIDFConstants.KI, OutakePIDFConstants.KD, OutakePIDFConstants.KF);
        int viperTarget = 0;
        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.left_bumper){
                mecanum.PowerMultiplier = 0.4;
            }
            else{
                mecanum.PowerMultiplier = 0.7;
            }

            if(gamepad2.right_bumper){
                intake.ReadyPosition(gamepad2);
            }
            else if(gamepad2.left_bumper){
                intake.SpitOut();
            }
            else if(gamepad2.touchpad){
                intake.CycleColours(gamepad2);
            }
            else{
                intake.StandbyPosition();
            }

            //NEED TO MAKE OUTTAKE CLASS
            if(gamepad2.triangle){
                map.RightArmServo.setPosition(0.3);
                map.LeftArmServo.setPosition(0.7);
                viperTarget = 600;
            }
            else if(gamepad2.circle){
                map.RightArmServo.setPosition(1);
                map.LeftArmServo.setPosition(0);
                viperTarget = 800;
            }

            mecanum.Move(gamepad1);
            double power = slidePIDFController.PIDControl(map.RightViperSlide.getCurrentPosition(), viperTarget);
            map.LeftViperSlide.setPower(power);
            map.RightViperSlide.setPower(power);
        }
    }
}
