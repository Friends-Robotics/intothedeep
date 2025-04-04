package bifunctors.hardwaremap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import bifunctors.hardwaremap.components.Mecanum;

public class BifunctorsHM {
    private final LinearOpMode opMode;
    public Mecanum Mecanum;
    private DcMotor frontRightWheelMotor;
    private DcMotor backRightWheelMotor;
    private DcMotor backLeftWheelMotor;
    private DcMotor frontLeftWheelMotor;
    private DcMotor[] driveTrainMotors;

    public BifunctorsHM(LinearOpMode opMode){this.opMode = opMode;}

    public void initAll(){
        initDriveTrain();
    }

    public void initDriveTrain()
    {
        frontRightWheelMotor = opMode.hardwareMap.get(DcMotor.class, "FRW");
        frontRightWheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        backRightWheelMotor = opMode.hardwareMap.get(DcMotor.class, "BRW");
        backRightWheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        backLeftWheelMotor = opMode.hardwareMap.get(DcMotor.class, "BLW");
        backLeftWheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftWheelMotor = opMode.hardwareMap.get(DcMotor.class, "FLW");
        frontLeftWheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        driveTrainMotors = new DcMotor[]{
                frontRightWheelMotor,
                backRightWheelMotor,
                backLeftWheelMotor,
                frontLeftWheelMotor
        };

        for(DcMotor motor : driveTrainMotors){
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        Mecanum = new Mecanum(frontRightWheelMotor, frontLeftWheelMotor, backRightWheelMotor, backLeftWheelMotor, 1);
        opMode.telemetry.addLine().addData(">", "Drive train initialised");
    }
}