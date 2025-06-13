package CtrAltDelete.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import CtrAltDelete.hardwaremap.CtrlAltDefeatHardwareMap;
import CtrAltDelete.hardwaremap.components.Claw;
import CtrAltDelete.hardwaremap.components.Intake;

@TeleOp(name = "Claw Test", group = "Testing")
public class ClawOpMode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        CtrlAltDefeatHardwareMap map = new CtrlAltDefeatHardwareMap(hardwareMap);
        Claw claw = new Claw(map.ClawServo, map.WristServo);
        waitForStart();
        while(opModeIsActive()){
            if(gamepad2.square) claw.clawOpen();
            else if(gamepad2.cross) claw.clawClose();

            if(gamepad2.triangle) claw.hangWristPos();
            else if(gamepad2.circle) claw.wallWristPos();

            telemetry.addData("claw pos", map.ClawServo.getPosition());
            telemetry.addData("wrist pos", map.WristServo.getPosition());
            telemetry.update();
        }
    }
}
