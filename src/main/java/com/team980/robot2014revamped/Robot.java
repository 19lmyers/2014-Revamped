package com.team980.robot2014revamped;

import com.ctre.PigeonImu;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.*;

public class Robot extends IterativeRobot {

    private Joystick driveStick;
    private Joystick driveWheel;

    private ShiftDrive shiftDrive;
    private BallSystem ballSystem;

    private PigeonImu imu;

    private boolean isAutoFinished;

    private SendableChooser<AutoProgram> autoChooser;
    private SendableChooser<Boolean> highGearAuto;

    private PrintWriter writer;

    @Override
    public void robotInit() {
        driveStick = new Joystick(Parameters.DRIVE_JOYSTICK_CHANNEL);
        driveWheel = new Joystick(Parameters.DRIVE_WHEEL_CHANNEL);

        shiftDrive = new ShiftDrive();
        ballSystem = new BallSystem();

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

        //ballSystem.init(); TODO

        try {
            writer = new PrintWriter("teleop.txt", "UTF-8");
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }

        writer.println(System.currentTimeMillis() + ": ENABLING ROBOT");
    }

    @Override
    public void teleopPeriodic() {
        if (driveStick.getRawButton(3)) { //3 manually enables high gear
            shiftDrive.setHighGear(true);
        } else if (driveStick.getRawButton(4)) { //4 manually disables high gear
            shiftDrive.setHighGear(false);
        }

        shiftDrive.drive(driveStick, driveWheel); //Drives robot

        //ball system controls
        //TODO https://github.com/Team980/2014/blob/master/MyRobot.cpp
        //TODO https://github.com/Team980/2014/blob/master/BallSystem.cpp

        double[] ypr = new double[3];
        imu.GetYawPitchRoll(ypr);

        NetworkTable.getTable("PigeonIMU").putNumber("Yaw", ypr[0]);
        NetworkTable.getTable("PigeonIMU").putNumber("Pitch", ypr[1]);
        NetworkTable.getTable("PigeonIMU").putNumber("Roll", ypr[2]);


        writer.print(System.currentTimeMillis() + ": ");
        writer.print("yaw: " + ypr[0] + ", ");
        writer.print("pitch: " + ypr[1] + ", ");
        writer.println("roll: " + ypr[2]);
    }

    @Override
    public void disabledInit() {
        writer.println(System.currentTimeMillis() + ": DISABLING ROBOT");
        writer.close();
    }
}
