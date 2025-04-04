package bifunctors.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bifunctors.hardwaremap.HardwareMap;
import bifunctors.hardwaremap.TestingHardwareMap;
import bifunctors.hardwaremap.components.Mecanum;

@TeleOp(name = "Mecanum Only TeleOp", group = "Testing")
public class MecanumOnlyTeleOp extends LinearOpMode {
    @Override
    public void runOpMode(){

        // Init hardware map
        TestingHardwareMap map = new TestingHardwareMap(hardwareMap);
        telemetry.addLine("All hardware and controllers initialised");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.right_trigger >= 0.15) {
                map.MecanumSet.PowerMultiplier = 0.3;
            }
            else {
                map.MecanumSet.PowerMultiplier = 0.7;
            }

            map.MecanumSet.Move(gamepad1);
            map.MecanumSet.SendMecanumTelemetry(telemetry);

            telemetry.update();
        }
    }
}