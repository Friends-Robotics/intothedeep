package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
import friends.hardwareMap.components.Hang;
import friends.hardwareMap.components.Intake;
import friends.hardwareMap.components.Mecanum;
import friends.helper.Colours;
import friends.helper.Count;
import friends.helper.MotorControl.ViperPIDFConstants;
import friends.helper.gamepad.GamepadEx;
import friends.helper.MotorControl.PIDFController;

import static friends.helper.gamepad.GamepadButton.*;

import java.util.Optional;
import java.util.Timer;

@TeleOp(name = "Competition", group = "Competition")
public class CompTeleOp extends LinearOpMode {
    // Components
    Intake intake;
    Arm arm;
    Hang hang;
    Count macro_state = new Count();
    Count viper_target = new Count();
    Timer hangTimer = new Timer();

    @Override
    public void runOpMode() {
        HardwareMap map = new HardwareMap(hardwareMap, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.addData("Status", "Initialised HardwareMap");

        Mecanum mecanum = new Mecanum(map.FrontRightMotor,
                map.BackRightMotor,
                map.BackLeftMotor,
                map.FrontLeftMotor,
                1);
        telemetry.addData("Status", "Initialised Mecanum");

        intake = new Intake(map);
        arm = new Arm(map, Optional.of(viper_target));
        hang = new Hang(map);
        telemetry.addData("Status", "Initialised Components");

        arm.openClaw();

        PIDFController viper_controller = new PIDFController(ViperPIDFConstants.KP, ViperPIDFConstants.KI, ViperPIDFConstants.KD, ViperPIDFConstants.KF, ViperPIDFConstants.tolerance);
        telemetry.addData("Status", "Initialised PIDF Controller");

        GamepadEx primary = new GamepadEx(gamepad1);
        GamepadEx secondary = new GamepadEx(gamepad2);
        telemetry.addData("Status", "Initialised GamepadEx");

        primary.down(RIGHT_BUMPER, mecanum::lowPower);
        primary.up(RIGHT_BUMPER, mecanum::highPower);
        ///  Primary Controls
        /// Touchpad -> Unlatch Level 2 Hooks
        primary.pressed(TOUCHPAD, () -> {
            hang.setUnlatch();
            viper_target.value = 900;
        });

        primary.pressed(PLAYSTATION, () -> {
            if(viper_target.value > 20) return;
            macro_state.value = 0;
        });

        primary.down(ALWAYS, mecanum::move);

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
        secondary.released(CIRCLE,  arm::wall);
        secondary.down(CIRCLE, (gamepad) -> {
            if(gamepad.cross) return;
            arm.readyToWall();
        });

        secondary.down(TRIANGLE, (gamepad) -> {
            if(gamepad.circle) return;
            arm.readyToScore();
        });
        secondary.released(TRIANGLE, arm::score);

        secondary.pressed(DPAD_RIGHT, arm::openClaw);
        secondary.pressed(DPAD_LEFT, arm::closeClaw);
        secondary.pressed(DPAD_UP, arm::slideClaw);

        secondary.down(RIGHT_BUMPER, intake::ready);
        secondary.up(RIGHT_BUMPER, intake::standby);
        secondary.down(LEFT_BUMPER, intake::spit);

        secondary.pressed(TOUCHPAD, intake::cycle);

        telemetry.update();

        arm.openClaw();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // Update both primary and secondary game-pads
            primary.update();
            primary.setColour(mecanum.getColour());

            secondary.update();
            secondary.setColour(intake.getColour());

            telemetry.addData("Current Colour", intake.ViewedColour);

            switch((int)macro_state.value) {
                case 1:
                    break;
                case 2:
                    viper_target.value = 40;
                    if (map.RightViperMotor.getCurrentPosition() < 45) macro_state.value = 1;
                    break;
                case 3:
                    hang.setLatch();
                    macro_state.value = 2;
                    break;
                case 4:
                    viper_target.value = 5000;
                    if(map.RightViperMotor.getCurrentPosition() > 4900) {
                        macro_state.value = 3;
                    }
                    break;
                case 5:
                    map.RightViperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    map.LeftViperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    viper_target.value = 2000;
                    if(map.RightViperMotor.getCurrentPosition() < 2100) {
                        macro_state.value = 0;
                    }
                    break;
            }

            // Who cares
            intake.slideOutWithSetPower(-gamepad2.left_stick_y);

            // PID for viper
            double power = viper_controller.PIDControl(map.RightViperMotor.getCurrentPosition(), (int)viper_target.value);
            map.LeftViperMotor.setPower(power);
            map.RightViperMotor.setPower(power);

            if(map.RightViperMotor.getCurrentPosition() > 865){
                arm.openClaw();
            }

            telemetry.addData("Current Viper Target", viper_target.value);
            telemetry.update();
        }
    }
}

