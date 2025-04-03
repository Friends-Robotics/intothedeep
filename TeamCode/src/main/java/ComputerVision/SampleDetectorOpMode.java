package ComputerVision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.*;

@Autonomous(name="Sample Detector", group="Autonomous")
public class SampleDetectorOpMode extends LinearOpMode {
    private OpenCvCamera webcam;

    @Override
    public void runOpMode() {
        // Initialize OpenCV Camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        SampleDetectionPipeline pipeline = new SampleDetectionPipeline();
        webcam.setPipeline(pipeline);

        // Open camera asynchronously
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error:", errorCode);
            }
        });

        telemetry.addLine("Waiting for start...");
        telemetry.update();
        waitForStart();

//        while (opModeIsActive()) {
//            telemetry.addData("Detected:", pipeline.isGoldDetected());
//            telemetry.addData("Gold X:", pipeline.getGoldX());
//            telemetry.addData("Gold Y:", pipeline.getGoldY());
//            telemetry.addData("Angle:", pipeline.getGoldAngle());
//            telemetry.update();
//        }

        webcam.stopStreaming();
    }
}
