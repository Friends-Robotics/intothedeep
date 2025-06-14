package friends.hardwaremap.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class VerticalExtension {
    public final DcMotorEx RightViper;
    public final DcMotorEx LeftViper;

    boolean is_deposited;

    private final Servo Bucket;

    public VerticalExtension(DcMotorEx Right, DcMotorEx Left, Servo bucket) {
        RightViper = Right;
        LeftViper = Left;
        Bucket = bucket;

        RightViper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftViper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        RightViper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LeftViper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        RightViper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftViper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        RightViper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        LeftViper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//        RightViper.setTargetPosition(0);
//        LeftViper.setTargetPosition(0);

//        RightViper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        LeftViper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//        RightViper.setVelocity(2000);
//        LeftViper.setVelocity(2000);


        is_deposited = false;
        Bucket.setPosition(1);
    }

    public void Extend() {
//        RightViper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        LeftViper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        RightViper.setVelocity(200);
//        LeftViper.setVelocity(200);

        if(RightViper.getCurrentPosition() > 5900 || LeftViper.getCurrentPosition() > 5900) {
            RightViper.setPower(0);
            LeftViper.setPower(0);
            return;
        }

        RightViper.setPower(0.75);
        LeftViper.setPower(0.75);
    }

    public void Dextend() {
//        RightViper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        LeftViper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        RightViper.setVelocity(-200);
//        LeftViper.setVelocity(-200);

        RightViper.setPower(-0.75);
        LeftViper.setPower(-0.75);
    }

    public void Nextend() {
        RightViper.setPower(0);
        LeftViper.setPower(0);
    }

    // MAX 6000
    public void Textend(int pos) {
        RightViper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftViper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        RightViper.setVelocity(1200);
        LeftViper.setVelocity(1200);

        RightViper.setTargetPosition(pos);
        LeftViper.setTargetPosition(pos);
    }

    public void depositBucket() {
        Bucket.setPosition(0);
    }

    public void receiveBucket() {
        Bucket.setPosition(1);
    }

    public void toggleBucket() {
        if(is_deposited) {
            depositBucket();
        } else {
            receiveBucket();
        }
        is_deposited = !is_deposited;
    }
}