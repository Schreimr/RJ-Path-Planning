package frc.util;

import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

public class rjNavx extends AHRS{

    private double prevAngle;
    private double angle;

    public rjNavx(){
        super(SPI.Port.kMXP);
        this.reset();
    }

    @Override
    public double getAngle(){
        return 180 - (this.angle + this.getFusedHeading());
    }

    @Override
    public void reset(){
        this.angle = 90 - this.getFusedHeading();
        this.prevAngle = this.getFusedHeading();
    }

    public void update(){
        double diff = this.getFusedHeading() - this.prevAngle;
        if(diff > 180){
            this.angle -= 360;
        }else if (diff < -180){
            this.angle += 360;
        }
        this.prevAngle += diff;
    }

}