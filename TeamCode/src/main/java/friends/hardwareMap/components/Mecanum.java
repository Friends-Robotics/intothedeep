package friends.hardwareMap.components;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import friends.helper.Colours;

public class Mecanum {
    private final DcMotorEx
            frontRightMotor,
            frontLeftMotor,
            backRightMotor,
            backLeftMotor;

    public double powerMultiplier = 1;

    /**
     * Creates the Mecanum object. Sets private fields and configures motor directions.
     * @param frontRightMotor Front Right Motor Object
     * @param frontLeftMotor Front Left Motor Object
     * @param backRightMotor Back Right Motor Object
     * @param backLeftMotor Back Left Motor Object
     */
    public Mecanum(DcMotorEx frontRightMotor, DcMotorEx backRightMotor, DcMotorEx backLeftMotor, DcMotorEx frontLeftMotor, double powerMultiplier){
        this.frontRightMotor = frontRightMotor;
        this.backRightMotor = backRightMotor;
        this.backLeftMotor = backLeftMotor;
        this.frontLeftMotor = frontLeftMotor;
        this.powerMultiplier = powerMultiplier;
    }

    /**
     * Creates the Mecanum object. Sets private fields and configures motor directions.
     * @param frontRightMotor Front Right Motor Object
     * @param frontLeftMotor Front Left Motor Object
     * @param backRightMotor Back Right Motor Object
     * @param backLeftMotor Back Left Motor Object
     * @param robotIMU The robot imu for field centric movement
     */
    public Mecanum(DcMotorEx frontRightMotor, DcMotorEx backRightMotor, DcMotorEx backLeftMotor, DcMotorEx frontLeftMotor, double powerMultiplier, IMU robotIMU){
        this.frontRightMotor = frontRightMotor;
        this.backRightMotor = backRightMotor;
        this.backLeftMotor = backLeftMotor;
        this.frontLeftMotor = frontLeftMotor;
        this.powerMultiplier = powerMultiplier;
    }

    public void sendMecanumTelemetry(Telemetry telemetry){
        telemetry.addLine("FR")
                .addData("Power", frontRightMotor.getPower())
                        .addData("Current", frontRightMotor.getCurrent(CurrentUnit.AMPS));

        telemetry.addLine("BR")
                .addData("Power", backRightMotor.getPower())
                        .addData("Current", backRightMotor.getCurrent(CurrentUnit.AMPS));

        telemetry.addLine("BL")
                .addData("Power", backLeftMotor.getPower())
                        .addData("Current", backLeftMotor.getCurrent(CurrentUnit.AMPS));

        telemetry.addLine("FL")
                .addData("Power", frontLeftMotor.getPower())
                    .addData("Current", frontLeftMotor.getCurrent(CurrentUnit.AMPS));
    }

    /**
     * Analyses gamepad and sets power of motors appropriately
     * @param gp Gamepad object
     */
    public void move(Gamepad gp) {
        // If invalid power multiplier is provided then clamp to either 0 or 1
        powerMultiplier = Math.max(0, Math.min(1, powerMultiplier));

        // Y values need to be inverted
        double y = -gp.left_stick_y;
        double x = gp.left_stick_x * 1.1;
        double rx = gp.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontRightPower = (y - x - rx) / denominator;
        double frontLeftPower = (y + x + rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;

        frontRightMotor.setPower(frontRightPower * powerMultiplier);
        frontLeftMotor.setPower(frontLeftPower * powerMultiplier);
        backRightMotor.setPower(backRightPower * powerMultiplier);
        backLeftMotor.setPower(backLeftPower * powerMultiplier);
    }


    public void highPower() { powerMultiplier = 1; }
    public void midPower() { powerMultiplier = 0.5; }
    public void lowPower() { powerMultiplier = 0.5; }

    public Colours getColour() {
        if(powerMultiplier == 1)
            return Colours.RED;
        return Colours.GREEN;
    }
}
