/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.io.SensorInput;
import frc.io.DriverInput;
import frc.io.RobotOutput;
//import frc.io.Dashboard;
import frc.teleop.TeleopControl;
import frc.subsystems.Drive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private SensorInput sensorInput;
  private RobotOutput robotOut;
  private TeleopControl teleopControl;
  //private Dashboard dashboard;
  //private rjLogger logger;
  //private Dashboard dashboard;
  public static boolean teleopInitialized = false;
  private DriverInput driverIn;
  private Drive drive;
    
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //debugger
    //dashboard
    this.robotOut = robotOut.getInstance();
    this.sensorInput = sensorInput.getInstance();
    this.teleopControl = teleopControl.getInstance();
    //this.dashboard = dashboard.getInstance();
    //logger
    this.drive = drive.getInstance();
    this.driverIn = driverIn.getInstance();
    this.robotOut.configureSpeedControllers();  
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
  }

  /**
   * This function is called once when teleop is enabled.
   */
  @Override
  public void teleopInit() {
    //this.teleopControl.initialize();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    this.sensorInput.update();
    //this.dashboard.updateAll();
    //this.teleopControl.runCycle();
    /*
        //input
        double y = driverIn.getThrottle();
        double x = driverIn.getSteer();
    
        //deadband
        if (Math.abs(y) < 0.05) {
            x = 0;
        }
        if (Math.abs(x) < 0.05) {
            x = 0;
        }
        //scaling
            //leaving out for now
    
        //output
        drive.setOutput(y,x);
      */  
    
  }

  /**
   * This function is called once when the robot is disabled.
   */
  @Override
  public void disabledInit() {
    this.robotOut.stopAll();
    //this.teleopControl.disable();
    //this.logger.close();
  }

  /**
   * This function is called periodically when disabled.
   */
  @Override
  public void disabledPeriodic() {
    this.sensorInput.update();
    //this.dashboard.updateAll();
    //autonControl.
  }

  /**
   * This function is called once when test mode is enabled.
   */
  @Override
  public void testInit() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
