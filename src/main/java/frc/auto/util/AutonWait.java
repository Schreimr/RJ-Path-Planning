package frc.auto.util;

import frc.auto.AutonCommand;
//import frc.auto.RobotComponent;


public class AutonWait extends AutonCommand {

    private long startTime;
    private long delayTime;
    private boolean firstCycle;

    public AutonWait(long waitTime) {
        super(AutonCommand.RobotComponent.UTIL);
        this.delayTime = waitTime;
        this.firstCycle = true;
    }

    @Override
    public void firstCycle() {

    }

    @Override
    public boolean checkAndRun() {
        if (this.firstCycle) {
            this.firstCycle = false;
            this.startTime = System.currentTimeMillis();
        }

        long timeElapsed = System.currentTimeMillis() - this.startTime;
        
        if (timeElapsed < this.delayTime) {
            return false;
        } else {
            System.out.println("Auton Wait Check n Run time elapsed");
            return super.checkAndRun();
        }
        

    }


    @Override
    public boolean calculate() {
        System.out.println("Auton Wait Calculate");
        return true;
    }
    
    @Override
    public void override() {

    }


}