package com.team980.robot2014revamped;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {

    private Joystick joystick;

    private ShiftDrive shiftDrive;
    @Override
    public void robotInit() {
        joystick = new Joystick(Parameters.DRIVE_JOYSTICK_CHANNEL);

        shiftDrive = new ShiftDrive();
    }

    @Override
    public void teleopInit() {
        System.out.println("back into the fray v4");

    }

    @Override
    public void teleopPeriodic() {
        if (joystick.getRawButton(3)) { //3 manually enables high gear
            shiftDrive.setHighGear(true);
        } else if (joystick.getRawButton(4)) { //4 manually disables high gear
            shiftDrive.setHighGear(false);
        }

        shiftDrive.drive(joystick); //Drives robot
    }

    @Override
    public void disabledInit() {

    }
}
