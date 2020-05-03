package frc.robot;

import frc.util.PIDConstants;

public class RobotConstants {
	// Drive Size Constants (Feet)
	public static final double DRIVE_MAX_VELOCITY = 14.0;
    public static final double DRIVE_TICKS_PER_INCH_HIGH = 1024.0 / Math.PI * 6.0;
	public static final double DRIVE_FPS_TO_RPM = (60.0 / (Math.PI * 6.0)) * 12.0;
    
    private static PIDConstants driveStraightPID = new PIDConstants(3.0, 0.04, 5.0, 0.05);
	private static PIDConstants driveTurnPID = new PIDConstants(0.2, 0.005, 0.6, 1);
	private static PIDConstants driveVelocityPID = new PIDConstants(0.0, 0, 0.0, 0.0);
	private static PIDConstants gyroPID = new PIDConstants(0, 0, 0, 0);
	
    public static PIDConstants getDriveStraightPID() {
        return driveStraightPID;
    }  
    public static PIDConstants getDriveTurnPID() {
        return driveTurnPID;
    }
    public static PIDConstants getDriveVelocityPID() {
        return driveVelocityPID;
    }
    public static PIDConstants getGyroPID() {
        return gyroPID;
    }

}