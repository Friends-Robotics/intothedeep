package CtrAltDelete.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
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

        PIDController controller = new PIDController(DrawerPIDFConstants.KP, DrawerPIDFConstants.KI, DrawerPIDFConstants.KD);

        FtcDashboard dashboard = FtcDashboard.getInstance();

        telemetry.addData("Status", "Initialised HardwareMap");
        telemetry.update();

        // Initialize the target position once
        int targetPosition = map.DrawerSlide.getCurrentPosition();

        waitForStart();

        while (opModeIsActive()) {
            // Change target when button is pressed
            if (gamepad1.cross) {
                targetPosition = 80;
            } else if (gamepad1.circle) {
                targetPosition = 10;
            } else if (gamepad1.square) {
                targetPosition = 40;
            }
            else {
                // Run PID if no manual override
                int currentPosition = map.DrawerSlide.getCurrentPosition();
                double power = controller.PIDControl(currentPosition, targetPosition);
                map.DrawerSlide.setPower(power);
            }

            // Send data to dashboard
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Current Position", map.DrawerSlide.getCurrentPosition());
            packet.put("Target Position", targetPosition);
            packet.put("Motor Power", map.DrawerSlide.getPower());
            dashboard.sendTelemetryPacket(packet);

            // Regular telemetry
            telemetry.addData("Current Pos", map.DrawerSlide.getCurrentPosition());
            telemetry.addData("Target Pos", targetPosition);
            telemetry.addData("Motor Power", map.DrawerSlide.getPower());
            telemetry.addData("Buttons", "⬜: %.2f ⭕: %.2f ❌: %.2f 🔺: %.2f",
                    gamepad1.square ? 1.0 : 0.0,
                    gamepad1.circle ? 1.0 : 0.0,
                    gamepad1.cross ? 1.0 : 0.0,
                    gamepad1.triangle ? 1.0 : 0.0);
            telemetry.update();
        }
    }
}
