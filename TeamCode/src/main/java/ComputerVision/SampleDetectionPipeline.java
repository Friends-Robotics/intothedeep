package ComputerVision;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class SampleDetectionPipeline extends OpenCvPipeline {
    Mat hsv = new Mat();
    Mat mask = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        // Convert to HSV
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        // Define color ranges for gold (adjust these values)
        Scalar lowerGold = new Scalar(20, 100, 100); // Lower bound for gold
        Scalar upperGold = new Scalar(30, 255, 255); // Upper bound for gold

        // Threshold the image
        Core.inRange(hsv, lowerGold, upperGold, mask);

        // Find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Draw contours
        for (MatOfPoint contour : contours) {
            Rect boundingBox = Imgproc.boundingRect(contour);
            Imgproc.rectangle(input, boundingBox.tl(), boundingBox.br(), new Scalar(255, 0, 0), 2);
        }

        return input;  // Return the processed frame
    }
}

