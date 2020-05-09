package frc.auto.mode;

import frc.auto.AutonBuilder;
import frc.auto.drive.DriveSetPosition;
import frc.auto.drive.DriveToPoint;
import frc.auto.drive.DriveWait;
import frc.auto.util.AutonWait;
import frc.auto.mode.AutonMode;

public class Test1 implements AutonMode {

    @Override
    public void addToMode(AutonBuilder ab) {
        ab.addCommand(new AutonWait(4000));
        ab.addCommand(new DriveSetPosition(0, 0 ,180 - 90));
        ab.addCommand(new DriveToPoint(0.0, 6.5, 180 - 90, 7, 7, 1.0, 7, 7, 15000));
        ab.addCommand(new DriveToPoint(0.0, 6.5, 180 - 90, 0, 0, 1.0, 7, 7, 15000));
        ab.addCommand(new DriveWait());
    }
}