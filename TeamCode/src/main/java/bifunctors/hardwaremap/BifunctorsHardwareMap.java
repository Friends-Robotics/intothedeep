package bifunctors.hardwaremap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import bifunctors.hardwaremap.components.Mecanum;

public class BifunctorsHardwareMap {
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
    public Mecanum MecanumSet;

    public BifunctorsHardwareMap(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap){
        FrontRightWheel = hardwareMap.get(DcMotorEx.class, "FRW");
        FrontRightWheel.setDirection(DcMotorSimple.Direction.FORWARD);

        BackRightWheel = hardwareMap.get(DcMotorEx.class, "BRW");
        BackRightWheel.setDirection(DcMotorSimple.Direction.REVERSE);

        BackLeftWheel = hardwareMap.get(DcMotorEx.class, "BLW");
        BackLeftWheel.setDirection(DcMotorSimple.Direction.FORWARD);

        FrontLeftWheel = hardwareMap.get(DcMotorEx.class, "FLW");
        FrontLeftWheel.setDirection(DcMotorSimple.Direction.REVERSE);

        for(DcMotorEx motor : new DcMotorEx[]{ FrontRightWheel, BackRightWheel, BackLeftWheel, FrontLeftWheel }){
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        LeftViperSlide = hardwareMap.get(DcMotorEx.class, "LVS");
        LeftViperSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        LeftViperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftViperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftViperSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        RightViperSlide = hardwareMap.get(DcMotorEx.class, "RVS");
        RightViperSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        RightViperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightViperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightViperSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}