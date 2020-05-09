package frc.auto.drive;

import frc.auto.AutonCommand;
import frc.io.RobotOutput;
import frc.subsystems.Drive;

public class DriveToPoint extends AutonCommand {

    private double x;
    private double y;
    private double theta; //desired angle
    private double minVelocity;
    private double maxVelocity;
    private double eps; //acceptable range
    private double turnRate;
    private double maxTurn;

    private RobotOutput robotOut;
    private Drive drive;

    public DriveToPoint(double x, double y, double theta, double minVelocity, double maxVelocity,
			double eps, double turnRate, double maxTurn, long timeout) {
        super(AutonCommand.RobotComponent.DRIVE, timeout);
        this.x = x;
        this.y = y;
        this.theta = theta;
        this.minVelocity = minVelocity;
        this.maxVelocity = maxVelocity;
        this.eps = eps;
        this.turnRate = turnRate;
        this.maxTurn = maxTurn;

        this.drive = Drive.getInstance();
        this.robotOut = RobotOutput.getInstance();
    }

    @Override
    public void firstCycle() {
        System.out.println("Drive to Point First Cycle X: " + this.x + " Y: " + this.y);
    }

    @Override
    public boolean calculate() {
        return this.drive.driveToPoint(x, y, theta, minVelocity, maxVelocity, turnRate, maxTurn, eps);
    }

    @Override
    public void override() {    
        this.robotOut.setDriveLeft(0);
        this.robotOut.setDriveRight(0);
    }

}