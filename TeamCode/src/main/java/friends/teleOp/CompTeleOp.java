package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
import friends.hardwareMap.components.Intake;
import friends.hardwareMap.components.Mecanum;
import friends.helper.Colours;
import friends.helper.Count;
import friends.helper.MotorControl.OutakePIDFConstants;
import friends.helper.gamepad.GamepadEx;
import friends.helper.MotorControl.PIDFController;
import friends.helper.MotorControl.SlidePIDFConstants;

import static friends.helper.gamepad.GamepadButton.*;

@TeleOp(name = "Competition", group = "Competition")
public class CompTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Create hardware map
        HardwareMap map = new HardwareMap(hardwareMap, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Initialised HardwareMap");

        // Create mecanum drive
        Mecanum mecanum = new Mecanum(map.FrontRightMotor,
                map.BackRightMotor,
                map.BackLeftMotor,
                map.FrontLeftMotor,
                1,
                map.Mew);

        Intake intake = new Intake(map);
        Arm arm = new Arm(map);

        telemetry.addData("Status", "Initialised Mecanum");

        PIDFController viper_pidf = new PIDFController(OutakePIDFConstants.KP, OutakePIDFConstants.KI, OutakePIDFConstants.KD, OutakePIDFConstants.KF);

        telemetry.addData("Status", "Initialised PIDF Controller");

        GamepadEx primary = new GamepadEx(gamepad1);
        GamepadEx secondary = new GamepadEx(gamepad2);

        telemetry.addData("Status", "Initialised GamepadEx");

        Count viper_target = new Count();
        viper_target.value = 0;

        ///  Primary Controls
        ///  Right Bumper -> Sets High Power
        ///  Left Bumper -> Sets Mid Power
        ///  Left Bumper Hold -> Sets Low Power
        primary.pressed(RIGHT_BUMPER, mecanum::HighPower);
        primary.down(LEFT_BUMPER, mecanum::LowPower);
        primary.up(LEFT_BUMPER, mecanum::MidPower);

        /// Secondary Controls
        ///  Right Bumper -> Sets Intake to ready position
        ///  Left Bumper -> Spits Intake
        ///  Touchpad -> Cycles Intake To Next Colour
        ///  Triangle -> Sets arm to scoring position
        ///  Circle -> Sets arm to wall position
        ///  Square -> Close claw
        ///  Cross -> Open claw
        secondary.pressed(TRIANGLE, () -> viper_target.value = arm.scoring());
        secondary.pressed(CIRCLE, () -> viper_target.value = arm.wall());
        secondary.pressed(SQUARE, arm::closeClaw);
        secondary.pressed(CROSS, arm::openClaw);

        secondary.pressed(RIGHT_BUMPER, intake::ready);
        secondary.pressed(LEFT_BUMPER, intake::spit);

        secondary.pressed(TOUCHPAD, (gamepad) -> {
            intake.cycle();
            Colours col = intake.getColour();
            secondary.setColour(col);
        });

        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // Update both primary and secondary game-pads
            primary.update();
            primary.setColour(mecanum.PowerGamepadColour());
            secondary.update();

            mecanum.Move(gamepad1);

            // Set power of viper slides to PIDF
            double power = viper_pidf.PIDControl(map.RightViperMotor.getCurrentPosition(), (int)viper_target.value);
            map.LeftViperMotor.setPower(power);
            map.RightViperMotor.setPower(power);

            telemetry.addData("Current Viper Target", viper_target.value);

            telemetry.update();
        }
    }
}
