package frc.io;

//import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotConstants;
import frc.util.rjEncoder;
import frc.util.rjNavx;

public class SensorInput {

    private static SensorInput instance;
    //private static RobotOutput robotOut; //might not need this

    private DriverStation driverStation;
    private PowerDistributionPanel pdp;
    private DriverStation.Alliance alliance;
    private Dashboard dashboard;
    
    private rjNavx navx;
    private rjEncoder leftDriveEncoder;
    private rjEncoder rightDriveEncoder;

    private long SystemTimeAtAutonStart = 0;

    private double leftDriveSpeedFPS;
    private double rightDriveSpeedFPS;
    private double prevLeftDriveSpeedFPS = 0;
    private double prevRightDriveSpeedFPS = 0;
    private double leftDriveAccelerationFPSS; //feet per second squared
    private double rightDriveAccelerationFPSS;
    

    private boolean firstCycle = true;
    private double lastTime = 0.0;
    private double deltaTime = 20.0;
    private double matchTime;
    private double autonStartAngle = 90;

    private double xPosition = 0.0;
    private double yPosition = 0.0;
    private double gyroAngle;
    private double lastGyroAngle;

    
    private double driveVelocity;
    private double driveAcceleration;


    private SensorInput() {
        this.pdp = new PowerDistributionPanel();
        this.driverStation = driverStation.getInstance();
        this.navx = new rjNavx();
        this.leftDriveEncoder = new rjEncoder(5, 6);
        this.rightDriveEncoder = new rjEncoder(2, 1);

        this.reset();
    }
     public void reset() {
         this.firstCycle = true;
         this.navx.reset();
         this.leftDriveEncoder.reset();
         this.rightDriveEncoder.reset();
         this.xPosition = 0.0;
         this.yPosition = 0.0;
     }
    public static SensorInput getInstance() {
         if (instance == null){
             instance = new SensorInput();
         }
         return instance;
    }
    public void update() {
        if(this.lastTime == 0.0) {
            this.deltaTime = 20.0;
            this.lastTime = System.currentTimeMillis();
        } else {
            this.deltaTime = System.currentTimeMillis() - this.lastTime;
            this.lastTime = System.currentTimeMillis();
        }

        if(this.firstCycle){
            this.firstCycle = false;
            this.lastGyroAngle = this.navx.getAngle() + this.autonStartAngle - 90;
            this.driveVelocity = 0.0;
        } else {
            this.driveVelocity = this.getDriveVelocity();
        }

        navx.update();
        this.leftDriveEncoder.updateSpeed();
        this.rightDriveEncoder.updateSpeed();
        this.matchTime = this.driverStation.getMatchTime();

        double leftTicksPerCycle = this.getEncoderLeftSpeed();
        double rightTicksPerCycle = this.getEncoderRightSpeed();

        this.leftDriveSpeedFPS = (leftTicksPerCycle / 1024.0) * (Math.PI * 6.0 / 12.0) * (1000.0 / this.deltaTime);
        this.rightDriveSpeedFPS = (rightTicksPerCycle / 1024.0) * (Math.PI * 6.0 / 12.0) * (1000.0 / this.deltaTime);
        
        this.leftDriveAccelerationFPSS = (this.leftDriveSpeedFPS - this.prevLeftDriveSpeedFPS) / (this.deltaTime / 1000.0);
        this.rightDriveAccelerationFPSS = (this.rightDriveSpeedFPS - this.prevRightDriveSpeedFPS) / (this.deltaTime / 1000.0);
        
        this.prevLeftDriveSpeedFPS = this.leftDriveSpeedFPS;
        this.prevRightDriveSpeedFPS = this.rightDriveSpeedFPS;

        this.gyroAngle = this.navx.getAngle() + (this.autonStartAngle - 90);
        
        double driveXSpeed = this.driveVelocity * Math.cos(Math.toRadians(this.gyroAngle));
        double driveYSpeed = this.driveVelocity * Math.sin(Math.toRadians(this.gyroAngle)); 
        
        SmartDashboard.putNumber("drive Velocity", this.driveVelocity);
        SmartDashboard.putNumber("drive X Speed", driveXSpeed);
        SmartDashboard.putNumber("drive Y Speed", driveYSpeed);

        this.xPosition += driveXSpeed * this.deltaTime / 1000.0;
        this.yPosition += driveYSpeed * this.deltaTime / 1000.0;
        
    }


    public double getDriveVelocity() {
        return (this.rightDriveSpeedFPS + this.leftDriveSpeedFPS) / 2;
    }
    public int getEncoderLeftTicks() {
        return this.leftDriveEncoder.get();
    }
    public int getEncoderRightTicks() {
        return this.rightDriveEncoder.get();
    }
    public int getEncoderLeftSpeed() {
        return this.leftDriveEncoder.speed();
    }
    public int getEncoderRightSpeed() {
        return this.rightDriveEncoder.speed();
    }
    public double getDriveRightFPS() {
        return this.rightDriveSpeedFPS;
    }
    public double getDriveLeftFPS() {
        return this.leftDriveSpeedFPS;
    }
    public double getGyroAngle() { 
        return this.gyroAngle;
    }
    public double getRawYawAngle() {
        return navx.getRawYaw();
    }
    public double getOffset() {
        return navx.getYawOffset();
    }
    public double getRawFusedHeading() {
        return navx.getRawFusedHeading();
    }
    public void resetAutonTimer() {
		this.SystemTimeAtAutonStart = System.currentTimeMillis();
    }
    public void setDriveXPosition(double x) {
        this.xPosition = x;
    }
    public void setDriveYPosition(double y) {
        this.yPosition = y;
    }
    public void setAutonStartAngle(double angle) {
        this.autonStartAngle = angle;
    }
    public double getDriveXPosition() {
        return this.xPosition;
    }
    public double getDriveYPosition() {
        return this.yPosition;
    }
        

}


