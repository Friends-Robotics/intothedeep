package friends.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import friends.hardwaremap.CtrlAltDefeatHardwareMap;
import friends.hardwaremap.components.Intake;
import friends.helper.GamepadButton;
import friends.helper.GamepadEx;

@TeleOp(name = "Intake Test", group = "Testing")
public class IntakeTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        CtrlAltDefeatHardwareMap map = new CtrlAltDefeatHardwareMap(hardwareMap);
        Intake intake = new Intake(map.IntakeServo, map.IntakeMotor, map.ColorSensor, map.DrawerSlide);
        GamepadEx gp1 = new GamepadEx(gamepad1);

        gp1.down(GamepadButton.LEFT_BUMPER, (gamepad, buttonReader) ->{
            intake.StandbyPosition();
        });

        gp1.down(GamepadButton.RIGHT_BUMPER, (gamepad, buttonReader) -> {
            intake.ReadyPosition(gamepad1);
        });

        gp1.down(GamepadButton.TOUCHPAD, (gamepad, buttonReader) -> {
            intake.CycleColours(gamepad1);
        });

        waitForStart();
        while(opModeIsActive()){
            intake.SendTelemetry(telemetry);
            telemetry.update();
            gp1.update();
        }
    }
}
