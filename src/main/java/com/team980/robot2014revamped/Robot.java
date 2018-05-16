package com.team980.robot2014revamped;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import static com.team980.robot2014revamped.Parameters.*;

public class Robot extends IterativeRobot {

    private Joystick xboxController; //TODO use the custom class features

    private DifferentialDrive robotDrive;

    //TODO shifters

    private BallSystem ballSystem;

    @Override
    public void robotInit() {
        xboxController = new Joystick(XBOX_CONTROLLER_CHANNEL);

        Talon leftDrive = new Talon(LEFT_DRIVE_PWM_CHANNEL);
        Talon rightDrive = new Talon(RIGHT_DRIVE_PWM_CHANNEL);

        robotDrive = new DifferentialDrive(leftDrive, rightDrive);

        //TODO shifters

        ballSystem = new BallSystem();
    }

    @Override
    public void teleopInit() {
        //ballSystem.init(); TODO
    }

    @Override
    public void teleopPeriodic() {
        //ball system controls
        //TODO https://github.com/Team980/2014/blob/master/MyRobot.cpp
        //TODO https://github.com/Team980/2014/blob/master/BallSystem.cpp

        robotDrive.arcadeDrive(xboxController.getRawAxis(CONTROLLER_DRIVE_AXIS), xboxController.getRawAxis(CONTROLLER_TURN_AXIS));
    }

    @Override
    public void disabledInit() {
        robotDrive.stopMotor();
    }
}
