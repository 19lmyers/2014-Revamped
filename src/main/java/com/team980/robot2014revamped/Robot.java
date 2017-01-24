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

    private boolean isAutoFinished;

    private SendableChooser<AutoProgram> autoChooser;
    private SendableChooser<Boolean> highGearAuto;

    @Override
    public void robotInit() {
        joystick = new Joystick(Parameters.DRIVE_JOYSTICK_CHANNEL);

        shiftDrive = new ShiftDrive();

        imu = new PigeonImu(Parameters.PIGEON_IMU_CAN_ID);

        autoChooser = new SendableChooser<>();
        for (AutoProgram program : AutoProgram.values()) {
            autoChooser.addObject(program.toString(), program);
        }
        SmartDashboard.putData("autoChooser", autoChooser);

        highGearAuto = new SendableChooser<>();
        highGearAuto.addDefault("Low Gear", false);
        highGearAuto.addObject("High Gear", true);
        SmartDashboard.putData("highGearAuto", highGearAuto);
    }

    @Override
    public void autonomousInit() {
        isAutoFinished = false;

        if (highGearAuto.getSelected()) { //should auto be in high gear?
            shiftDrive.setHighGear(true);
        } else {
            shiftDrive.setHighGear(false);
        }
    }

    @Override
    public void autonomousPeriodic() {
        if (autoChooser.getSelected() == AutoProgram.NONE) {
            return;
        } else if (!isAutoFinished) {

            double[] ypr = new double[3];
            imu.GetYawPitchRoll(ypr);

            if (autoChooser.getSelected().getThreshold() > 0) { //Is the threshold positive?
                //rotate in positive direction (+ -)
                if (ypr[0] >= autoChooser.getSelected().getThreshold()) {
                    isAutoFinished = true;
                } else {
                    shiftDrive.drive(.3, -.3);
                }
            } else {
                //rotate in negative direction (- +)
                if (ypr[0] <= autoChooser.getSelected().getThreshold()) {
                    isAutoFinished = false;
                } else {
                    shiftDrive.drive(-.3, .3);
                }
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
