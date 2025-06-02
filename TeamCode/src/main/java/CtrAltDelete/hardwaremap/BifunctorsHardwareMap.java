package CtrAltDelete.hardwaremap;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.TouchSensor;

import CtrAltDelete.hardwaremap.components.Mecanum;
import CtrAltDelete.teleop.ColourSensor;

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
    public IMU RobotIMU;
    public ColorSensor ColorSensor;
    public TouchSensor ts;
    public DcMotorEx test;
    public Mecanum MecanumSet;

    public BifunctorsHardwareMap(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap){
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
        ColorSensor = hardwareMap.get(ColorSensor.class, "cs");
        ts = hardwareMap.get(TouchSensor.class, "ts");

        //Change these to how the control hub is positioned
        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        RobotIMU.initialize(new IMU.Parameters(orientation));

        test = hardwareMap.get(DcMotorEx.class, "t");
    }

    public int DetermineColour(double r, double g, double b){
        if(r > 120){
            return 1;
        }
        else if(b > 120){
            return -1;
        }
        else if(g > 80){
            return 0;
        }
        else{
            return -2;
        }
    }
}