package CtrAltDelete.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.util.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import CtrAltDelete.hardwaremap.CtrlAltDefeatHardwareMap;
import CtrAltDelete.helper.MotorControl.DrawerPIDFConstants;
import CtrAltDelete.helper.MotorControl.OutakePIDFConstants;
import CtrAltDelete.helper.MotorControl.PIDController;
import CtrAltDelete.helper.MotorControl.SlidePIDFController;

@TeleOp(name = "PIDF Control Testing", group = "Testing")
public class PIDFControlTesting extends LinearOpMode{
    @Override
    public void runOpMode() {
        CtrlAltDefeatHardwareMap map = new CtrlAltDefeatHardwareMap(hardwareMap);

        SlidePIDFController controller = new SlidePIDFController(OutakePIDFConstants.KP, OutakePIDFConstants.KI, OutakePIDFConstants.KD, OutakePIDFConstants.KF);

        FtcDashboard dashboard = FtcDashboard.getInstance();

        telemetry.addData("Status", "Initialised HardwareMap");
        telemetry.update();

        // Initialize the target position once
        int targetPosition = map.RightViperSlide.getCurrentPosition();

        waitForStart();

        while (opModeIsActive()) {
            // Change target when button is pressed
            if (gamepad1.cross) {
                targetPosition = 1000;
            } else if (gamepad1.circle) {
                targetPosition = 3000;
            } else if (gamepad1.square) {
                targetPosition = 200;
            }

            int currentPosition = map.RightViperSlide.getCurrentPosition();
            double power = controller.PIDControl(currentPosition, targetPosition);
            map.LeftViperSlide.setPower(power);
            map.RightViperSlide.setPower(power);

            // Send data to dashboard
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Current Position", map.RightViperSlide.getCurrentPosition());
            packet.put("Target Position", targetPosition);
            packet.put("Motor Power", map.RightViperSlide.getPower());
            dashboard.sendTelemetryPacket(packet);

            // Regular telemetry
            telemetry.addData("Current Pos", map.RightViperSlide.getCurrentPosition());
            telemetry.addData("Target Pos", targetPosition);
            telemetry.addData("Motor Power", map.RightViperSlide.getPower());
            telemetry.addData("Buttons", "⬜: %.2f ⭕: %.2f ❌: %.2f 🔺: %.2f",
                    gamepad1.square ? 1.0 : 0.0,
                    gamepad1.circle ? 1.0 : 0.0,
                    gamepad1.cross ? 1.0 : 0.0,
                    gamepad1.triangle ? 1.0 : 0.0);
            telemetry.update();
        }
    }
}
