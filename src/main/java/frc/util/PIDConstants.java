package frc.util;

public class PIDConstants {
    public final double p;
    public final double i;
    public final double d;
    public final double eps; //acceptable range
    public final double ff; //feed forward

    public PIDConstants(double kP, double kI, double kD, double kEps) {
        this(kP,kI,kD,0,kEps);
    }
    public PIDConstants(double kP, double kI, double kD, double kFF, double kEps) {
        this.p = kP;
        this.i = kI;
        this.d = kD;
        this.ff = kFF;
        this.eps = kEps;
    }
} 