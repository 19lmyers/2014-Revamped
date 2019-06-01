/*
 *  MIT License
 *
 *  Copyright (c) 2018-2019 FRC Team 980 ThunderBots
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

import edu.wpi.first.wpilibj.DoubleSolenoid;

class Parameters {

    // --- DRIVE SYSTEM ---
    static final int LEFT_DRIVE_PWM_CHANNEL = 0;
    static final int RIGHT_DRIVE_PWM_CHANNEL = 1;

    //static final double UPPER_SHIFT_THRESHOLD = -1;
    //static final double LOWER_SHIFT_THRESHOLD = -1;

    // --- ENCODERS ---
    /*static final int LEFT_DRIVE_ENCODER_DIO_CHANNEL_A = -1;
    static final int LEFT_DRIVE_ENCODER_DIO_CHANNEL_B = -1;
    static final boolean INVERT_LEFT_DRIVE_ENCODER = false;

    static final int RIGHT_DRIVE_ENCODER_DIO_CHANNEL_A = -1;
    static final int RIGHT_DRIVE_ENCODER_DIO_CHANNEL_B = -1;
    static final boolean INVERT_RIGHT_DRIVE_ENCODER = false;*/

    // --- ROLLER SYSTEM ---
    static final int LEFT_ROLLER_MOTOR_PWM_CHANNEL = 4;
    static final int RIGHT_ROLLER_MOTOR_PWM_CHANNEL = 3;

    static final double ROLLER_MOTOR_INTAKE_SPEED = 0.5;
    static final double ROLLER_MOTOR_OUTPUT_SPEED = -0.5;

    // --- CATAPULT SYSTEM ---
    static final int WINCH_MOTOR_PWM_CHANNEL = 2;

    static final int LEFT_LOCK_SWITCH_DIO_CHANNEL = 0;
    static final int RIGHT_LOCK_SWITCH_DIO_CHANNEL = 1;

    static final double WINCH_MOTOR_SPEED = -0.6;
    //static final double WINCH_LOCK_DELAY = 1.0; //in seconds

    // --- PNEUMATICS ---
    static final int PCM_CAN_ID = 1;

    static final int SHIFT_SOLENOID_CHANNEL_A = 0;
    static final int SHIFT_SOLENOID_CHANNEL_B = 1;

    static final DoubleSolenoid.Value LOW_GEAR_SOLENOID_VALUE = DoubleSolenoid.Value.kForward;
    static final DoubleSolenoid.Value HIGH_GEAR_SOLENOID_VALUE = DoubleSolenoid.Value.kReverse;

    static final int ROLLER_SOLENOID_CHANNEL_A = 3;
    static final int ROLLER_SOLENOID_CHANNEL_B = 4;

    static final DoubleSolenoid.Value ROLLER_RETRACTED_SOLENOID_VALUE = DoubleSolenoid.Value.kReverse;
    static final DoubleSolenoid.Value ROLLER_EXTENDED_SOLENOID_VALUE = DoubleSolenoid.Value.kForward;

    static final int RATCHET_SOLENOID_CHANNEL = 2;

    static final boolean RATCHET_UNLOCKED_VALUE = true;
    static final boolean RATCHET_LOCKED_VALUE = false;

    // --- JOYSTICKS / DRIVER INPUTS ---
    static final int XBOX_CONTROLLER_JS_ID = 2;

    static final double TRIGGER_THRESHOLD = 0.9;
}
