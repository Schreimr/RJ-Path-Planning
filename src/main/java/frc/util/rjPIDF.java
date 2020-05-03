package frc.util;

import frc.util.rjPID;

public class rjPIDF extends rjPID {

    private double feedForward;

    public  rjPIDF(double p, double i, double d, double f, double eps) {
        super(p,i,d,eps);
        this.feedForward = f;
    }
    @Override
    public double calcPID(double current) {
        double feedForwardOutput = (super.getDesiredValue() * this.feedForward);
        return super.calcPID(current) + feedForwardOutput;
    }

    @Override
    public void setConstants(double p, double i, double d, double f){
        super.setConstants(p, i, d);
        this.feedForward = f;
    }

    @Override
    public boolean isDone() {
        double currentError = Math.abs(this.prevError);
        return currentError <= this.finishedRange;
    }

    public void setFeedForward(double f) {
        this.feedForward = f;
    }
}