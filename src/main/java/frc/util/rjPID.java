package frc.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.util.PIDConstants;
import frc.util.rjLibrary;

public class rjPID {

    private double kP;
    private double kI;
    private double kD;
    private double desiredVal;
    protected double prevError;
    private double errorSum;
    protected double finishedRange;
    private double maxOutput;
    private double minOutput;
    private int minCycleCount;
    private int currentCycleCount;
    private boolean firstCycle;
    private boolean resetI;
    private double rangeI;
    private double lastTime;
    private double deltaTime;
    protected boolean debug;

    public rjPID(double p, double i, double d, double epsRange) {
        this.kP = p;
        this.kI = i;
        this.kD = d;
        this.finishedRange = epsRange;
        this.desiredVal = 0.0;
        this.firstCycle = true;
        this.minOutput = 0.0;
        this.maxOutput = 1.0;
        this.currentCycleCount = 0;
        this.minCycleCount = 5;
        this.rangeI = 1000; //high number to always apply 
        this.resetI = true;  
    }

    public rjPID(PIDConstants constants) {
        this(constants.p, constants.i, constants.d, constants.eps);
    }
    
    public void setConstants(double p, double i, double d, double epsRange) {
        this.kP = p;
        this.kI = i;
        this.kD = d;
        this.finishedRange = epsRange;
    }

    public void setConstants(double p, double i, double d) {
        this.kP = p;
        this.kI = i;
        this.kD = d;
    }

    public void setConstants(PIDConstants constants) {
        this.setConstants(constants.p, constants.i, constants.d);
        this.setFinishedRange(constants.eps);
    }

    public void setFinishedRange(double epsRange) {
        this.finishedRange = epsRange;
    }

    public void setDesiredValue(double val) {
        this.desiredVal = val;
    }

    public void disableIReset() {
        this.resetI = false;
    }

    public void enableIReset() {
        this.resetI = true;
    }
    
    public void setMinOutput(double min) {
        this.minOutput = min;
    }
    public void setMaxOutput(double max) {
        this.maxOutput = max;
    }
    public void setMinMaxOutput(double min, double max) {
        this.minOutput = min;
        this.maxOutput = max;
    }
    public void setMinCycles(int count) {
        this.minCycleCount = count;
    }
    public void resetErrorSum() {
        this.errorSum = 0.0;
    }
    public double getDesiredValue() {
        return this.desiredVal;
    }
    public void setRangeI(double range) {
        this.rangeI = range;
    }

    public double getRangeI() {
        return this.rangeI;
    }

    public double calcPID(double current) {
        return this.calcPIDError(this.desiredVal - current);
    }

    public double calcPIDError(double error){
        double pVal = 0.0;
        double iVal = 0.0;
        double dVal = 0.0;

        if (this.firstCycle) {
            this.prevError = error;
            this.firstCycle = false;
            this.lastTime = System.currentTimeMillis();
            this.deltaTime = 20.0;
        } else {
            double currentTime = System.currentTimeMillis();
            this.deltaTime = currentTime - this.lastTime;
            this.lastTime = currentTime;
        }

        this.deltaTime = (this.deltaTime / 20.0); //20ms cycle scales this to 1
        
        // Proportional Term
        pVal = this.kP * error;

        // Integral Term
        if (Math.abs(error) < Math.abs(this.rangeI)) {
            this.errorSum += error * this.deltaTime;
        } else {
            this.errorSum = 0.0;
        }
        iVal = this.kI * this.errorSum;

        // Derivative Term
        double derivative = (error - this.prevError) / this.deltaTime;
        dVal = this.kD * derivative;

        // determine output
        double output = pVal + iVal + dVal;

        output = rjLibrary.limitValue(output, this.maxOutput);

        if (output > 0) {
            if(output < this.minOutput) {
                output = this.minOutput;
            }
        } else if(output < 0 ) {
            if (output > -this.minOutput) {
                output = -this.minOutput;
            }
        }

        this.prevError = error;

        return output;
    }

    public boolean isDone() {
        double currentError = Math.abs(this.prevError);
        if (currentError <= this.finishedRange) {
            this.currentCycleCount++; // in range
        } else {
            this.currentCycleCount = 0; // not close to target
        }
        return this.currentCycleCount > this.minCycleCount;

    }

    public boolean getFirstCycle() {
        return this.firstCycle;
    }

    public void resetPreviousVal() {
        this.firstCycle = true;
    }
}