package org.firstinspires.ftc.teamcode.robot.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@TeleOp
public class ObjectDetection extends LinearOpMode {
    static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";

    static final String[] LABELS = {
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };

    static final String VUFORIA_KEY = "AYHwks3/////AAABmQLnZ1la9Eu2j9yxlXfAbW1kDwAMhGLzXfy9TqWAxJmdNYOC4J4O9cZFoeylt2YPe4kymLxmrwBEzp3YHtkivntsLqzhU/IeJVf/Cmv07iGhui6/vtjPYjKik9JlUpk/cyIrGBzYgO37eUM/j5Lv+olhTdAIol6mzZZOk2mJjN+ljMZt4Vk1mzqw+Or8Zz3ly/93mfZeu6dOm/UsPgg9YGqXbt72JMz20ZDqFQ8T7TnXmAIm2SBoO1EpKcTSZthtX1tYbVfTBDsYNG5Xb2IkEEn/7AbwLpnnRWVYo48+LJh64jkFmqaTYOHdkXRorry2OeqXDKK51obsoqPMr52IkXkPS6Roc43bAlD+xvaVRyAS";

    VuforiaLocalizer vuforia;
    TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.0, 16.0/9.0);
        }

        waitForStart();

        while (opModeIsActive()) {
            if (tfod != null) {
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals("1 Bolt")) {
                            telemetry.addData("Object Detected", "Bolt");
                        }
                        else if (recognition.getLabel().equals("2 Bulb")) {
                            telemetry.addData("Object Detected", "Bulb");
                        }
                        else if (recognition.getLabel().equals("3 Panel")) {
                            telemetry.addData("Object Detected", "Panel");
                        }
                    }
                    telemetry.update();
                }
            }
        }
    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "21301-Cam");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);

        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
}
