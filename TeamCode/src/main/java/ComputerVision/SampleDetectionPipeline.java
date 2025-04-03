package ComputerVision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class SampleDetectionPipeline extends OpenCvPipeline {
    private final Object syncObject = new Object(); // Synchronization object

    private boolean sampleDetected = false;
    private double sampleX = -1;
    private double sampleY = -1;
    private double sampleAngle = 0;

    private Mat hsv = new Mat();
    private Mat mask = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        // Define RED color range (using both ranges for red)
        Scalar lowerRed1 = new Scalar(0, 100, 100);
        Scalar upperRed1 = new Scalar(10, 255, 255);
        Scalar lowerRed2 = new Scalar(170, 100, 100);
        Scalar upperRed2 = new Scalar(180, 255, 255);

        // Threshold for red sample
        Mat mask1 = new Mat();
        Mat mask2 = new Mat();
        Core.inRange(hsv, lowerRed1, upperRed1, mask1);
        Core.inRange(hsv, lowerRed2, upperRed2, mask2);
        Core.bitwise_or(mask1, mask2, mask); // Combine both red masks

        // Find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        synchronized (syncObject) { // Protect shared variables
            sampleDetected = false;

            for (MatOfPoint contour : contours) {
                Rect boundingBox = Imgproc.boundingRect(contour);
                if (boundingBox.area() > 500) { // Ignore small objects
                    sampleDetected = true;
                    sampleX = boundingBox.x + (boundingBox.width / 2.0);
                    sampleY = boundingBox.y + (boundingBox.height / 2.0);

                    RotatedRect rotatedRect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));
                    sampleAngle = rotatedRect.angle;

                    Imgproc.rectangle(input, boundingBox.tl(), boundingBox.br(), new Scalar(255, 0, 0), 2);
                    Imgproc.circle(input, new Point(sampleX, sampleY), 5, new Scalar(0, 255, 0), -1);
                }
            }
        }
        return input;
    }

    // Synchronize access to these methods to avoid thread issues
    public synchronized boolean isSampleDetected() {
        return sampleDetected;
    }

    public synchronized double getSampleX() {
        return sampleX;
    }

    public synchronized double getSampleY() {
        return sampleY;
    }

    public synchronized double getSampleAngle() {
        return sampleAngle;
    }
}
