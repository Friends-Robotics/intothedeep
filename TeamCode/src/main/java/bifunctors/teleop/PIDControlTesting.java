package bifunctors.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bifunctors.hardwaremap.BifunctorsHardwareMap;
import bifunctors.helper.OutakePIDFConstants;
import bifunctors.helper.PIDController;

@TeleOp
public class PIDControlTesting extends LinearOpMode{

    @Override
    public void runOpMode(){
        BifunctorsHardwareMap map = new BifunctorsHardwareMap(hardwareMap);
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
