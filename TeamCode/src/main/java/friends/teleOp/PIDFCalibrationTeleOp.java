package friends.teleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import friends.hardwareMap.HardwareMap;
import friends.helper.Count;
import friends.helper.MotorControl.LeftViperPIDFConstants;
import friends.helper.MotorControl.PIDFController;
import friends.helper.GamepadEx;
import friends.helper.MotorControl.RightViperPIDFConstants;

import static friends.helper.GamepadButton.*;

@TeleOp(name = "PIDF Calibration", group = "Testing")
public class PIDFCalibrationTeleOp extends LinearOpMode {

    @Override
    public void runOpMode(){
        HardwareMap map = new HardwareMap(hardwareMap);

        PIDFController right_controller = new PIDFController(RightViperPIDFConstants.KP, RightViperPIDFConstants.KI, RightViperPIDFConstants.KD, RightViperPIDFConstants.KF);
        PIDFController left_controller = new PIDFController(LeftViperPIDFConstants.KP, LeftViperPIDFConstants.KI, LeftViperPIDFConstants.KD, LeftViperPIDFConstants.KF);

        FtcDashboard dashboard = FtcDashboard.getInstance();

        GamepadEx primary = new GamepadEx(gamepad1);
        int[] target_pos = {0};

        map.RightViperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        map.LeftViperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        primary.pressed(A, () -> target_pos[0] = 80);
        primary.pressed(B, () -> target_pos[0] = 10);

        telemetry.addData("Status", "Initialised HardwareMap");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            primary.update();

            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Right Current Position", map.RightViperMotor.getCurrentPosition());
            packet.put("Right Target Position", target_pos[0]);
            packet.put("Right Motor Power", map.RightViperMotor.getPower());
            packet.put("Left Current Position", map.LeftViperMotor.getCurrentPosition());
            packet.put("Left Target Position", target_pos[0]);
            packet.put("Left Motor Power", map.LeftViperMotor.getPower());
            dashboard.sendTelemetryPacket(packet);

            int right_pos = map.RightViperMotor.getCurrentPosition();
            double right_power = right_controller.PIDControl(telemetry, right_pos, target_pos[0]);
            map.RightViperMotor.setPower(right_power);
            int left_pos = map.LeftViperMotor.getCurrentPosition();
            double left_power = left_controller.PIDControl(telemetry, left_pos, target_pos[0]);
            map.LeftViperMotor.setPower(left_power);

            telemetry.update();
        }
    }
}