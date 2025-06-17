package friends.teleOp;

import static com.qualcomm.robotcore.hardware.Gamepad.LED_DURATION_CONTINUOUS;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import static friends.helper.Colours.YELLOW;
import static friends.helper.gamepad.GamepadButton.*;
import friends.hardwareMap.HardwareMap;
import friends.hardwareMap.components.Intake;
import friends.helper.Colours;
import friends.helper.gamepad.GamepadEx;

@TeleOp(name = "Intake Testing", group = "Testing")
public class IntakeTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {
        HardwareMap map = new HardwareMap(hardwareMap);
        Intake intake = new Intake(map);

        GamepadEx primary = new GamepadEx(gamepad1);

        primary.pressed(RIGHT_BUMPER, intake::slideOut);
        primary.pressed(LEFT_BUMPER, intake::slideIn);
        primary.pressed(CROSS, intake::cycle);

        primary.down(CIRCLE, intake::ready);
        primary.down(TOUCHPAD, intake::spit);

        primary.up(CIRCLE, (gamepad) -> {
            if(gamepad.touchpad) return;
            intake.standby();
        });

        waitForStart();

        if (isStopRequested()) return;

        while(opModeIsActive()){
            primary.update();

            intake.slide();

            telemetry.update();
        }
    }
}
