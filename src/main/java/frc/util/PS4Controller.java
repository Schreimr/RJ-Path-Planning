package frc.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class PS4Controller {

    private Joystick joystick;

    public PS4Controller(int portNumber) {
        this.joystick = new Joystick(portNumber);
    }
    public double getLeftX() {
        return this.joystick.getRawAxis(0);
    }
    public double getLeftY() {
        return this.joystick.getRawAxis(1);
    }
    public double getRightX() {
        return this.joystick.getRawAxis(2);
    }
    public double getRightY() {
        return this.joystick.getRawAxis(5);
    }
    public double getLeftTriggerY() {
        return this.joystick.getRawAxis(3);
    }
    public double getRightTrigger() {
        return this.joystick.getRawAxis(4);
    }
    public boolean getButton(int btn) {
        return this.joystick.getRawButton(btn);
    }
    public boolean getSquareButton() {
        return this.joystick.getRawButton(1);
    }
    public boolean getCrossButton() {
        return this.joystick.getRawButton(2);
    }
    public  boolean getCircleButton() {
        return this.joystick.getRawButton(3);
    }
    public boolean getTriangleButton() {
        return this.joystick.getRawButton(4);
    }
    public boolean getLeftBumper() {
        return this.joystick.getRawButton(5);
    }
    public boolean getRightBumper() {
        return this.joystick.getRawButton(6);
    }
    public boolean getShareButton() {
        return this.joystick.getRawButton(9);
    }
    public boolean getOptionsButton() {
        return this.joystick.getRawButton(10);
    }
    public boolean getLeftStickButton() {
        return this.joystick.getRawButton(11);
    }
    public boolean getRightStickButton() {
        return this.joystick.getRawButton(12);
    }
    public boolean getPSButton() {
        return this.joystick.getRawButton(13);
    }
    public boolean getTouchPadButton() {
        return this.joystick.getRawButton(14);
    }
    public boolean getUpButton() {
        return this.joystick.getPOV(0) == 0;
    }
    public boolean getRightButton() {
        return this.joystick.getPOV(0) == 90;
    }
    public boolean getDownButton() {
        return this.joystick.getPOV(0) == 180;
    }
    public boolean getLeftButton() {
        return this.joystick.getPOV(0) == 270;
    }
    public void setRumble(double rumble) {
        this.joystick.setRumble(RumbleType.kLeftRumble, rumble);
        this.joystick.setRumble(RumbleType.kRightRumble, rumble);
    }
}