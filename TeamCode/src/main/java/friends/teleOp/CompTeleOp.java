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
        Mecanum m = new Mecanum(map.FrontRightMotor,
                map.BackRightMotor,
                map.BackLeftMotor,
                map.FrontLeftMotor,
                1,
                map.Mew);

        Intake intake = new Intake(map);
        Arm arm = new Arm(map);

        telemetry.addData("Status", "Initialised Mecanum");

        PIDFController viperpidf = new PIDFController(SlidePIDFConstants.KP, SlidePIDFConstants.KI, SlidePIDFConstants.KD, SlidePIDFConstants.KF);

        telemetry.addData("Status", "Initialised PIDF Controller");

        GamepadEx primary = new GamepadEx(gamepad1);
        GamepadEx secondary = new GamepadEx(gamepad2);

        telemetry.addData("Status", "Initialised GamepadEx");

        Count viperTarget = new Count();
        viperTarget.value = 0;

        ///  Primary Controls
        ///  Right Bumper -> Sets High Power
        ///  Left Bumper -> Sets Mid Power
        ///  Left Bumper Hold -> Sets Low Power
        primary.pressed(RIGHT_BUMPER, m::HighPower);
        primary.down(LEFT_BUMPER, m::LowPower);
        primary.up(LEFT_BUMPER, m::MidPower);

        /// Secondary Controls
        ///  Right Bumper -> Sets Intake to ready position
        ///  Left Bumper -> Spits Intake
        ///  Touchpad -> Cycles Intake To Next Colour
        ///  Triangle -> Sets arm to scoring position
        ///  Circle -> Sets arm to wall position
        secondary.pressed(RIGHT_BUMPER, intake::ready);
        secondary.pressed(LEFT_BUMPER, intake::spit);
        secondary.pressed(TOUCHPAD, (gamepad) -> {
            intake.cycle();
            Colours col = intake.getColour();
            gamepad.setLedColor(col.R(), col.G(), col.B(), Gamepad.LED_DURATION_CONTINUOUS);
        });

        secondary.pressed(TRIANGLE, () -> viperTarget.value = arm.scoring());
        secondary.pressed(CIRCLE, () -> viperTarget.value = arm.wall());

        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            primary.update();
            secondary.update();

            m.Move(gamepad1);

            // Set power of viper slides
            double power = viperpidf.PIDControl(map.RightViperMotor.getCurrentPosition(), (int)viperTarget.value);
            map.LeftViperMotor.setPower(power);
            map.RightViperMotor.setPower(power);

            telemetry.addData("Current Viper Target", viperTarget.value);

            telemetry.update();
        }
    }
}
