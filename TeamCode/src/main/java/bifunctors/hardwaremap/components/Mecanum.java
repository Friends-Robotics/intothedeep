package bifunctors.hardwaremap.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Mecanum {
    private final DcMotorSimple
            frontRightMotor,
            frontLeftMotor,
            backRightMotor,
            backLeftMotor;

    public double PowerMultiplier = 1;

    /**
     * Creates the Mecanum object. Sets private fields and configures motor directions.
     * @param frontRightMotor Front Right Motor Object
     * @param frontLeftMotor Front Left Motor Object
     * @param backRightMotor Back Right Motor Object
     * @param backLeftMotor Back Left Motor Object
     */
    public Mecanum(DcMotorSimple frontRightMotor, DcMotorSimple backRightMotor, DcMotorSimple backLeftMotor, DcMotorSimple frontLeftMotor, double powerMultiplier){
        this.frontRightMotor = frontRightMotor;
        this.backRightMotor = backRightMotor;
        this.backLeftMotor = backLeftMotor;
        this.frontLeftMotor = frontLeftMotor;
        this.PowerMultiplier = powerMultiplier;
    }

    public void SendMecanumTelemetry(Telemetry telemetry){
        telemetry.addLine().addData("FR power", frontRightMotor.getPower());
        telemetry.addLine().addData("BR power", backRightMotor.getPower());
        telemetry.addLine().addData("BL power", backLeftMotor.getPower());
        telemetry.addLine().addData("FL power", frontLeftMotor.getPower());
    }

    /**
     * Analyses gamepad and sets power of motors appropriately
     * @param gp Gamepad object
     */
    public void Move(Gamepad gp) {
        // If invalid power multiplier is provided then clamp to either 0 or 1
        PowerMultiplier = Math.max(0, Math.min(1, PowerMultiplier));

        // Y values need to be inverted
        double y = -gp.left_stick_y;
        double x = gp.left_stick_x;
        double rx = gp.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontRightPower = (y - x - rx) / denominator;
        double frontLeftPower = (y + x + rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;

        frontRightMotor.setPower(frontRightPower * PowerMultiplier);
        frontLeftMotor.setPower(frontLeftPower * PowerMultiplier);
        backRightMotor.setPower(backRightPower * PowerMultiplier);
        backLeftMotor.setPower(backLeftPower * PowerMultiplier);
    }
}
