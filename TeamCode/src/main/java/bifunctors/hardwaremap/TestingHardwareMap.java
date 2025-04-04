package bifunctors.hardwaremap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class TestingHardwareMap {
    /*
        -----------------------------------------------------------------------
        | FRW               | Front Right Wheel     | Control Hub Motor X     |
        --------------------+-----------------------+--------------------------
        | FLW               | Front Left Wheel      | Control Hub Motor X     |
        --------------------+-----------------------+--------------------------
        | BRW               | Back Right Wheel      | Control Hub Motor X     |
        --------------------+-----------------------+--------------------------
        | BLW               | Back Left Wheel       | Control Hub Motor X     |
        --------------------+-----------------------+--------------------------
    */
    public DcMotorEx FrontLeftWheel;
    public DcMotorEx FrontRightWheel;
    public DcMotorEx BackLeftWheel;
    public DcMotorEx BackRightWheel;

    public TestingHardwareMap(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap){
        FrontRightWheel = hardwareMap.get(DcMotorEx.class, "FRW");
        FrontRightWheel.setDirection(DcMotorSimple.Direction.FORWARD);

        BackRightWheel = hardwareMap.get(DcMotorEx.class, "BRW");
        BackRightWheel.setDirection(DcMotorSimple.Direction.REVERSE);

        BackLeftWheel = hardwareMap.get(DcMotorEx.class, "BLW");
        BackLeftWheel.setDirection(DcMotorSimple.Direction.FORWARD);

        FrontLeftWheel = hardwareMap.get(DcMotorEx.class, "FLW");
        FrontLeftWheel.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotorEx[] driveTrainMotors = new DcMotorEx[]{
                FrontRightWheel,
                BackRightWheel,
                BackLeftWheel,
                FrontLeftWheel
        };

        for(DcMotorEx motor : driveTrainMotors){
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }
}