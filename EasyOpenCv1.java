package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class SignalPipeline extends OpenCvPipeline {

    public enum ParkPosition {
        ONE,
        TWO,
        THREE
    }

    Telemetry telemetry;
    Mat mat = new Mat();
    Mat ycrcb = new Mat();
    Mat output = new Mat();

    static final Rect MIDDLE = new Rect(
            new Point(125, 35),
            new Point(160, 95));

    public SignalPipeline(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    volatile ParkPosition position = ParkPosition.TWO;

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, ycrcb, Imgproc.COLOR_RGB2YCrCb);

        Scalar rectColor = new Scalar(255.0, 0.0, 0.0);

        input.copyTo(output);
        Imgproc.rectangle(output, MIDDLE, rectColor, 2);

        Core.extractChannel(ycrcb, mat, 0); // Channel 0 Extraction
        Core.inRange(mat, new Scalar(160), new Scalar(190), mat);
        double countY = Core.mean(mat.submat(MIDDLE)).val[0];
        telemetry.addData("CountY", countY);

        Core.extractChannel(ycrcb, mat, 1); // Channel 1 Extraction
        Core.inRange(mat, new Scalar(130), new Scalar(200), mat);
        double countCr = Core.mean(mat.submat(MIDDLE)).val[0];
        telemetry.addData("countCr", countCr);

        Core.extractChannel(ycrcb, mat, 2); // Chanel 2 Extraction
        Core.inRange(mat, new Scalar(130), new Scalar(170), mat);
        double countCb = Core.mean(mat.submat(MIDDLE)).val[0];
        telemetry.addData("countCb", countCb);

        telemetry.update();

        return input;
    }
}
