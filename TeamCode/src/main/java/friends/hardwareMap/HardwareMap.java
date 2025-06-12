package friends.hardwareMap;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

/*
    -----------------------------------------------------------------------
    | FRW               | Front Right Wheel     | Control Hub Motor 0     |
    --------------------+-----------------------+--------------------------
    | FLW               | Front Left Wheel      | Control Hub Motor 3     |
    --------------------+-----------------------+--------------------------
    | BRW               | Back Right Wheel      | Control Hub Motor 1     |
    --------------------+-----------------------+--------------------------
    | BLW               | Back Left Wheel       | Control Hub Motor 2     |
    --------------------+-----------------------+--------------------------
    | RO                | Right Odometer        | Control Hub Encoder 2   |
    --------------------+-----------------------+--------------------------
    | LO                | Left Odometer         | Control Hub Encoder 0   |
    --------------------+-----------------------+--------------------------
    | CO                | Centre Odometer       | Control Hub Encoder 1   |
    -----------------------------------------------------------------------
    | RVM               | Right Viper Motor     | Extension Hub Motor 0   |
    -----------------------------------------------------------------------
    | LVM               | Left Viper Motor      | Extension Hub Motor 1   |
    -----------------------------------------------------------------------
    | IMU               | IMU                   | I2C Bus 2               |
    -----------------------------------------------------------------------
    | OTOS              | OTOS                  | I2C Bus 1               |
    -----------------------------------------------------------------------
 */
public class HardwareMap {

    public DcMotorEx FrontRightMotor;
    public DcMotorEx FrontLeftMotor;
    public DcMotorEx BackRightMotor;
    public DcMotorEx BackLeftMotor;

    public DcMotorEx RightOdometer;
    public DcMotorEx LeftOdometer;
    public DcMotorEx CenterOdometer;

    public DcMotorEx RightViperMotor;
    public DcMotorEx LeftViperMotor;

    public IMU Mew;
    public ColorSensor ColorSensor;
    public Servo IntakeServo;
    public DcMotorEx IntakeMotor;

    public HardwareMap(com.qualcomm.robotcore.hardware.HardwareMap hardwaremap, DcMotor.RunMode runMode) {
        FrontRightMotor = hardwaremap.get(DcMotorEx.class, "FRW");
        FrontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        FrontLeftMotor = hardwaremap.get(DcMotorEx.class, "FLW");
        FrontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        BackRightMotor = hardwaremap.get(DcMotorEx.class, "BRW");
        BackRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        BackLeftMotor = hardwaremap.get(DcMotorEx.class, "BLW");
        BackLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        for(DcMotorEx motor : new DcMotorEx[]{ FrontRightMotor, BackRightMotor, BackLeftMotor, FrontLeftMotor }){
            motor.setMode(runMode);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

//        LeftOdometer = hardwaremap.get(DcMotorEx.class, "FLW");
//        LeftOdometer.setDirection(DcMotorSimple.Direction.REVERSE);
//        RightOdometer = hardwaremap.get(DcMotorEx.class, "BRW");
//        RightOdometer.setDirection(DcMotorEx.Direction.FORWARD);
//        CenterOdometer = hardwaremap.get(DcMotorEx.class, "BLW");
//        CenterOdometer.setDirection(DcMotorEx.Direction.FORWARD);

        RightViperMotor = hardwaremap.get(DcMotorEx.class, "RVM");
        RightViperMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        RightViperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LeftViperMotor = hardwaremap.get(DcMotorEx.class, "LVM");
        LeftViperMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        LeftViperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Mew = hardwaremap.get(IMU.class, "IMU");

        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        Mew.initialize(new IMU.Parameters(orientation));

//        ColorSensor = hardwaremap.get(ColorSensor.class, "cs");

//        IntakeServo = hardwaremap.get(Servo.class, "iServo");
//        IntakeServo.scaleRange(0.13, 0.5); //CHECK

//        IntakeMotor = hardwaremap.get(DcMotorEx.class, "iMotor");
//        IntakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        IntakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    public HardwareMap(com.qualcomm.robotcore.hardware.HardwareMap hardwaremap) {
        FrontRightMotor = hardwaremap.get(DcMotorEx.class, "FRW");
        FrontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        FrontLeftMotor = hardwaremap.get(DcMotorEx.class, "FLW");
        FrontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        BackRightMotor = hardwaremap.get(DcMotorEx.class, "BRW");
        BackRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        BackLeftMotor = hardwaremap.get(DcMotorEx.class, "BLW");
        BackLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

//        LeftOdometer = hardwaremap.get(DcMotorEx.class, "FLW");
//        LeftOdometer.setDirection(DcMotorSimple.Direction.REVERSE);
//        RightOdometer = hardwaremap.get(DcMotorEx.class, "BRW");
//        RightOdometer.setDirection(DcMotorEx.Direction.FORWARD);
//        CenterOdometer = hardwaremap.get(DcMotorEx.class, "BLW");
//        CenterOdometer.setDirection(DcMotorEx.Direction.FORWARD);

        RightViperMotor = hardwaremap.get(DcMotorEx.class, "RVM");
        RightViperMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        RightViperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LeftViperMotor = hardwaremap.get(DcMotorEx.class, "LVM");
        LeftViperMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        LeftViperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Mew = hardwaremap.get(IMU.class, "imu");

        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        Mew.initialize(new IMU.Parameters(orientation));

//        ColorSensor = hardwaremap.get(ColorSensor.class, "cs");

//        IntakeServo = hardwaremap.get(Servo.class, "iServo");
//        IntakeServo.scaleRange(0.13, 0.5); //CHECK

//        IntakeMotor = hardwaremap.get(DcMotorEx.class, "iMotor");
//        IntakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        IntakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    public DcMotor GetTestingMotor() {
        return FrontRightMotor;
    }
}
