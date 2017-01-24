package com.team980.robot2014revamped;

public enum AutoProgram {

    NONE(0),
    TURN_90_RIGHT(90),
    TURN_270_LEFT(-270);

    private double threshold;

    AutoProgram(double par1) {
        threshold = par1;
    }

    public double getThreshold() {
        return threshold;
    }
}

