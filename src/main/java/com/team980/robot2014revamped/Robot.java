package com.team980.robot2014revamped;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {

    private Joystick joystick;

    private ShiftDrive shiftDrive;

    private Compressor compressor;

    @Override
    public void robotInit() {
        joystick = new Joystick(Parameters.DRIVE_JOYSTICK_CHANNEL);

        shiftDrive = new ShiftDrive();

        compressor = new Compressor(Parameters.PCM_CAN_ID);
        compressor.setClosedLoopControl(true);
    }

    @Override
    public void teleopInit() {
        System.out.println("back into the fray v3");

    }

    @Override
    public void teleopPeriodic() {
        if (joystick.getRawButton(3)) { //3 manually enables high gear
            shiftDrive.setHighGear(true);
        } else if (joystick.getRawButton(4)) { //4 manually disables high gear
            shiftDrive.setHighGear(false);
        }

        shiftDrive.drive(joystick); //Drives robot

        System.out.println(compressor.enabled() + "");
        System.out.println(compressor.getPressureSwitchValue() + "");
        System.out.println(compressor.getCompressorCurrent() + "");
        System.out.println("");
    }

    @Override
    public void disabledInit() {

    }
}
