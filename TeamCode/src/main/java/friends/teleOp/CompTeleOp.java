package friends.teleOp;

import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
import friends.hardwareMap.components.Hang;
import friends.hardwareMap.components.Intake;
import friends.hardwareMap.components.Mecanum;
import friends.helper.Count;
import friends.helper.MotorControl.ViperPIDFConstants;
import friends.helper.gamepad.GamepadEx;
import friends.helper.MotorControl.PIDFController;

import static friends.helper.gamepad.GamepadButton.*;

import java.util.Optional;

@TeleOp(name = "Competition", group = "Competition")
public class CompTeleOp extends LinearOpMode {
    // Components
    Mecanum mecanum;
    Intake intake;
    Arm arm;
    Hang hang;

    Timer pre_hang_timer = new Timer();
    Count pre_hang_macro_state = new Count();

    Timer hang_timer = new Timer();
    Count hang_macro_state = new Count();

    Count current_macro = new Count();

    PIDFController viper_controller = new PIDFController(ViperPIDFConstants.KP, ViperPIDFConstants.KI, ViperPIDFConstants.KD, ViperPIDFConstants.KF, ViperPIDFConstants.tolerance);
    Count viper_target = new Count();

    @Override
    public void runOpMode() {
        HardwareMap map = new HardwareMap(hardwareMap);
        telemetry.addData("Status", "Initialised HardwareMap");

        mecanum = new Mecanum(map.FrontRightMotor,
                map.BackRightMotor,
                map.BackLeftMotor,
                map.FrontLeftMotor,
                1);
        telemetry.addData("Status", "Initialised Mecanum");

        telemetry.speak("hello my devious little gremlin child");

        intake = new Intake(map);
        arm = new Arm(map, Optional.of(viper_target));
        hang = new Hang(map);
        telemetry.addData("Status", "Initialised Components");

        arm.openClaw();

        viper_controller = new PIDFController(ViperPIDFConstants.KP, ViperPIDFConstants.KI, ViperPIDFConstants.KD, ViperPIDFConstants.KF, ViperPIDFConstants.tolerance);
        telemetry.addData("Status", "Initialised PIDF Controller");

        GamepadEx primary = new GamepadEx(gamepad1);
        GamepadEx secondary = new GamepadEx(gamepad2);
        telemetry.addData("Status", "Initialised GamepadEx");

        primary.down(CROSS, mecanum::lowPower);
        primary.up(CROSS, mecanum::highPower);

        /// Primary Controls
        primary.down(ALWAYS, mecanum::move);

        primary.pressed(TOUCHPAD, () -> {
            pre_hang_macro_state.value = 1;
            current_macro.value = 1;
        });

        primary.pressed(PLAYSTATION, () -> {
            hang_macro_state.value = 1;
            current_macro.value = 2;
        });

        /// Secondary Controls
        secondary.pressed(CIRCLE, (gamepad) -> {
            if(gamepad.cross) return;
            arm.readyToWall();
        });
        secondary.released(CIRCLE,  arm::fromWall);

        secondary.pressed(TRIANGLE, (gamepad) -> {
            if(gamepad.circle) return;
            arm.readyToScore();
        });
        secondary.released(TRIANGLE, arm::score);

        secondary.pressed(DPAD_RIGHT, arm::closeClaw);
        secondary.pressed(DPAD_LEFT, arm::openClaw);
        secondary.pressed(DPAD_UP, arm::looseClaw);

        secondary.down(RIGHT_BUMPER, intake::ready);
        secondary.up(RIGHT_BUMPER, intake::standby);
        secondary.down(LEFT_BUMPER, intake::spit);

        secondary.pressed(TOUCHPAD, intake::cycle);
        secondary.down(ALWAYS, intake::slideOutWithSetPower);

        telemetry.update();

        arm.openClaw();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            primary.update();
            primary.setColour(mecanum.getColour());

            secondary.update();
            secondary.setColour(intake.getTargetColour());

            preHangMacro();
            hangMacro(map);

            // Open claw if the viper slides are above a value
            if(map.RightViperMotor.getCurrentPosition() > 865){
                arm.openClaw();
            }

            double power = viper_controller.PIDControl(map.RightViperMotor.getCurrentPosition(), (int)viper_target.value);
            map.RightViperMotor.setPower(power);
            map.LeftViperMotor.setPower(power);

            telemetry.addData("Currently Viewed Colour: ", intake.getViewedColour());
            telemetry.update();
        }
    }

    // COUNT -> 1
    private void preHangMacro() {
        if(current_macro.value > 1) return;
        switch ((int)pre_hang_macro_state.value) {
            case 0:
                break;
            case 1:
                viper_target.value = 900;
                pre_hang_macro_state.value = 2;
                pre_hang_timer.resetTimer();
                hang.setUnlatch();
                break;
            case 2:
                if (pre_hang_timer.getElapsedTimeSeconds() > 1.2) {
                    hang.setLatch();
                    pre_hang_macro_state.value = 3;
                    pre_hang_timer.resetTimer();
                }
                break;
            case 3:
                if (pre_hang_timer.getElapsedTimeSeconds() > 1.2) {
                    hang.powerOff();
                    pre_hang_macro_state.value = 0;
                }
                break;
        }
    }

    // Count -> 2
    private void hangMacro(HardwareMap map) {
        if (current_macro.value != 0 && current_macro.value != 2) return;
        switch((int) hang_macro_state.value) {
            case 0:
                break;
            case 1:
                viper_target.value = 40;
                if (map.RightViperMotor.getCurrentPosition() < 45) {
                    hang_macro_state.value = 2;
                    hang_timer.resetTimer();
                }
                break;
            case 2:
                hang.setLatch();
                if (hang_timer.getElapsedTimeSeconds() > 1.5) {
                    hang_macro_state.value = 3;
                }
                break;
            case 3:
                viper_target.value = 5000;
                if(map.RightViperMotor.getCurrentPosition() > 4900) {
                    hang_macro_state.value = 4;
                }
                break;
            case 4:
                map.RightViperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                map.LeftViperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                viper_target.value = 2000;
                if(map.RightViperMotor.getCurrentPosition() < 2100) {
                    hang_macro_state.value = 0;
                }
                break;
        }
    }
}