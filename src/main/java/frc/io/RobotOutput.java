package frc.io;


//import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.DemandType;
//import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.VictorSP;;
//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMaxLowLevel;

//import edu.wpi.first.wpilibj.Talon;
//import frc.robot.RobotConstants;


public class RobotOutput {
    private static RobotOutput instance;

    private VictorSP driveL1;
    private VictorSP driveR1;
    private VictorSP driveL2;
    private VictorSP driveR2;

    //private double leftDriveTarget = 0;
    //private double rightDriveTarget = 0;

    private RobotOutput() {

        this.driveL1 = new VictorSP(3);
        this.driveL2 = new VictorSP(4);
        this.driveR1 = new VictorSP(0);
        this.driveR2 = new VictorSP(1);
        
        this.configureSpeedControllers();

    }
    
    public static RobotOutput getInstance() {
        if (instance == null) {
            instance = new RobotOutput();
        }
        return instance;
    }

    // Motor Commands

    public void configureSpeedControllers() {
        //this.driveL2.follow(this.driveL1);
        //this.driveR2.follow(this.driveR1);

        this.driveL1.setInverted(false);
        this.driveL2.setInverted(false);
        this.driveR1.setInverted(true);
        this.driveR2.setInverted(true);
    }

    public void setDriveLeft(double speed) {
        //this.driveL1.set(ControlMode.PercentOutput,output);
        this.driveL1.set(speed);   //set(speed);
        this.driveL2.set(speed);
    }

    public void setDriveRight(double speed) {
        //this.driveR1.set(ControlMode.PercentOutput, output);
        this.driveR1.set(speed);
        this.driveR2.set(speed);
    }

   /*
   public double getLeftDriveTarget() {
        return this.leftDriveTarget;
    }

    public double getRightDriveTarget() {
        return this.rightDriveTarget;
    }
    */

   /* public void setDriveRampRate(double secondsFromNeutralToFull) {
        this.driveL1.configOpenloopRamp(secondsFromNeutralToFull);
        this.driveL2.configOpenloopRamp(secondsFromNeutralToFull);
        this.driveR1.configOpenloopRamp(secondsFromNeutralToFull);
        this.driveR2.configOpenloopRamp(secondsFromNeutralToFull);
    }*/

    public void stopAll() {
        setDriveLeft(0);
        setDriveRight(0);
    }

    public double getDriveLeft() {
        return this.driveL1.get();
    }
    public double getDriveRight() {
        return this.driveR1.get();
    }

}