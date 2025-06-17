package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
import friends.hardwareMap.components.Intake;
import friends.hardwareMap.components.Mecanum;
import friends.helper.Count;
import friends.helper.MotorControl.ViperPIDFConstants;
import friends.helper.gamepad.GamepadEx;
import friends.helper.MotorControl.PIDFController;

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

        Count macro_state = new Count();

        telemetry.addData("Status", "Initialised Mecanum");

        PIDFController viper_controller = new PIDFController(ViperPIDFConstants.KP, ViperPIDFConstants.KI, ViperPIDFConstants.KD, ViperPIDFConstants.KF);

        telemetry.addData("Status", "Initialised PIDF Controller");

        GamepadEx primary = new GamepadEx(gamepad1);
        GamepadEx secondary = new GamepadEx(gamepad2);

        telemetry.addData("Status", "Initialised GamepadEx");

        Count viper_target = new Count();

        ///  Primary Controls
        ///  Right Bumper -> Sets High Power
        ///  Left Bumper -> Sets Mid Power
        ///  Left Bumper Hold -> Sets Low Power
        ///  DPad up -> Sets vipers to max
        ///  DPad down -> Sets vipers to 0
        primary.pressed(RIGHT_BUMPER, mecanum::HighPower);
        primary.down(LEFT_BUMPER, mecanum::LowPower);
        primary.released(LEFT_BUMPER, mecanum::MidPower);
        primary.pressed(DPAD_UP, ()   -> viper_target.value = 5000);
        primary.pressed(DPAD_DOWN, () -> viper_target.value = 0);

        /// Secondary Controls
        ///  Right Bumper -> Sets Intake to ready position
        ///  Left Bumper -> Spits Intake
        ///  Dpad Up -> Slide Intake Out
        ///  Dpad Down -> Slide Intake In
        ///  Touchpad -> Cycles Intake To Next Colour
        ///  Triangle -> Sets arm to scoring position
        ///  Circle -> Sets arm to wall position
        ///  Square -> Close claw
        ///  Cross -> Open claw
        ///  Right Stick -> Move intake position
        secondary.pressed(TRIANGLE, ()  -> viper_target.value = arm.readyToScore());
        secondary.pressed(CIRCLE, ()    -> viper_target.value = arm.readyToWall());
        secondary.pressed(SQUARE,       arm::closeClaw);
        secondary.pressed(CROSS,        arm::openClaw);
        secondary.pressed(RIGHT_BUMPER, intake::ready);
        secondary.pressed(LEFT_BUMPER,  intake::spit);
        secondary.pressed(TOUCHPAD,     intake::cycle);
        secondary.pressed(DPAD_UP,    intake::slideOut);
        secondary.pressed(DPAD_DOWN,   intake::slideIn);

        secondary.pressed(PLAYSTATION, () -> {
            if(viper_target.value > 20) return;
            macro_state.value = 1;
        });

        secondary.down(RIGHT_STICK, (gamepad) -> intake.slideToPos((int)((gamepad.right_stick_x + 1) / 2) * intake.Max()));

        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // Update both primary and secondary game-pads
            primary.update();
            primary.setColour(mecanum.getColour());
            secondary.update();
            secondary.setColour(intake.getColour());

            mecanum.Move(gamepad1);


            // PID for drawer
            intake.slide();

            switch((int)macro_state.value) {
                case 0:
                    break;
                case 1:
                    viper_target.value = 5000;
                    if(map.RightViperMotor.getCurrentPosition() > 4900) {
                        macro_state.value = 2;
                    }
                    break;
                case 2:
                    map.RightViperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    map.LeftViperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    viper_target.value = 2000;
                    if(map.RightViperMotor.getCurrentPosition() < 2100) {
                        macro_state.value = 0;
                    }
            }

            // PID for viper
            double power = viper_controller.PIDControl(map.RightViperMotor.getCurrentPosition(), (int)viper_target.value);
            map.LeftViperMotor.setPower(power);
            map.RightViperMotor.setPower(power);

            telemetry.addData("Current Viper Target", viper_target.value);

            telemetry.update();
        }
    }
}
