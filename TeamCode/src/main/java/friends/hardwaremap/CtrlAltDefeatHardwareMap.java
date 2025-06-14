package friends.hardwaremap;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class CtrlAltDefeatHardwareMap {
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
        | LVS               | Left Viper Slide      | Expansion Hub Motor 1   |
        --------------------+-----------------------+--------------------------
        | RVS               | Right Viper Slide     | Expansion Hub Motor 2   |
        --------------------+-----------------------+--------------------------
    */
    public DcMotorEx FrontLeftWheel;
    public DcMotorEx FrontRightWheel;
    public DcMotorEx BackLeftWheel;
    public DcMotorEx BackRightWheel;
    public DcMotorEx LeftViperSlide;
    public DcMotorEx RightViperSlide;
    public IMU RobotIMU;
    public ColorSensor ColorSensor;
    public TouchSensor ts;
    public DcMotorEx DrawerSlide;
    public Servo IntakeServo;
    public DcMotorEx IntakeMotor;

    public Servo WristServo;
    public Servo ClawServo;

    public CtrlAltDefeatHardwareMap(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap){
        FrontRightWheel = hardwareMap.get(DcMotorEx.class, "FRW");
        FrontRightWheel.setDirection(DcMotorSimple.Direction.REVERSE);

        BackRightWheel = hardwareMap.get(DcMotorEx.class, "BRW");
        BackRightWheel.setDirection(DcMotorSimple.Direction.REVERSE);

        BackLeftWheel = hardwareMap.get(DcMotorEx.class, "BLW");
        BackLeftWheel.setDirection(DcMotorSimple.Direction.FORWARD);

        FrontLeftWheel = hardwareMap.get(DcMotorEx.class, "FLW");
        FrontLeftWheel.setDirection(DcMotorSimple.Direction.FORWARD);

        for(DcMotorEx motor : new DcMotorEx[]{ FrontRightWheel, BackRightWheel, BackLeftWheel, FrontLeftWheel }){
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        RobotIMU = hardwareMap.get(IMU.class, "imu");
//        ColorSensor = hardwareMap.get(ColorSensor.class, "cs");
//        ts = hardwareMap.get(TouchSensor.class, "ts");

        //Change these to how the control hub is positioned
        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        RobotIMU.initialize(new IMU.Parameters(orientation));

        DrawerSlide = hardwareMap.get(DcMotorEx.class, "slide");
        DrawerSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DrawerSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DrawerSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LeftViperSlide = hardwareMap.get(DcMotorEx.class, "LVM");
        LeftViperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftViperSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        LeftViperSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LeftViperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RightViperSlide = hardwareMap.get(DcMotorEx.class, "RVM");
        RightViperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightViperSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        RightViperSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightViperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IntakeMotor = hardwareMap.get(DcMotorEx.class, "IM");
        IntakeServo = hardwareMap.get(Servo.class, "IS");
        IntakeServo.scaleRange(0.13, 0.5); //CHECK


//        WristServo = hardwareMap.get(Servo.class, "WS");
//
//        ClawServo = hardwareMap.get(Servo.class, "CS");

//        IntakeServo = hardwareMap.get(Servo.class, "iServo");
//        IntakeServo.scaleRange(0.13, 0.5); //CHECK
//
//        IntakeMotor = hardwareMap.get(DcMotorEx.class, "iMotor");
//        IntakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}