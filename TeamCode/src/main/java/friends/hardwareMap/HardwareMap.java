package friends.hardwareMap;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class HardwareMap {
    public DcMotorEx FrontRightMotor;
    public DcMotorEx FrontLeftMotor;
    public DcMotorEx BackRightMotor;
    public DcMotorEx BackLeftMotor;

    public DcMotorEx RightViperMotor;
    public DcMotorEx LeftViperMotor;

    public ColorSensor ColorSensor;
    public Servo RightHangServo;
    public Servo LeftHangServo;
    public Servo RightArmServo;
    public Servo LeftArmServo;
    public DcMotorEx DrawerSlideMotor;
    public DcMotorEx IntakeMotor;
    public Servo IntakeServo;
    public Servo Claw;

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

        Claw = hardwaremap.get(Servo.class, "CL");

        ColorSensor = hardwaremap.get(ColorSensor.class, "CS");

        DrawerSlideMotor = hardwaremap.get(DcMotorEx.class, "DS");
        DrawerSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DrawerSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        RightArmServo = hardwaremap.get(Servo.class, "RAS");
        LeftArmServo = hardwaremap.get(Servo.class, "LAS");

        RightHangServo = hardwaremap.get(Servo.class, "RHS");
        LeftHangServo = hardwaremap.get(Servo.class, "LHS");

        IntakeMotor = hardwaremap.get(DcMotorEx.class, "IM");
        IntakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        IntakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        IntakeServo = hardwaremap.get(Servo.class, "IS");
    }

    public HardwareMap(com.qualcomm.robotcore.hardware.HardwareMap hardwaremap) {
        this(hardwaremap, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
