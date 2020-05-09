package frc.auto;

public abstract class AutonCommand {
    
    public enum RobotComponent {
        UTIL, DRIVE
    }

    private static AutonCommand[] autonComponents = new AutonCommand[RobotComponent.values().length];

    private RobotComponent cmdType;

    private long timeout = -1;
    private long startTime = -1;
    private boolean firstCycle = true;

    public AutonCommand(RobotComponent type, long timeoutLength) {
        this.cmdType = type;
        this.timeout = timeoutLength;
    }
    public AutonCommand(RobotComponent type) {
        this(type, -1);
    }

    public abstract void firstCycle();

    public abstract boolean calculate();

    public abstract void override();

    public boolean checkAndRun() {
        if (autonComponents[this.cmdType.ordinal()] == null){
            autonComponents[this.cmdType.ordinal()] = this;
            return true;
        } else {
            return false;
        }
    };

    public boolean runCommand() {
        System.out.println("runCommand " + this.toString());
        if (this.startTime < 0) {
            this.startTime = System.currentTimeMillis();
        }

        if (this.firstCycle) {
            System.out.println("First Cycle");
            this.firstCycle();
            this.firstCycle = false;
        }
        System.out.println("Calculate");
        boolean done = this.calculate();
        long timePassed = System.currentTimeMillis() - this.startTime;

        if (this.timeout > 0 && timePassed > this.timeout) {
            this.override();
            System.out.println("Timeout Occurred");
            return true;
        } else if (done) {
            System.out.println("Command Done");
            return true;
        } else {
            return false;
        }

    }

    public static void execute() {
        for (int i = 0; i < autonComponents.length; i++) {
            if (autonComponents[i] != null) {
                System.out.println("Executing" + autonComponents[i].toString());
                boolean done = autonComponents[i].runCommand();
                if (done) {
                    autonComponents[i] = null;
                }
            }
        }
    }

    public static void overrideComponent(AutonCommand.RobotComponent component) {
        if (autonComponents[component.ordinal()] != null) {
            autonComponents[component.ordinal()].override();
        }
        autonComponents[component.ordinal()] = null;
    }

    public static void reset() {
        for (int i = 0; i < autonComponents.length; i++) {
            autonComponents[i] = null;
        }
    }
}