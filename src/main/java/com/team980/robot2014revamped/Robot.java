package com.team980.robot2014revamped;

import com.ctre.PigeonImu;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

    private Joystick joystick;

    private ShiftDrive shiftDrive;

    private PigeonImu imu;

    private AutonomousState autoState;

    private SendableChooser<AutoProgram> autoChooser;

    @Override
    public void robotInit() {
        joystick = new Joystick(Parameters.DRIVE_JOYSTICK_CHANNEL);

        shiftDrive = new ShiftDrive();

        imu = new PigeonImu(Parameters.PIGEON_IMU_CAN_ID);

        autoChooser = new SendableChooser<AutoProgram>();
        autoChooser.addDefault("None", AutoProgram.NONE);
        autoChooser.addObject("Turning Test", AutoProgram.TURN_TEST);
        SmartDashboard.putData("Select Autonomous Mode:", autoChooser);
    }

    @Override
    public void autonomousInit() {
        if (autoChooser.getSelected() == AutoProgram.NONE) {
            return;
        } else if (autoChooser.getSelected() == AutoProgram.TURN_TEST) {
            autoState = AutonomousState.TURN_90_RIGHT;
            imu.SetYaw(0); //reset yaw
        }
    }

    @Override
    public void autonomousPeriodic() {
        if (autoChooser.getSelected() == AutoProgram.NONE) {
            return;
        } else if (autoChooser.getSelected() == AutoProgram.TURN_TEST) {

            double[] ypr = new double[3];
            imu.GetYawPitchRoll(ypr);

            switch (autoState) {
                case TURN_90_RIGHT:
                    if (ypr[0] >= 90) {
                        autoState = AutonomousState.TURN_180_LEFT;
                        imu.SetYaw(0); //reset yaw
                    } else {
                        shiftDrive.drive(.3, -.3);
                    }
                    break;
                case TURN_180_LEFT:
                    if (ypr[0] <= -180) {
                        autoState = AutonomousState.TURN_270_RIGHT;
                        imu.SetYaw(0); //reset yaw
                    } else {
                        shiftDrive.drive(-.3, .3);
                    }
                    break;
                case TURN_270_RIGHT:
                    if (ypr[0] >= 270) {
                        autoState = AutonomousState.TURN_720_LEFT;
                        imu.SetYaw(0); //reset yaw
                    } else {
                        shiftDrive.drive(.3, -.3);
                    }
                    break;
                case TURN_720_LEFT:
                    if (ypr[0] <= -720) {
                        autoState = AutonomousState.FINISHED;
                        imu.SetYaw(0); //reset yaw
                    } else {
                        shiftDrive.drive(-.3, .3);
                    }
                    break;
            }

            NetworkTable.getTable("PigeonIMU").putNumber("Yaw", ypr[0]);
            NetworkTable.getTable("PigeonIMU").putNumber("Pitch", ypr[1]);
            NetworkTable.getTable("PigeonIMU").putNumber("Roll", ypr[2]);
        }
    }

    @Override
    public void teleopInit() {
        System.out.println("back into the fray");
    }

    @Override
    public void teleopPeriodic() {
        if (joystick.getRawButton(3)) { //3 manually enables high gear
            shiftDrive.setHighGear(true);
        } else if (joystick.getRawButton(4)) { //4 manually disables high gear
            shiftDrive.setHighGear(false);
        }

        shiftDrive.drive(joystick); //Drives robot

        double[] ypr = new double[3];
        imu.GetYawPitchRoll(ypr);

        NetworkTable.getTable("PigeonIMU").putNumber("Yaw", ypr[0]);
        NetworkTable.getTable("PigeonIMU").putNumber("Pitch", ypr[1]);
        NetworkTable.getTable("PigeonIMU").putNumber("Roll", ypr[2]);

    }

    @Override
    public void disabledInit() {

    }
}
