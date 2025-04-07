package bifunctors.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bifunctors.hardwaremap.BifunctorsHardwareMap;
import bifunctors.helper.MotorControl.OutakePIDFConstants;
import bifunctors.helper.MotorControl.PIDController;

@TeleOp
public class PIDFControlTesting extends LinearOpMode{

    @Override
    public void runOpMode(){
        BifunctorsHardwareMap map = new BifunctorsHardwareMap(hardwareMap);

        //This could be a PIDFController too.
        PIDController controller = new PIDController(OutakePIDFConstants.KP, OutakePIDFConstants.KI, OutakePIDFConstants.KD);

        telemetry.addData("Status", "Initialised HardwareMap");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.cross){
                //map.Example.setPower(controller.PIDControl(map.Example.getCurrentPosition(), 100)); ONE POSITION
            }
            else{
                //map.Example.setPower(controller.PIDControl(map.Example.getCurrentPosition(), -100)); DIFFERENT POSITION
            }
        }
    }
}
