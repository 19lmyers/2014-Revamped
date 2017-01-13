package com.team980.robot2014revamped;

import edu.wpi.first.wpilibj.*;

public class ShiftDrive {

    //private Encoder leftEncoder; //Drive encoders
    //private Encoder rightEncoder;

    private DoubleSolenoid shiftSolenoid; //Used to shift

    private RobotDrive robotDrive; //lazy drive system

    private boolean inHighGear; //status variables

    public ShiftDrive() {
        //Encoder directions should be reversed to produce the same signs
        //leftEncoder = new Encoder(Parameters.LEFT_DRIVE_ENCODER_CHANNEL_A, Parameters.LEFT_DRIVE_ENCODER_CHANNEL_B,
                //false, Encoder.EncodingType.k1X);
        //rightEncoder = new Encoder(Parameters.RIGHT_DRIVE_ENCODER_CHANNEL_A, Parameters.RIGHT_DRIVE_ENCODER_CHANNEL_B,
                //true, Encoder.EncodingType.k1X);

        //leftEncoder.setDistancePerPulse(Constants.DISTANCE_PER_PULSE);
        //rightEncoder.setDistancePerPulse(Constants.DISTANCE_PER_PULSE);

        shiftSolenoid = new DoubleSolenoid(Parameters.PCM_CAN_ID,
                Parameters.SHIFT_SOLENOID_CHANNEL_A,
                Parameters.SHIFT_SOLENOID_CHANNEL_B);

        robotDrive = new RobotDrive(Parameters.LEFT_DRIVE_MOTOR_CHANNEL, Parameters.RIGHT_DRIVE_MOTOR_CHANNEL);

        inHighGear = false;
    }

    public void drive(Joystick driveStick) {

        //IF NOT TURNING, then check for shifting velocities and shift
        /*if (leftEncoder.getDirection() == rightEncoder.getDirection()) { //Are we not turning (encoder directions match)?

            if (Math.abs((leftEncoder.getRate() + rightEncoder.getRate() / 2)) > 200
                    && !inHighGear) { //Are we above the high gear threshold and not in high gear?
                setHighGear(true);

            } else if (inHighGear) { //Are we below the threshold and in high gear?
                setHighGear(false);
            }
        }*/

        //DRIVE THE ROBOT
        robotDrive.arcadeDrive(driveStick, Joystick.AxisType.kY.value, driveStick, Joystick.AxisType.kZ.value);
    }

    public void setHighGear(boolean enable) { //TODO verify
        if (enable) {
            shiftSolenoid.set(DoubleSolenoid.Value.kForward);
            inHighGear = true;
        } else {
            shiftSolenoid.set(DoubleSolenoid.Value.kReverse);
            inHighGear = false;
        }
    }
}
