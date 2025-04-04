package bifunctors.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bifunctors.hardwaremap.BifunctorsHM;
import bifunctors.hardwaremap.components.Mecanum;
import bifunctors.helper.GamepadEx;

@TeleOp(name = "Mecanum Only TeleOp", group = "Testing")
public class MecanumOnlyTeleOp extends LinearOpMode {
    BifunctorsHM robot = new BifunctorsHM(this);

    @Override
    public void runOpMode(){
        robot.initDriveTrain();
        telemetry.addLine().addData(">", "All hardware and controllers initialised");
        telemetry.update();
        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.right_trigger >= 0.15){
                robot.Mecanum.PowerMultiplier = 0.3;
            }
            else{
                robot.Mecanum.PowerMultiplier = 0.7;
            }

            robot.Mecanum.Move(gamepad1);
        }
    }
}
