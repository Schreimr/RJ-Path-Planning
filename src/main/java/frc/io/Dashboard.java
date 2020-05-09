package frc.io;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.util.PIDConstants;

public class Dashboard {

    private static Dashboard instance;

    private Dashboard() {

    }

    public static Dashboard getInstance() {
        if (instance == null) {
            instance = new Dashboard();
        }
        return instance;
    }

    public void updateAll() { 
        SensorInput sensorIn = SensorInput.getInstance();
        DriverInput driverIn = DriverInput.getInstance();
        RobotOutput robotOut = RobotOutput.getInstance();

        SmartDashboard.putNumber("Throttle", driverIn.getThrottle());
        SmartDashboard.putNumber("Steer", driverIn.getSteer());

        SmartDashboard.putNumber("Left Encoder Ticks", sensorIn.getEncoderLeftTicks());
        SmartDashboard.putNumber("Right Encoder Ticks", sensorIn.getEncoderRightTicks());

        
        SmartDashboard.putNumber("Left Encoder Speed", sensorIn.getEncoderLeftSpeed());
        SmartDashboard.putNumber("Right Encoder Speed", sensorIn.getEncoderRightSpeed());

        SmartDashboard.putNumber("Robot Angle", sensorIn.getGyroAngle());
        SmartDashboard.putNumber("Gyro Raw Yaw", sensorIn.getRawYawAngle());
        SmartDashboard.putNumber("Gyro Yaw Offset", sensorIn.getOffset());
        SmartDashboard.putNumber("Gyro Fused Heading", sensorIn.getRawFusedHeading());
        
        SmartDashboard.putNumber("Drive L1", robotOut.getDriveLeft());
        SmartDashboard.putNumber("Drive R1", robotOut.getDriveRight());

        SmartDashboard.putNumber("X Position", sensorIn.getDriveXPosition());
        SmartDashboard.putNumber("Y Position", sensorIn.getDriveYPosition());
        
    }
}