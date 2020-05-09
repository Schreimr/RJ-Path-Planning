package frc.auto;

import java.util.ArrayList;

import frc.auto.AutonCommand;

public class AutonBuilder {

    private ArrayList<AutonCommand> cmds;

    public AutonBuilder() {
        this.cmds = new ArrayList<>();
    }

    public void addCommand(AutonCommand cmd) {
        this.cmds.add(cmd);
    }

    public AutonCommand[] getAutonList() {
        //this.cmds.add(new AutonStop()); //stop at end of automode
        
        AutonCommand[] result = new AutonCommand[this.cmds.size()];
        result = this.cmds.toArray(result);
        return result;
    }

}