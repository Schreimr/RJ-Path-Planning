package frc.auto.drive;

import frc.auto.AutonCommand;
import frc.io.SensorInput;
import frc.util.rjPoint;

public class DriveSetPosition extends AutonCommand {

    private SensorInput sensorInput;

    private double x;
    private double y;
    private double angle;

    public DriveSetPosition(rjPoint p, double theta) {
        this(p.getX(),p.getY(),theta);
    }

    public DriveSetPosition(double x, double y, double theta) {
        super(AutonCommand.RobotComponent.DRIVE);
        this.sensorInput = SensorInput.getInstance();

        this.x = x;
        this.y = y;
        this.angle = theta;
    }

    @Override
    public void firstCycle() {
        System.out.println("Drive Position Set!");
    }

    @Override
    public boolean calculate() {
        this.sensorInput.setDriveXPosition(this.x);
        this.sensorInput.setDriveYPosition(this.y);
        this.sensorInput.setAutonStartAngle(this.angle);
        return true;   
    }

    @Override
    public void override() {    
    }

}