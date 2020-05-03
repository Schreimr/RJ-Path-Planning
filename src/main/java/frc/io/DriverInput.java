package frc.io;

import frc.util.PS4Controller;

public class DriverInput {

    private static DriverInput instance;

    private PS4Controller driver;
    private PS4Controller operator;

    private DriverInput () {
        this.driver = new PS4Controller(0);
        this.operator = new PS4Controller(1);
    }

    public static DriverInput getInstance() {
        if (instance == null) {
            instance = new DriverInput();
        }
        return instance;
    }

    // Driver Controls

    public double getThrottle() {
        return driver.getLeftY();
    }

    public double getSteer() {
        return driver.getRightX();
    }

    public boolean stopOnTrigger() {
        return (driver.getRightTrigger() > 0.4);
    }

    // Operator Controls
    
}