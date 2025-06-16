package friends.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import friends.hardwaremap.CtrlAltDefeatHardwareMap;
import friends.hardwaremap.components.Intake;
import friends.hardwaremap.components.Mecanum;
import friends.helper.GamepadButton;
import friends.helper.GamepadEx;

@TeleOp(name = "Intake Test", group = "Testing")
public class IntakeTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        CtrlAltDefeatHardwareMap map = new CtrlAltDefeatHardwareMap(hardwareMap);
        Intake intake = new Intake(map.IntakeServo, map.IntakeMotor, map.ColorSensor, map.DrawerSlide);
        GamepadEx gp2 = new GamepadEx(gamepad2);
        Mecanum mecanum = new Mecanum(map.FrontRightWheel, map.BackRightWheel, map.BackLeftWheel, map.FrontLeftWheel, 0.7, map.RobotIMU);

        gp2.pressed(GamepadButton.TOUCHPAD, (gamepad, buttonReader) -> {
            intake.CycleColours(gamepad2);
        });

        waitForStart();
        while(opModeIsActive()){

            if(gamepad2.right_bumper){
                intake.ReadyPosition(gamepad2);
            }
            else if(gamepad2.left_bumper){
                intake.SpitOut();
            }
            else{
                intake.StandbyPosition();
            }
            mecanum.Move(gamepad1);

            intake.SendTelemetry(telemetry);
            telemetry.update();
            gp2.update();
        }
    }
}
