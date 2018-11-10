/*
 *  MIT License
 *
 *  Copyright (c) 2018 FRC Team 980 ThunderBots
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.team980.robot2014revamped;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import static com.team980.robot2014revamped.Parameters.*;

public class Robot extends IterativeRobot {

    private NetworkTable table;

    private XboxController xboxController; //TODO use the custom class features

    private DifferentialDrive robotDrive;

    /*private Encoder leftDriveEncoder;
    private Encoder rightDriveEncoder;

    private DoubleSolenoid shifterSolenoid;
    private Gear currentGear;*/

    private VictorSP leftRollerMotor;
    private VictorSP rightRollerMotor;

    private DoubleSolenoid rollerSolenoid;
    private RollerState currentRollerState = RollerState.EXTENDED;

    private CatapultSystem catapultSystem;

    @Override
    public void robotInit() {
        table = NetworkTableInstance.getDefault().getTable("2014-Revamped"); //Robot code name!

        xboxController = new XboxController(XBOX_CONTROLLER_JS_ID);

        Talon leftDrive = new Talon(LEFT_DRIVE_PWM_CHANNEL);
        Talon rightDrive = new Talon(RIGHT_DRIVE_PWM_CHANNEL);

        robotDrive = new DifferentialDrive(leftDrive, rightDrive);
        robotDrive.setName("Robot Drive");

        /*leftDriveEncoder = new Encoder(Parameters.LEFT_DRIVE_ENCODER_DIO_CHANNEL_A, Parameters.LEFT_DRIVE_ENCODER_DIO_CHANNEL_B, Parameters.INVERT_LEFT_DRIVE_ENCODER, CounterBase.EncodingType.k4X);
        leftDriveEncoder.setDistancePerPulse((2 * (Constants.PI) * (Constants.DRIVE_WHEEL_RADIUS / 12)) / (Constants.DRIVE_ENCODER_PULSES_PER_REVOLUTION * Constants.DRIVE_SYSTEM_GEAR_RATIO));
        leftDriveEncoder.setName("Drive Encoders", "Left");

        rightDriveEncoder = new Encoder(Parameters.RIGHT_DRIVE_ENCODER_DIO_CHANNEL_A, Parameters.RIGHT_DRIVE_ENCODER_DIO_CHANNEL_B, Parameters.INVERT_RIGHT_DRIVE_ENCODER, CounterBase.EncodingType.k4X);
        rightDriveEncoder.setDistancePerPulse((2 * (Constants.PI) * (Constants.DRIVE_WHEEL_RADIUS / 12)) / (Constants.DRIVE_ENCODER_PULSES_PER_REVOLUTION * Constants.DRIVE_SYSTEM_GEAR_RATIO));
        rightDriveEncoder.setName("Drive Encoders", "Right");

        shifterSolenoid = new DoubleSolenoid(PCM_CAN_ID, SHIFT_SOLENOID_CHANNEL_A, SHIFT_SOLENOID_CHANNEL_B);*/

        leftRollerMotor = new VictorSP(LEFT_ROLLER_MOTOR_PWM_CHANNEL);
        leftRollerMotor.setName("Roller System", "Left Motor");
        rightRollerMotor = new VictorSP(RIGHT_ROLLER_MOTOR_PWM_CHANNEL);
        rightRollerMotor.setInverted(true);
        rightRollerMotor.setName("Roller System", "Right Motor");

        rollerSolenoid = new DoubleSolenoid(PCM_CAN_ID, ROLLER_SOLENOID_CHANNEL_A, ROLLER_SOLENOID_CHANNEL_B);
        rollerSolenoid.setName("Roller System", "Solenoid");

        catapultSystem = new CatapultSystem();
    }

    @Override
    public void robotPeriodic() {
        /*table.getSubTable("Status Flags").getEntry("Gear").setString(currentGear.name());*/
        table.getSubTable("Status Flags").getEntry("Roller State").setString(currentRollerState.name());
        table.getSubTable("Status Flags").getEntry("Catapult State").setString(catapultSystem.getState().name());
    }

    @Override
    public void teleopInit() {
        //setGear(Gear.LOW_GEAR);
        setRollerState(RollerState.EXTENDED);
        catapultSystem.init();
    }

    @Override
    public void teleopPeriodic() {
        // AUTOMATIC SHIFTING
        /*if (Math.abs(leftDriveEncoder.getRate()) > Parameters.UPPER_SHIFT_THRESHOLD
                && Math.abs(rightDriveEncoder.getRate()) > Parameters.UPPER_SHIFT_THRESHOLD && currentGear == Gear.LOW_GEAR) {
            setGear(Gear.HIGH_GEAR);
        } else if (Math.abs(leftDriveEncoder.getRate()) < Parameters.LOWER_SHIFT_THRESHOLD
                && Math.abs(rightDriveEncoder.getRate()) < Parameters.LOWER_SHIFT_THRESHOLD && currentGear == Gear.HIGH_GEAR) {
            setGear(Gear.LOW_GEAR);
        }*/

        if (xboxController.getBumper(GenericHID.Hand.kLeft)) {
            setRollerState(RollerState.RETRACTED);
        } else if (xboxController.getBumper(GenericHID.Hand.kRight)) {
            setRollerState(RollerState.EXTENDED);
        }

        if (xboxController.getAButton()) {
            leftRollerMotor.set(ROLLER_MOTOR_INTAKE_SPEED);
            rightRollerMotor.set(-ROLLER_MOTOR_INTAKE_SPEED);
        } else if (xboxController.getXButton()) {
            leftRollerMotor.set(ROLLER_MOTOR_OUTPUT_SPEED);
            rightRollerMotor.set(-ROLLER_MOTOR_OUTPUT_SPEED);
        } else if (xboxController.getBButton()) {
            leftRollerMotor.set(0);
            rightRollerMotor.set(0);
        }

        // INTAKE EXTENSION

        robotDrive.arcadeDrive(xboxController.getY(GenericHID.Hand.kLeft), -xboxController.getX(GenericHID.Hand.kRight));

        catapultSystem.operate(xboxController, currentRollerState);
    }

    @Override
    public void disabledInit() {
        robotDrive.stopMotor();

        leftRollerMotor.disable();
        rightRollerMotor.disable();

        catapultSystem.disable();
    }

    /*private void setGear(Gear gear) {
        currentGear = gear;
        shifterSolenoid.set(gear.getSolenoidValue());
    }*/

    private void setRollerState(RollerState rollerState) {
        currentRollerState = rollerState;
        rollerSolenoid.set(rollerState.getSolenoidValue());
    }

    /*private enum Gear {
        LOW_GEAR(LOW_GEAR_SOLENOID_VALUE),
        HIGH_GEAR(HIGH_GEAR_SOLENOID_VALUE);

        private DoubleSolenoid.Value solenoidValue;

        Gear(DoubleSolenoid.Value value) {
            solenoidValue = value;
        }

        DoubleSolenoid.Value getSolenoidValue() {
            return solenoidValue;
        }
    }*/

    public enum RollerState {
        RETRACTED(ROLLER_RETRACTED_SOLENOID_VALUE),
        EXTENDED(ROLLER_EXTENDED_SOLENOID_VALUE);

        private DoubleSolenoid.Value solenoidValue;

        RollerState(DoubleSolenoid.Value value) {
            solenoidValue = value;
        }

        DoubleSolenoid.Value getSolenoidValue() {
            return solenoidValue;
        }
    }
}
