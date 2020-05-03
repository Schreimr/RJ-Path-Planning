package frc.teleop;

import frc.io.DriverInput;
import frc.io.SensorInput;
import frc.io.RobotOutput;
import frc.subsystems.Drive;

public class TeleopControl {

    private static TeleopControl instance;

    //private RobotOutput robotOut;
    //private SensorInput sensorIn;
    //private DriverInput driverIn;

    //private Drive drive;
    

    private TeleopControl() {
        //this.sensorIn = SensorInput.getInstance();
        //this.driverIn = DriverInput.getInstance();
        //this.robotOut = RobotOutput.getInstance();

        //this.drive = Drive.getInstance();
    }

    public TeleopControl getInstance() {
        if (instance == null) {
            instance = new TeleopControl();
        }
        return instance;
    }

    public void initialize() {
        //drive.firstCycle();
    }

    public void disable() {
        //drive.disable();
    }

    public void runCycle() {
        /*
        ////////////////////
        ////// DRIVE ///////
        ///////////////////

        //input
        double y = driverIn.getThrottle();
        double x = driverIn.getSteer();

        //deadband
        if (Math.abs(y) < 0.05) {
            x = 0;
        }
        if (Math.abs(x) < 0.05) {
            x = 0;
        }
        //scaling
            //leaving out for now

        //output
        drive.setOutput(y,x);
        */
    }

}