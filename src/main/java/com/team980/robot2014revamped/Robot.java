package com.team980.robot2014revamped;

import com.ctre.PigeonImu;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Robot extends IterativeRobot {

    private Joystick joystick;

    private ShiftDrive shiftDrive;

    private PigeonImu imu;

    @Override
    public void robotInit() {
        joystick = new Joystick(Parameters.DRIVE_JOYSTICK_CHANNEL);

        shiftDrive = new ShiftDrive();

        imu = new PigeonImu(Parameters.PIGEON_IMU_CAN_ID);
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
