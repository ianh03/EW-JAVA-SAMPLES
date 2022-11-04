package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "RobotAutoTest", group = "Testing")
public class RoboAutoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RobotHardware robot = new RobotHardware(hardwareMap);

        waitForStart();

        robot.move(230, 0, 0.5);

        double startingHeading = robot.getHeading();
        while (robot.getHeading() - startingHeading < 63) {
            robot.tankLeft(0.5);
        }
        robot.stopMotors();

        robot.move(250, robot.getHeading(), 0.5);

        stop();
    }
}
