package org.firstinspires.ftc.teamcode.robot.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;

import java.util.List;

@TeleOp(name = "DrivingTest", group = "RobotTests")
public class DrivingTest extends LinearOpMode {
    final static double L = 170; // Distance between Encoder 0 and 1
    final static double B = 150; // Distance from midpoint of L to Encoder 2
    final static double R = 37.5; // Wheel Radius in mm
    final static double N = 8192; // Ticks per Revolution
    final static double mmPerTick = Math.PI * R / N;

    @Override
    public void runOpMode() throws InterruptedException {
        RobotHardware robot = new RobotHardware(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            while (gamepad1.left_stick_y != 0) {
                robot.frontLeft.setPower(-gamepad1.left_stick_y);
                robot.frontRight.setPower(-gamepad1.left_stick_y);
                robot.backLeft.setPower(-gamepad1.left_stick_y);
                robot.backRight.setPower(-gamepad1.left_stick_y);
            }

            while (gamepad1.right_stick_x != 0) {
                robot.frontLeft.setPower(gamepad1.right_stick_x);
                robot.frontRight.setPower(-gamepad1.right_stick_x);
                robot.backLeft.setPower(gamepad1.right_stick_x);
                robot.backRight.setPower(-gamepad1.right_stick_x);
            }

            while (gamepad1.left_stick_x != 0) {
                robot.frontLeft.setPower(gamepad1.left_stick_x);
                robot.frontRight.setPower(-gamepad1.left_stick_x);
                robot.backLeft.setPower(-gamepad1.left_stick_x);
                robot.backRight.setPower(gamepad1.left_stick_x);
            }

            double deltaHeading = mmPerTick * (robot.rightEncoder.getCurrentPosition() - robot.leftEncoder.getCurrentPosition()) / L;
            double calculatedHeading = Math.toDegrees(deltaHeading);

            telemetry.addData("IMU Heading", robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
            telemetry.addData("Calculated Heading", calculatedHeading);
            telemetry.addLine("\n");
            telemetry.addData("Left Encoder", robot.leftEncoder.getCurrentPosition());
            telemetry.addData("Right Encoder", robot.rightEncoder.getCurrentPosition());
            telemetry.addData("Central Encoder", robot.centralEncoder.getCurrentPosition());
            telemetry.addLine("\n");
            telemetry.addData("Left MM", robot.ticksToMM(robot.leftEncoder.getCurrentPosition()) + " mm");
            telemetry.addData("Right MM", robot.ticksToMM(robot.rightEncoder.getCurrentPosition()) + " mm");
            telemetry.addData("Central MM", robot.ticksToMM(robot.centralEncoder.getCurrentPosition()) + " mm");

            telemetry.update();

            robot.stopMotors();
        }
    }
}
