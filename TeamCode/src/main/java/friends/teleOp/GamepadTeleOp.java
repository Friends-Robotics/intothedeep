package friends.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import friends.helper.Count;
import friends.helper.GamepadEx;

import static friends.helper.GamepadButton.*;

@TeleOp(name="Gamepad Testing", group="Testing")
public class GamepadTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        Count count = new Count();
        telemetry.addData("Status","Initialised Count");

        GamepadEx primary = new GamepadEx(gamepad1);

        telemetry.addData("Status","Initialised GamepadEx");


        // KEY => A
        // FUN => Change colour based on count
        primary.pressed(A, (c, reader) -> {
            count.value += 1;
            int col = count.value % 3;
            telemetry.addLine("A Just Pressed");
            gamepad1.setLedColor(255 * (col == 1 ? 1 : 0), 255 * (col == 2 ? 1 : 0), 255 * (col == 0 ? 1 : 0), -1);
        });

        // KEY => A
        // FUN => Add telemetry
        primary.down(A, (gamepad, reader) -> {
            telemetry.addLine("A Pressed");
        });

        // KEY => !A
        // FUN => Add telemetry
         primary.up(A, (c, reader) -> {
             telemetry.addLine("A Not Pressed");
         });

        telemetry.addLine("Bound A");
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            primary.update();
            telemetry.addData("Colour val", count.value % 3);

            telemetry.update();
        }
    }
}
