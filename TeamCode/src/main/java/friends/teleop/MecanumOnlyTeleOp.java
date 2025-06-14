package friends.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import friends.hardwaremap.CtrlAltDefeatHardwareMap;
import friends.hardwaremap.components.Mecanum;

@TeleOp(name = "Mecanum Only TeleOp", group = "Testing")
public class MecanumOnlyTeleOp extends LinearOpMode {
    @Override
    public void runOpMode(){

        // Init hardware map
        CtrlAltDefeatHardwareMap map = new CtrlAltDefeatHardwareMap(hardwareMap);
        Mecanum MecanumSet = new Mecanum(map.FrontRightWheel, map.BackRightWheel, map.BackLeftWheel, map.FrontLeftWheel, 0.5, map.RobotIMU);
        telemetry.addLine("All hardware and controllers initialised");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.right_trigger >= 0.15) {
                MecanumSet.PowerMultiplier = 0.3;
            }
            else {
                MecanumSet.PowerMultiplier = 0.7;
            }

            MecanumSet.Move(gamepad1);
            MecanumSet.SendTelemetry(telemetry);

            telemetry.update();
        }
    }
}