package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import friends.helper.GamepadEx;

import static friends.helper.GamepadButton.*;

class Example {
    public int X = 0;
}

@TeleOp(name="GamepadExTesting", group="Linear OpMode")
public class GamepadExTesting extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Create hardware map
        // HardwareMap map = new HardwareMap(hardwareMap);

        telemetry.addData("Status", "Initialised HardwareMap");

        // Create mecanum drive
//        Mecanum m = new Mecanum(map.FrontRightMotor,
//                map.BackRightMotor,
//                map.BackLeftMotor,
//                map.FrontLeftMotor,
//                1,
//                map.Mew);

        telemetry.addData("Status", "Initialised Mecanum");

        Example e = new Example();

        GamepadEx primary = new GamepadEx(gamepad1);

        telemetry.addData("Status","Initialised GamepadEx");

        primary.bind(A, (c, reader) -> {
            telemetry.addLine("A Pressed");
            if(!reader.justPressed()) return;
            e.X += 1;
            int col = e.X % 3;
            telemetry.addLine("A Just Pressed");
            gamepad1.setLedColor(255 * (col == 1 ? 1 : 0), 255 * (col == 2 ? 1 : 0), 255 * (col == 0 ? 1 : 0), -1);
        });

         primary.bindAlt(A, (c, reader) -> {
             telemetry.addLine("A Not Pressed");
         });

        telemetry.addLine("Bound A");
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // m.Move(gamepad1);

            primary.update(gamepad1);
            telemetry.addData("Col val", e.X % 3);

            telemetry.update();
        }
    }
}
