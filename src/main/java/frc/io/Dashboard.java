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
        RobotOutput robotOut = RobotOutput.getInstance();

        SmartDashboard.putNumber("Left Encoder Ticks", sensorIn.getEncoderLeftTicks());
        SmartDashboard.putNumber("Right Encoder Ticks", sensorIn.getEncoderRightTicks());
        SmartDashboard.putNumber("Gyro Angle", sensorIn.getGyroAngle());
        SmartDashboard.putNumber("Drive L1", robotOut.getDriveLeft());
        SmartDashboard.putNumber("Drive R1", robotOut.getDriveRight());
    }
}