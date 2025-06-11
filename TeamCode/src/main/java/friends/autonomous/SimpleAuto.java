//package friends.autonomous;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.robotcontroller.external.samples.RobotHardware;
//
//@Autonomous(name = "Simple Auto", group = "Y13CompCode/FSL")
//public class SimpleAuto extends LinearOpMode {
//    RobotHardware robot = new RobotHardware(this);
//    @Override
//    public void runOpMode(){
//        robot.init(true);
//        waitForStart();
//
//        robot.SetViperSlidePos(RobotHardware.TopRungEncoders);
//        sleep(500);
//        robot.DriveByEncoderTicks(550, 550, 550, 550, 0.2);
//        HangingSequence();
//        robot.SetViperSlidePos(RobotHardware.BottomEncoders);
//
//        Ending();
//    }
//    void HangingSequence(){
//        robot.SetViperSlidePos(RobotHardware.TopRungEncoders - 800);
//        sleep(650);
//        robot.SetClawPos(false);
//    }
//
//    void Ending(){
//        robot.DriveByEncoderTicks(1131, -1725, 1139, -1671, 0.9);
//    }
//
//}
