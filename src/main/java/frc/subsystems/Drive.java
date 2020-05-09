package frc.subsystems;

import frc.subsystems.Subsystem;
import frc.io.RobotOutput;
import frc.io.SensorInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.io.DriverInput;
import frc.robot.RobotConstants;
import frc.io.Dashboard;
import frc.util.rjLibrary;
import frc.util.rjPID;
import frc.util.rjPIDF;
import frc.util.rjPoint;

public class Drive extends Subsystem {

    public enum driveState {
        OUTPUT, VELOCITY
    }

    private driveState currentState;
    private static Drive instance;
    private RobotOutput robotOut;
    private SensorInput sensorInput;
    private DriverInput driverIn;
    private SmartDashboard dashboard;

    private double leftOut;
    private double rightOut;

    private rjPID leftVelocityPID;
    private rjPID rightVelocityPID; 
    private rjPID straightPID;
    private rjPID turnPID;


    private Drive(){
        this.robotOut = RobotOutput.getInstance();
        this.sensorInput = SensorInput.getInstance();
        this.driverIn = DriverInput.getInstance();
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

        this.leftVelocityPID = new rjPIDF(RobotConstants.getDriveVelocityPID().p, RobotConstants.getDriveVelocityPID().i,
            RobotConstants.getDriveVelocityPID().d, RobotConstants.getDriveVelocityPID().ff,
            RobotConstants.getDriveVelocityPID().eps);
        this.rightVelocityPID = new rjPIDF(RobotConstants.getDriveVelocityPID().p, RobotConstants.getDriveVelocityPID().i,
            RobotConstants.getDriveVelocityPID().d, RobotConstants.getDriveVelocityPID().ff,
            RobotConstants.getDriveVelocityPID().eps);
        this.leftVelocityPID.setMaxOutput(1);
        this.rightVelocityPID.setMaxOutput(1);

    }

    public void setOutput(double throttle, double turn) {
        this.leftOut = throttle + turn;
        this.rightOut = throttle - turn;
        currentState = driveState.OUTPUT;
    }   

    public void setVelocity(double leftVelocity, double rightVelocity) {
        this.currentState = driveState.VELOCITY;
        //System.out.println("DriveState: " + this.currentState);

        this.leftVelocityPID.setDesiredValue(leftVelocity);
        this.rightVelocityPID.setDesiredValue(rightVelocity);
        
        double printLeftError = this.sensorInput.getDriveLeftFPS()-this.leftVelocityPID.getDesiredValue();
        //System.out.println("Left Speed Error: " + printLeftError );
        //double printRightError = this.sensorInput.getDriveRightFPS()-this.rightVelocityPID.getDesiredValue();
        //System.out.println("Right Speed Error: " +  printRightError);

        this.rightOut = this.rightVelocityPID.calcPID(this.sensorInput.getDriveRightFPS());
        this.leftOut = this.leftVelocityPID.calcPID(this.sensorInput.getDriveLeftFPS());


        //System.out.println("Set output Left: " + this.leftOut);
        //System.out.println("Set output Right: " + this.rightOut);
        
        this.robotOut.setDriveLeft(leftOut);
		this.robotOut.setDriveRight(rightOut);
    }

    @Override
    public void calculate() {
        dashboard.putString("driveState",currentState.toString());
        if (this.currentState == driveState.OUTPUT) {
            robotOut.setDriveLeft(this.leftOut);
            robotOut.setDriveRight(this.rightOut);
        } else if (this.currentState == driveState.VELOCITY) {
            robotOut.setDriveLeft(this.leftOut);
            robotOut.setDriveRight(this.rightOut);            
        }
        dashboard.putNumber("leftOut", this.leftOut);
        dashboard.putNumber("rightOut", this.rightOut);
    }

    @Override
    public void disable() {
        robotOut.setDriveLeft(0);
        robotOut.setDriveRight(0);
    }




    public rjPoint getRotatedError(double theta, double targetX, double targetY) {
        double currentX = this.sensorInput.getDriveXPosition();
        double currentY = this.sensorInput.getDriveYPosition();
        double rotation = 90 - theta;

        rjPoint currentPosition = new rjPoint(currentX, currentY);
        rjPoint targetPosition = new rjPoint(targetX, targetY);
        
        currentPosition.rotateByAngle(rotation);
        targetPosition.rotateByAngle(rotation);

        double xError = targetPosition.getX() - currentPosition.getX();
        double yError = targetPosition.getY() - currentPosition.getY();

        return new rjPoint(xError,yError);
    };

    public boolean driveToPoint(double x, double  y, double theta, double minVelocity, double maxVelocity,
        double turnRate, double maxTurn, double eps) {
            
            double targetHeading;
            double headingError;
            double angle;
            double throttle;
            double yError;
            double steer;
            double leftOut;
            double rightOut;
            boolean isDone = false;

            this.straightPID.setMinMaxOutput(minVelocity, maxVelocity);

            rjPoint error = getRotatedError(theta, x, y);
            //flip X if travelling backwards;
            if(error.getX() < 0) {
                error.setX(-error.getX());
            }

            //based on X distance from target turn more. This is what creates the arc.
            double turningOffset = (error.getX() * turnRate); 

            //limit turn
            if (turningOffset > maxTurn) {
                turningOffset = maxTurn;
            } else if (turningOffset < - maxTurn) {
                turningOffset = -maxTurn;
            }

            targetHeading = theta - turningOffset;
            this.turnPID.setDesiredValue(targetHeading);
            
            yError = error.getY();
            throttle = this.straightPID.calcPIDError(yError);

            
            targetHeading = theta - turningOffset;
            angle = sensorInput.getGyroAngle();
            headingError = Math.abs(this.turnPID.getDesiredValue() - angle);         
            
            if (headingError > 90) {
                headingError = 90; //prevents flipping of throttle output;
            }

            //scale throttle based on angle from target
            throttle = throttle * ((-1 * headingError / 90.0) + 1);

            steer = -this.turnPID.calcPID(angle);

            leftOut = rjLibrary.calcLeftTankDrive(throttle, steer);
            rightOut = rjLibrary.calcRightTankDrive(throttle, steer); 
            
            this.setVelocity(leftOut, rightOut);

            //Check if we've reached target
            if (this.straightPID.isDone()) {
                System.out.println("Y target reached");
            }
            
            if (minVelocity <= 0.5 ) {
                if (this.straightPID.isDone()) {
                    System.out.println("Straight PID is done");
                    disable();
                    isDone = true;
                }
            } else if (Math.abs(yError) < eps) {
                isDone = true;
                System.out.println("driveToPoint done Y error: " + yError);

            }
            return isDone;
    }
}