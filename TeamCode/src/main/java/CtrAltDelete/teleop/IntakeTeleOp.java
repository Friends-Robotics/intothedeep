package CtrAltDelete.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import CtrAltDelete.hardwaremap.CtrlAltDefeatHardwareMap;
import CtrAltDelete.hardwaremap.components.Intake;
import CtrAltDelete.helper.GamepadEx;

@TeleOp(name = "Intake Test", group = "Testing")
public class IntakeTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        CtrlAltDefeatHardwareMap map = new CtrlAltDefeatHardwareMap(hardwareMap);
        Intake intake = new Intake(map.IntakeServo, map.IntakeMotor, map.ColorSensor, map.DrawerSlide);
        GamepadEx.initGamepads(gamepad1, gamepad2);

        GamepadEx.secondary.bind(GamepadEx.GamepadButton.RIGHT_BUMPER

        waitForStart();
        while(opModeIsActive()){
            if(gamepad2.right_bumper) intake.StandbyPosition();
            else if(gamepad2.left_bumper) intake.ReadyPosition(gamepad2);

            if(gamepad2.touchpad) intake.CycleColours(gamepad2);

            intake.SendTelemetry(telemetry);
            telemetry.update();
        }
    }
}
