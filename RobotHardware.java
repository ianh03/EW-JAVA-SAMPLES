package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class RobotHardware {
    public DcMotor frontLeft = null;
    public DcMotor frontRight = null;
    public DcMotor backLeft = null;
    public DcMotor backRight = null;

    public DcMotorSimple stageOne = null;
    public DcMotorSimple stageTwo = null;

    public CRServo gripperTilter = null;
    public Servo gripper = null;

    public DcMotor leftEncoder = null;
    public DcMotor rightEncoder = null;
    public DcMotor centralEncoder = null;

    public BNO055IMU imu = null;

    HardwareMap hardwareMap = null;

    public RobotHardware(HardwareMap hwMap) {
        initialize(hwMap);
    }

    private void initialize(HardwareMap hwMap) {
        hardwareMap = hwMap;

        // Main Drivetrain Motors
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        frontRight = hardwareMap.get(DcMotor.class, "FR");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");

        // SPARK Mini Stage one and two motors
        stageOne = hardwareMap.get(DcMotorSimple.class, "stage1motor");
        stageTwo = hardwareMap.get(DcMotorSimple.class, "stage2motor");

        // Gripper Servos
        gripperTilter = hardwareMap.get(CRServo.class, "GripUD");
        gripper = hardwareMap.get(Servo.class, "Gripper");

        // Encoders occupy built-in motor encoder ports, so needs to be shadowed
        leftEncoder = frontLeft;
        rightEncoder = frontRight;
        centralEncoder = backLeft;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Adjusting the Forward (positive value) Direction to be uniform
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        // When the motors are not moving, they will brake
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Not using runToPosition for enhanced navigation
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        resetEncoders();
    }

    public void move(double targetMM, double targetHeading, double power) {
        double startingTicks = leftEncoder.getCurrentPosition();

        while (ticksToMM(leftEncoder.getCurrentPosition() - startingTicks) < targetMM) {
            Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            if (angles.firstAngle < targetHeading) {
                frontLeft.setPower(power - 0.05);
                frontRight.setPower(power + 0.05);
                backLeft.setPower(power - 0.05);
                backRight.setPower(power + 0.05);
            }
            else if (angles.firstAngle > targetHeading) {
                frontLeft.setPower(power + 0.05);
                frontRight.setPower(power - 0.05);
                backLeft.setPower(power + 0.05);
                backRight.setPower(power - 0.05);
            }
            else {
                frontLeft.setPower(power);
                frontRight.setPower(power);
                backLeft.setPower(power);
                backRight.setPower(power);
            }
        }
    }

    public double ticksToMM(double ticks) {
        double mmPerTick = Math.PI * 37.5 / 8192;
        return ticks * mmPerTick;
    }

    public void stopMotors() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void resetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
