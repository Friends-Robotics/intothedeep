package friends.teleOp;

import static friends.helper.gamepad.GamepadButton.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Optional;

import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Arm;
import friends.hardwareMap.components.Mecanum;
import friends.helper.Count;
import friends.helper.MotorControl.PIDFController;
import friends.helper.MotorControl.ViperPIDFConstants;
import friends.helper.gamepad.GamepadEx;

@TeleOp(name="Scoring Testing", group="Testing")
public class ScoringTeleOp extends LinearOpMode {
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
                1,
                map.Mew);

        telemetry.addData("Status", "Initialised HardwareMap");

        GamepadEx primary = new GamepadEx(gamepad1);
        PIDFController viperpidf = new PIDFController(ViperPIDFConstants.KP, ViperPIDFConstants.KI, ViperPIDFConstants.KD, ViperPIDFConstants.KF);

        Count target = new Count();
        Arm arm = new Arm(map, Optional.of(target));

        telemetry.addData("Status","Initialised GamepadEx");

        primary.down(CROSS, (gamepad) -> {
            if(gamepad.circle) return;
            arm.readyToScore();
        });
        primary.released(CROSS, arm::score);

        primary.down(CIRCLE, (gamepad) -> {
            if(gamepad.cross) return;
            arm.readyToWall();
        });
//        primary.released(CIRCLE, arm::wall);

        primary.pressed(LEFT_BUMPER, arm::closeClaw);
        primary.pressed(RIGHT_BUMPER, arm::openClaw);

        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            primary.update();

            mecanum.move(gamepad1);

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

