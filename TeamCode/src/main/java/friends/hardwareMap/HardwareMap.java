package friends.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

public class HardwareMap {
    /*
        -----------------------------------------------------------------------
        | FRW               | Front Right Wheel     | Control Hub Motor 3     |
        --------------------+-----------------------+--------------------------
        | FLW               | Front Left Wheel      | Control Hub Motor 0     |
        --------------------+-----------------------+--------------------------
        | BRW               | Back Right Wheel      | Control Hub Motor 2     |
        --------------------+-----------------------+--------------------------
        | BLW               | Back Left Wheel       | Control Hub Motor 1     |
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
        | VBS               | Viper Bucket Servo    | Control Hub Servo 1     |
        -----------------------------------------------------------------------
        | RES               | Right Extend Servo    | Control Hub Servo 0     |
        -----------------------------------------------------------------------
        | LES               | Left Extend Servo     | Control Hub Servo 2     |
        -----------------------------------------------------------------------
        | RAS               | Right Arm Servo       | Control Hub Servo 3     |
        -----------------------------------------------------------------------
        | LAS               | Left Arm Servo        | Control Hub Servo 4     |
        -----------------------------------------------------------------------
        | CLS               | Claw Servo            | Control Hub Servo 5     |
        -----------------------------------------------------------------------
        | IMU               | IMU                   | ???                     |
        -----------------------------------------------------------------------
     */

    public DcMotorEx FrontRightMotor;
    public DcMotorEx FrontLeftMotor;
    public DcMotorEx BackRightMotor;
    public DcMotorEx BackLeftMotor;

    public DcMotorEx RightOdometer;
    public DcMotorEx LeftOdometer;
    public DcMotorEx CenterOdometer;

    public DcMotorEx RightViperMotor;
    public DcMotorEx LeftViperMotor;

    public Servo RightExtendServo;
    public Servo LeftExtendServo;

    public Servo ViperBucketServo;
    public Servo RightArmServo;
    public Servo LeftArmServo;
    public Servo ClawServo;

    public IMU Imu;

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

        RightExtendServo = hardwaremap.get(Servo.class, "RES");
        LeftExtendServo = hardwaremap.get(Servo.class, "LES");
        LeftExtendServo.setDirection(Servo.Direction.REVERSE);

        ViperBucketServo = hardwaremap.get(Servo.class, "VBS");

        RightArmServo = hardwaremap.get(Servo.class, "RAS");
        RightArmServo.setDirection(Servo.Direction.REVERSE);

        LeftArmServo = hardwaremap.get(Servo.class, "LAS");

        ClawServo = hardwaremap.get(Servo.class, "CLS");

        Imu = hardwaremap.get(IMU.class, "imu");
    }
}