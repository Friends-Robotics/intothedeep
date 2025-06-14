package friends.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import friends.hardwaremap.RobotHardware;

@Autonomous(name = "Greedy Auto", group = "Y13CompCode/FSL")
public class GreedyAuto extends LinearOpMode {

    RobotHardware robot = new RobotHardware(this);
    @Override
    public void runOpMode() {
        robot.init(true);
        waitForStart();

        robot.SetViperSlidePos(RobotHardware.TopRungEncoders);
        sleep(500);
        robot.DriveByEncoderTicks(550, 550, 550, 550, 0.2);
        HangingSequence();
        robot.SetViperSlidePos(RobotHardware.BottomEncoders);

        Rotate180();
        HangingToBullCharge();
        BullChargeToPickup();
        robot.SetClawPos(true);
        robot.SetViperSlidePos(600);
        sleep(500);
        PickToOutALittle();
        Rotate180();

        robot.SetViperSlidePos(RobotHardware.TopRungEncoders);
        OutALittleToHang();
        HangingSequence();
        sleep(3000);
        robot.SetViperSlidePos(0);
    }

    void HangingToBullCharge(){
        robot.DriveByEncoderTicks(-1200, 1200, -1200, 1200, 0.3);
    }

    void BullChargeToPickup(){
        robot.DriveByEncoderTicks(672, 675, 684, 687, 0.4);
    }

    void PickToOutALittle(){
        robot.DriveByEncoderTicks(-272, -284, -284, -266, 0.25);
    }

    void OutALittleToHang(){
        robot.DriveByEncoderTicks(-720, 1700, -655, 1587, 0.3);
    }

    void Rotate180(){
        robot.DriveByEncoderTicks(-1328, 1385, 1442, -1403, 0.25);
    }
    void HangingSequence(){
        robot.SetViperSlidePos(RobotHardware.TopRungEncoders - 9000);
        sleep(500);
        robot.SetClawPos(false);
    }
}
