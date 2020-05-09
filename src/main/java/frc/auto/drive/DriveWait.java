package frc.auto.drive;

import frc.auto.AutonCommand;

public class DriveWait extends AutonCommand {

    public DriveWait() {
        super(AutonCommand.RobotComponent.DRIVE);
    }

    @Override
    public void firstCycle() {
    }

    @Override
    public boolean calculate() {
        return true;    
    }

    @Override
    public void override() {    
    }

}