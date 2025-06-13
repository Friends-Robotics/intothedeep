package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import friends.hardwareMap.HardwareMap;
import friends.helper.GamepadEx;
import static friends.helper.GamepadButton.*;

@TeleOp(name = "UP THE QUAKERS", group = "Testing")
public class UpTheBossTeleOp extends LinearOpMode {

    @Override
    public void runOpMode(){
        HardwareMap map = new HardwareMap(hardwareMap);

        GamepadEx primary = new GamepadEx(gamepad1);



        ///  MACRO ONE   : Pickup Position -> Open Claw
        ///  MACRO TWO   : Close Claw -> Flip Servo -> Deposit Position
        ///  MACRO THREE : Rest Position




        telemetry.addData("Status", "Initialised HardwareMap");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            primary.update();

            telemetry.update();
        }
    }

    private void waitUntil(boolean predicate) {
        while(!predicate) {
            continue;
        }
    }
}

