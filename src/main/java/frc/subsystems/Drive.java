package frc.subsystems;

import frc.subsystems.Subsystem;
import frc.io.RobotOutput;
import frc.io.SensorInput;
import frc.io.DriverInput;
import frc.robot.RobotConstants;

import frc.util.rjPID;
import frc.util.rjPIDF;

public class Drive extends Subsystem {

    public enum driveState {
        OUTPUT, VELOCITY
    }

    private driveState currentState;
    private static Drive instance;
    private RobotOutput robotOut;
    private SensorInput sensorInput;
    private DriverInput driverIn;

    private double leftOut;
    private double rightOut;

    //private rjPID leftVelocityPID;
    //private rjPID rightVelocityPID; 
    private rjPID straightPID;
    private rjPID turnPID;



    private Drive(){
        this.robotOut = robotOut.getInstance();
        this.sensorInput = sensorInput.getInstance();
        this.driverIn = driverIn.getInstance();
        this.firstCycle();
    }

    public static Drive getInstance() {
        if (instance == null) {
            instance = new Drive();
        }
        return instance;
    }

    @Override
    public void firstCycle() {
        this.straightPID = new rjPID(RobotConstants.getDriveStraightPID());
        this.straightPID.setMinCycles(1);
        this.straightPID.setRangeI(1);

        this.turnPID = new rjPID(RobotConstants.getDriveTurnPID());
        this.turnPID.setMinCycles(10);
        this.turnPID.setMaxOutput(10);
        this.turnPID.setRangeI(10);
    }

    public void setOutput(double throttle, double turn) {
        this.leftOut = throttle + turn;
        this.rightOut = throttle - turn;
    }

    

    @Override
    public void calculate() {
        if (this.currentState == driveState.OUTPUT) {
            robotOut.setDriveLeft(this.leftOut);
            robotOut.setDriveRight(this.rightOut);
        } else if (this.currentState == driveState.VELOCITY) {
            //should fill this out later
        }
    }

    @Override
    public void disable() {
        robotOut.setDriveLeft(0);
        robotOut.setDriveRight(0);
    }
}