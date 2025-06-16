package friends.teleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Intake;
import friends.helper.Count;
import friends.helper.MotorControl.DrawerPIDFConstants;
import friends.helper.MotorControl.PIDFController;
import friends.helper.gamepad.GamepadButton;
import friends.helper.gamepad.GamepadEx;

@TeleOp(name = "PIDF Testing", group = "Testing")
public class PIDFTuner extends LinearOpMode {
    @Override
    public void runOpMode() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        HardwareMap map = new HardwareMap(hardwareMap);

        Count target = new Count();
        GamepadEx primary = new GamepadEx(gamepad1);

        PIDFController pid = new PIDFController(DrawerPIDFConstants.KP, DrawerPIDFConstants.KI, DrawerPIDFConstants.KD, DrawerPIDFConstants.KF);

        primary.pressed(GamepadButton.CROSS, () -> target.value = 224);
        primary.pressed(GamepadButton.CIRCLE, () -> target.value = 100);
        primary.pressed(GamepadButton.SQUARE, () -> target.value = 0);
        primary.pressed(GamepadButton.TRIANGLE, () -> target.value = 50);

        waitForStart();
        while(opModeIsActive()) {

            primary.update();

            map.DrawerSlideMotor.setPower(pid.PIDControl(map.DrawerSlideMotor.getCurrentPosition(), (int)target.value));
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("State", map.DrawerSlideMotor.getCurrentPosition());
            dashboard.sendTelemetryPacket(packet);

            telemetry.addData("DS motor port", map.DrawerSlideMotor.getPortNumber());
            telemetry.addData("power", map.DrawerSlideMotor.getPower());
            telemetry.addData("pos", map.DrawerSlideMotor.getCurrentPosition());
            telemetry.addData("target", target.value);
            telemetry.update();
        }
    }
}
