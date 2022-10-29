package org.firstinspires.ftc.teamcode.IanOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class EvanManualControl extends LinearOpMode {

    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;
    DcMotorSimple Stage2;
    DcMotorSimple Stage1;
    CRServo GripUD;
    Servo Gripper;

    @Override
    public void runOpMode() throws InterruptedException {

        FL = hardwareMap.get(DcMotor.class, "FL");
        FR = hardwareMap.get(DcMotor.class, "FR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        Stage2 = hardwareMap.get(DcMotorSimple.class, "stage2motor");
        Stage1 = hardwareMap.get(DcMotorSimple.class, "stage1motor");
        GripUD = hardwareMap.get(CRServo.class, "GripUD");
        Gripper = hardwareMap.get(Servo.class, "Gripper");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.left_stick_y != 0 || gamepad1.left_stick_x != 0 || gamepad1.right_stick_y != 0 || gamepad1.right_stick_x != 0) {
                // Movement "Methods"
                while (gamepad1.left_stick_y > 0) { // FWD
                    FL.setPower(gamepad1.left_stick_y);
                    FR.setPower(gamepad1.left_stick_y);
                    BL.setPower(gamepad1.left_stick_y);
                    BR.setPower(gamepad1.left_stick_y);
                }

                while (gamepad1.left_stick_y < 0) { // BWD
                    FL.setPower(gamepad1.left_stick_y);
                    FR.setPower(gamepad1.left_stick_y);
                    BL.setPower(gamepad1.left_stick_y);
                    BR.setPower(gamepad1.left_stick_y);
                }

                while (gamepad1.left_stick_x > 0) { // Right
                    FL.setPower(-gamepad1.left_stick_x);
                    FR.setPower(gamepad1.left_stick_x);
                    BL.setPower(gamepad1.left_stick_x);
                    BR.setPower(-gamepad1.left_stick_x);
                }

                while (gamepad1.left_stick_x < 0) { // Left
                    FL.setPower(-gamepad1.left_stick_x);
                    FR.setPower(gamepad1.left_stick_x);
                    BL.setPower(gamepad1.left_stick_x);
                    BR.setPower(-gamepad1.left_stick_x);
                }
                //Tank Turn "Methods"
                while (gamepad1.right_stick_x < 0) { // TNK Left
                    FL.setPower(-gamepad1.right_stick_x);
                    FR.setPower(gamepad1.right_stick_x);
                    BL.setPower(-gamepad1.right_stick_x);
                    BR.setPower(gamepad1.right_stick_x);
                }

                while (gamepad1.right_stick_x > 0) { // TNK Right
                    FL.setPower(-gamepad1.right_stick_x);
                    FR.setPower(gamepad1.right_stick_x);
                    BL.setPower(-gamepad1.right_stick_x);
                    BR.setPower(gamepad1.right_stick_x);
                }
            }
            else { //Stop
                FL.setPower(0);
                FR.setPower(0);
                BL.setPower(0);
                BR.setPower(0);
            }


                // Stage 1 Individual Control
                while (gamepad1.right_bumper) {
                    Stage1.setPower(-0.5);
                }
                if (!gamepad1.right_bumper) {
                    Stage1.setPower(0);
                }

                while (gamepad1.left_bumper) {
                    Stage1.setPower(0.5);
                }
                if (!gamepad1.left_bumper) {
                    Stage1.setPower(0);
                }


                //Stage 2 Individual Control
                while (gamepad1.right_trigger > 0) {
                    Stage2.setPower(-0.5);
                }
                if (gamepad1.right_trigger == 0) {
                    Stage2.setPower(0);
                }

                while (gamepad1.left_trigger > 0) {
                    Stage2.setPower(0.5);
                }
                if (gamepad1.left_trigger == 0) {
                    Stage2.setPower(0);
                }


                //Gripper Up/Down
            while (gamepad1.dpad_up) {
                GripUD.setPower(0.25);
            }
            if (!gamepad1.dpad_up) {
                GripUD.setPower(0);
            }

            while (gamepad1.dpad_down) {
                GripUD.setPower(-0.25);
            }
            if (!gamepad1.dpad_down) {
                GripUD.setPower(0);
            }

                //Gripper Open/Close
            if (gamepad1.triangle) {
                Gripper.setPosition(0);
            }

            if (gamepad1.cross) {
                Gripper.setPosition(1);
            }
            /*Left.setPower(-gamepad1.left_stick_y);
            Right.setPower(gamepad1.right_stick_y);
            Stage1.setPower(gamepad1.right_trigger);
            Stage1.setPower(-gamepad1.left_trigger);
            if (gamepad1.right_stick_button) {
                Stage2.setPower(1);
            }
            else if (!gamepad1.right_stick_button) {
                Stage2.setPower(-1);
        }*/
            }
        }
    }
