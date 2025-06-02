package CtrAltDelete.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import CtrAltDelete.hardwaremap.CtrlAltDefeatHardwareMap;
import CtrAltDelete.helper.MotorControl.OutakePIDFConstants;
import CtrAltDelete.helper.MotorControl.SlidePIDFController;

@TeleOp(name = "PIDF Control Testing", group = "Testing")
public class PIDFControlTesting extends LinearOpMode{

    @Override
    public void runOpMode(){
        CtrlAltDefeatHardwareMap map = new CtrlAltDefeatHardwareMap(hardwareMap);

        //This could be a PIDFController too.
        SlidePIDFController controller = new SlidePIDFController(OutakePIDFConstants.KP, OutakePIDFConstants.KI, OutakePIDFConstants.KD, OutakePIDFConstants.KF);

        telemetry.addData("Status", "Initialised HardwareMap");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.right_trigger > 0.1){
                map.LeftViperSlide.setPower(controller.PIDControl(map.LeftViperSlide.getCurrentPosition(), 2000));
                map.RightViperSlide.setPower(controller.PIDControl(map.RightViperSlide.getCurrentPosition(), 2000));
            }
            else if(gamepad1.circle){
                map.LeftViperSlide.setPower(controller.PIDControl(map.LeftViperSlide.getCurrentPosition(), 1000));
                map.RightViperSlide.setPower(controller.PIDControl(map.RightViperSlide.getCurrentPosition(), 1000));
            }
            else{
                map.LeftViperSlide.setPower(controller.FeedForwardControl());
                map.RightViperSlide.setPower(controller.FeedForwardControl());
            }
        }
    }
}
