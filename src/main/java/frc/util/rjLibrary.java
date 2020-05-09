package frc.util;

public class rjLibrary {

    public static double limitValue(double val, double max) {
        if (val > max) {
            return max;
        } else if (val < -max) {
            return -max;
        } else {
            return val;
        }
    }

    public static double limitValue(double val, double max, double min) {
        if (val > max) {
            return max;
        } else if (val < min) {
            return min;
        } else {
            return val;
        }
    }

    public static double limitValue(double val){
        return rjLibrary.limitValue(val, 1.0);
    } 

    public static double calcLeftTankDrive(double throttle, double steer) {
        return throttle + steer;
    }

    public static double calcRightTankDrive(double throttle, double steer) {
        return throttle - steer;
    }
}