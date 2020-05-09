package frc.auto.mode;

import frc.auto.AutonBuilder;
import frc.auto.mode.AutonMode;
import frc.auto.util.AutonWait;

public class DefaultMode implements AutonMode {

    @Override
    public void addToMode(AutonBuilder ab) {
        ab.addCommand(new AutonWait(3000));
    }
}