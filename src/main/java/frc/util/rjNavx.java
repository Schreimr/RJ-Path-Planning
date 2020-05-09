package frc.util;

import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

public class rjNavx extends AHRS{

    // Field orientation - center wall of center driver station is (0, 0)
    // Y direction is towards opponent driver station.
    // X direction is to the driver's right when facing the field
    // X axis is 0 degrees, Y axis is 90 degrees
    // Top view of robot makes possitive angle CCW 
    // If robot starts facing downfield it starts at angle of 90 degrees
    // NavX uses CW as positive

    private double prevAngle;
    private double angleOffset;
    private boolean offsetCalibrated = false;
    private long waitTime = 5000;

    public rjNavx(){
        super(SPI.Port.kMXP);
        this.reset();
    }

    @Override
    public double getAngle(){ //angle should be between 180 and -180
        return 180 - this.angleOffset - this.getFusedHeading();
    }

    public double getRawYaw() {
        return this.getYaw();
    }
    public double getYawOffset() {
        return this.angleOffset;
    }
    public double getRawFusedHeading() {
        return this.getFusedHeading();
    }

    @Override
    public void reset(){
        this.angleOffset = 90 - this.getRawFusedHeading();
    }

    public void update(){
        double angle = this.getAngle();
        if(angle > 180){
            this.angleOffset += 360;
        }else if (angle < -180){
            this.angleOffset -= 360;
        }
    }

}