package friends.hardwareMap;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

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
    | RVM               | Right Viper Motor     | Expansion Hub Motor 0   |
    -----------------------------------------------------------------------
    | LVM               | Left Viper Motor      | Expansion Hub Motor 1   |
    -----------------------------------------------------------------------
    | IM                | Intake Motor          | Expansion Hub Motor 2   |
    -----------------------------------------------------------------------
    | DS                | Horizontal Motor      | Expansion Hub Motor 3   |
    -----------------------------------------------------------------------
    | RHS               | Right Hang Servo      | Control Hub Servo 0     |
    -----------------------------------------------------------------------
    | LHS               | Left Hang Servo       | Control Hub Servo 1     |
    -----------------------------------------------------------------------
    | RAS               | Right Arm Servo       | Control Hub Servo 1     |
    -----------------------------------------------------------------------
    | LAS               | Left Arm Servo        | Control Hub Servo 0     |
    -----------------------------------------------------------------------
    | IS                | Intake Servo          | Expansion Hub Servo 0   |
    -----------------------------------------------------------------------
    | CL                | Claw Servo            | Expansion Hub Servo 1   |
    -----------------------------------------------------------------------
    | WR                | Wrist Servo           | Expansion b Servo 2     |
    -----------------------------------------------------------------------
    | IMU               | IMU                   | I2C Bus 0               |
    -----------------------------------------------------------------------
    | CS                | Colour Sensor         | I2C Bus 1               |
    -----------------------------------------------------------------------
    | OTOS              | OTOS                  | I2C Bus 2               |
    -----------------------------------------------------------------------
 */

public class HardwareMap {

    public DcMotorEx FrontRightMotor;
    public DcMotorEx FrontLeftMotor;
    public DcMotorEx BackRightMotor;
    public DcMotorEx BackLeftMotor;

    public DcMotorEx RightViperMotor;
    public DcMotorEx LeftViperMotor;

    public IMU Mew;
    public ColorSensor ColorSensor;
    public ServoImplEx RightHangServo;
    public ServoImplEx LeftHangServo;
    public Servo RightArmServo;
    public Servo LeftArmServo;
    public DcMotorEx DrawerSlideMotor;
    public Servo IntakeServo;
    public DcMotorEx IntakeMotor;
    public Servo Claw;
    public Servo Wrist;

    public HardwareMap(com.qualcomm.robotcore.hardware.HardwareMap hardwaremap, DcMotor.RunMode runMode) {
        FrontRightMotor = hardwaremap.get(DcMotorEx.class, "FRW");
        FrontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        FrontLeftMotor = hardwaremap.get(DcMotorEx.class, "FLW");
        FrontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        BackRightMotor = hardwaremap.get(DcMotorEx.class, "BRW");
        BackRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        BackLeftMotor = hardwaremap.get(DcMotorEx.class, "BLW");
        BackLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        for(DcMotorEx motor : new DcMotorEx[]{ FrontRightMotor, BackRightMotor, BackLeftMotor, FrontLeftMotor }){
            motor.setMode(runMode);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        RightViperMotor = hardwaremap.get(DcMotorEx.class, "RVM");
        RightViperMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        RightViperMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        LeftViperMotor = hardwaremap.get(DcMotorEx.class, "LVM");
        LeftViperMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        LeftViperMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Mew = hardwaremap.get(IMU.class, "IMU");

        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        Mew.initialize(new IMU.Parameters(orientation));

        Claw = hardwaremap.get(Servo.class, "CL");
        Claw.scaleRange(0.35, 0.7);
        Wrist = hardwaremap.get(Servo.class, "WR");

        ColorSensor = hardwaremap.get(ColorSensor.class, "CS");

        DrawerSlideMotor = hardwaremap.get(DcMotorEx.class, "DS");
        DrawerSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DrawerSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        RightArmServo = hardwaremap.get(Servo.class, "RAS");
        RightArmServo.setPosition(0.5);
        LeftArmServo = hardwaremap.get(Servo.class, "LAS");
        LeftArmServo.setPosition(0.5);

        IntakeMotor = hardwaremap.get(DcMotorEx.class, "IM");
        IntakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        IntakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        IntakeServo = hardwaremap.get(Servo.class, "IS");
        IntakeServo.scaleRange(0.35, 0.65);
        IntakeServo.setPosition(1);
    }

    public HardwareMap(com.qualcomm.robotcore.hardware.HardwareMap hardwaremap) {
        this(hardwaremap, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public DcMotor GetTestingMotor() {
        return FrontRightMotor;
    }
}
