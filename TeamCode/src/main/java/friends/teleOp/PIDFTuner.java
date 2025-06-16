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

@TeleOp
public class PIDFTuner extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        HardwareMap map = new HardwareMap(hardwareMap);
        Count target = new Count();
        GamepadEx primary = new GamepadEx(gamepad1);
        PIDFController pid = new PIDFController(DrawerPIDFConstants.KP, DrawerPIDFConstants.KI, DrawerPIDFConstants.KD, DrawerPIDFConstants.KF);
        primary.pressed(GamepadButton.CROSS, () -> target.value = 80);
        primary.pressed(GamepadButton.CIRCLE, () -> target.value = 40);
        primary.pressed(GamepadButton.SQUARE, () -> target.value = 0);
        waitForStart();
        while(opModeIsActive()){
            map.DrawerSlideMotor.setPower(pid.PIDControl(map.DrawerSlideMotor.getCurrentPosition(), (int)target.value));
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("State", map.DrawerSlideMotor.getCurrentPosition());
            dashboard.sendTelemetryPacket(packet);
        }
    }
}
