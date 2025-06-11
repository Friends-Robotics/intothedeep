package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import friends.helper.MotorControl.PIDFConstants;

import friends.hardwareMap.HardwareMap;
import friends.helper.MotorControl.PIDFController;

@TeleOp(name = "PIDF Control Testing", group = "Testing")
public class PIDFControlTesting extends LinearOpMode{

    @Override
    public void runOpMode(){
        HardwareMap map = new HardwareMap(hardwareMap);

        PIDFConstants constants = new PIDFConstants();

        //This could be a PIDFController too.
        PIDFController controller = new PIDFController(constants);

        telemetry.addData("Status", "Initialised HardwareMap");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            int left_pos = map.LeftViperMotor.getCurrentPosition();
            int right_pos = map.RightViperMotor.getCurrentPosition();
            if(gamepad1.right_trigger > 0.1){
                map.LeftViperMotor.setPower(controller.PIDControl(left_pos, 2000));
                map.RightViperMotor.setPower(controller.PIDControl(right_pos, 2000));
            }
            else if(gamepad1.circle){
                map.LeftViperMotor.setPower(controller.PIDControl(left_pos, 1000));
                map.RightViperMotor.setPower(controller.PIDControl(right_pos, 1000));
            }
            else{
                map.LeftViperMotor.setPower(controller.FeedForwardControl());
                map.RightViperMotor.setPower(controller.FeedForwardControl());
            }
        }
    }
}
