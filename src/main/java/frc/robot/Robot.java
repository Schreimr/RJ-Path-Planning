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
import frc.io.RobotOutput;
import frc.io.Dashboard;
import frc.auto.AutonControl;
import frc.teleop.TeleopControl;


public class Robot extends TimedRobot {

  private SensorInput sensorInput;
  private RobotOutput robotOut;
  private TeleopControl teleopControl;
  private AutonControl autonControl;
  private Dashboard dashboard;
  //private rjLogger logger;
  public static boolean teleopInitialized = false;
    
  @Override
  public void robotInit() {
    //debugger
    this.robotOut = RobotOutput.getInstance();
    this.teleopControl = TeleopControl.getInstance();
    this.dashboard = Dashboard.getInstance();
    this.autonControl = AutonControl.getInstance();
    //logger
    this.robotOut.configureSpeedControllers();
    this.sensorInput = SensorInput.getInstance();  
    sensorInput.reset();
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    System.out.println("AUTON ENABLED");
    autonControl.initialize();
    sensorInput.reset();
    sensorInput.resetAutonTimer();
  }

  @Override
  public void autonomousPeriodic() {
    sensorInput.update();
    dashboard.updateAll();
    autonControl.runCycle();
  }

  @Override
  public void teleopInit() {
    System.out.println("TELEOP ENABLED");
    this.teleopControl.initialize();
  }


  @Override
  public void teleopPeriodic() {
    this.sensorInput.update();
    this.dashboard.updateAll();
    this.teleopControl.runCycle();
  }

  @Override
  public void disabledInit() {
    System.out.println("DISABLED");
    this.robotOut.stopAll();
    this.teleopControl.disable();
    //this.logger.close();
  }

  @Override
  public void disabledPeriodic() {
    this.sensorInput.update();
    this.dashboard.updateAll();
    //autonControl.
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }
}
