package com.team980.robot2014revamped;

public class Parameters {

    // --- DRIVE SYSTEM ---
    public static final int LEFT_DRIVE_PWM_CHANNEL = 1;
    public static final int RIGHT_DRIVE_PWM_CHANNEL = 0;

    public static final int SHIFT_SOLENOID_CHANNEL_A = 4; //reverse
    public static final int SHIFT_SOLENOID_CHANNEL_B = 5; //forward

    // --- PNEUMATICS ---
    public static final int PCM_CAN_ID = 1;

    // --- JOYSTICKS / DRIVER INPUTS ---
    public static final int XBOX_CONTROLLER_CHANNEL = 2;

    public static final int CONTROLLER_DRIVE_AXIS = 1; //Left stick - Y
    public static final int CONTROLLER_TURN_AXIS = 4; //Right stick - X
}
