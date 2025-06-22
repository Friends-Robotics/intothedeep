package friends.teleOp;

import static friends.helper.gamepad.GamepadButton.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Optional;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
import friends.hardwareMap.components.Hang;
import friends.hardwareMap.components.Mecanum;
import friends.helper.Count;
import friends.helper.MotorControl.PIDFController;
import friends.helper.MotorControl.ViperPIDFConstants;
import friends.helper.gamepad.GamepadEx;

@TeleOp(name="Scoring Testing", group="Testing")
public class ScoringTeleOp extends LinearOpMode {
    Hang hang;

    Count hang_macro_state = new Count();
    @Override
    public void runOpMode() {
        HardwareMap map = new HardwareMap(hardwareMap);
        map.RightViperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        map.LeftViperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        map.RightViperMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        map.LeftViperMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Mecanum mecanum = new Mecanum(map.FrontRightMotor,
                map.BackRightMotor,
                map.BackLeftMotor,
                map.FrontLeftMotor,
                1);

        telemetry.addData("Status", "Initialised HardwareMap");

        GamepadEx primary = new GamepadEx(gamepad1);
        PIDFController viperpidf = new PIDFController(ViperPIDFConstants.KP, ViperPIDFConstants.KI, ViperPIDFConstants.KD, ViperPIDFConstants.KF);

        Count target = new Count();
        Arm arm = new Arm(map, Optional.of(target));
        hang = new Hang(map);

        telemetry.addData("Status","Initialised GamepadEx");

        primary.down(CROSS, (gamepad) -> {
            if(gamepad.circle) return;
            arm.looseClaw();
            arm.readyToScore();
        });
        primary.released(CROSS, () -> { arm.closeClaw(); arm.score(); });

        primary.down(CIRCLE, (gamepad) -> {
            if(gamepad.cross) return;
            arm.readyToWall();
        });
        primary.released(CIRCLE, arm::fromWall);

        primary.pressed(TOUCHPAD, () -> {
            hang.setUnlatch();
            target.value = 900;
        });

        primary.pressed(PLAYSTATION, () -> {
            if(target.value > 20) return;
            hang_macro_state.value = 1;
        });

        primary.pressed(DPAD_RIGHT, arm::closeClaw);
        primary.pressed(DPAD_LEFT, arm::openClaw);
        primary.pressed(DPAD_UP, arm::looseClaw);

        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            primary.update();

            mecanum.move(gamepad1);


            // Open viper slide if above value
            if(map.RightViperMotor.getCurrentPosition() > 865){
                arm.openClaw();
            }

            switch((int) hang_macro_state.value) {
                case 1:
                    break;
                case 2:
                    target.value = 40;
                    if (map.RightViperMotor.getCurrentPosition() < 45) hang_macro_state.value = 1;
                    break;
                case 3:
                    hang.setLatch();
                    hang_macro_state.value = 2;
                    break;
                case 4:
                    target.value = 5000;
                    if(map.RightViperMotor.getCurrentPosition() > 4900) {
                        hang_macro_state.value = 3;
                    }
                    break;
                case 5:
                    map.RightViperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    map.LeftViperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                    target.value = 2000;
                    if(map.RightViperMotor.getCurrentPosition() < 2100) {
                        hang_macro_state.value = 0;
                    }
                    break;
            }

            double power = viperpidf.PIDControl(map.RightViperMotor.getCurrentPosition(), (int)target.value);
            map.LeftViperMotor.setPower(power);
            map.RightViperMotor.setPower(power);

            telemetry.addData("ticks", map.RightViperMotor.getCurrentPosition());
            telemetry.addData("ticks left", map.LeftViperMotor.getCurrentPosition());

            telemetry.addData("right servo", map.RightArmServo.getPosition());
            telemetry.addData("left servo", map.LeftArmServo.getPosition());

            telemetry.update();
        }
    }
}

