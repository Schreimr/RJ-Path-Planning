package frc.auto;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.auto.AutonBuilder;
import frc.auto.AutonCommand;
import frc.auto.mode.AutonMode;
import frc.io.RobotOutput;
import frc.io.DriverInput;

import frc.auto.mode.DefaultMode;
import frc.auto.mode.Test1;


public class AutonControl {

    private static AutonControl instance;

    private static final int NUM_ARRAY_MODE_STEPS = 2;

    private DriverInput driverIn;
    private RobotOutput robotOut;

    private int autonDelay;
    private long autonStartTime;

    private boolean running = false;

    private int currAutonStepToSet = 0;
    private int[] autonSubmodeSelections = new int[NUM_ARRAY_MODE_STEPS];
    private ArrayList<ArrayList<AutonMode>> autonSteps = new ArrayList<>();
        
    private int currIndex;
    private AutonCommand[] commands;

    private String autonSelectError = "NO ERROR";

    public static AutonControl getInstance() {
        if (instance == null) {
            instance = new AutonControl();
        }
        return instance;
    }

    private AutonControl() {
        System.out.println("AutonControl");
        this.autonDelay = 0;
        this.currIndex = 0;

        for(int i = 0; i < NUM_ARRAY_MODE_STEPS; i++ ) {
            this.autonSteps.add(new ArrayList<AutonMode>());
            this.autonSubmodeSelections[i] = 0; //default auto
        }

        ArrayList<AutonMode> step1 = this.autonSteps.get(0);
        step1.add(new DefaultMode());

        ArrayList<AutonMode> step2 = this.autonSteps.get(1);
        step2.add(new Test1());

        System.out.println("Auton Steps: ");
        System.out.println(this.autonSteps.toString());

    }

    private void updateSelectedModes() {
        //need to do something to select modes here
    }

    public void initialize() {
        System.out.println("START AUTON");

        this.currIndex = 0;
        this.running = true;

        AutonBuilder ab = new AutonBuilder();

        // populate auton steps with the submodes that were selected
        for(int i = 0; i < this.autonSteps.size(); i++) {
            this.autonSteps.get(i).get(this.autonSubmodeSelections[i]).addToMode(ab);
        }

        //create list of all commands
        this.commands = ab.getAutonList();
        System.out.println("Commands length " + this.commands.length);
        for (int i = 0 ; this.commands.length< i; i++ ) {
            System.out.println(this.commands.toString());
        }

        this.autonStartTime = System.currentTimeMillis();

        //clear out any commands
        AutonCommand.reset();

    }

    public void runCycle() {
        long timeElapsed = System.currentTimeMillis() - this.autonStartTime;

        if (timeElapsed > this.getAutonDelay() && this.running) {
            while(this.currIndex < this.commands.length && this.commands[this.currIndex].checkAndRun()) {
                this.currIndex++;
            }         
            AutonCommand.execute();
        }else {
            robotOut.stopAll();
        }
    }

    public void stop() {
        this.running = false;
    }

    public int getAutonDelay() {
        return autonDelay;
    }


}