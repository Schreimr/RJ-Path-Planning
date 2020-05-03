package frc.util;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import java.util.ArrayList;

public class rjEncoder extends Encoder {
    
    private int prev; //previous encoder counts
    private int speed; //measured as a delta of counts
    private int offset;
    private ArrayList<Integer> pastSpeeds;
	private static final int NUM_SPEED_CYCLES = 5;


    public rjEncoder(int aChannel, int bChannel, boolean reverseDirection, CounterBase.EncodingType encodingType,
    int preset){
        super(aChannel, bChannel, reverseDirection, encodingType);
		this.offset = preset;
        this.setDistancePerPulse(1);
        this.pastSpeeds = new ArrayList<Integer>();
    }

    public rjEncoder(int aChannel, int bChannel, boolean reverseDirection, int preset){
        this(aChannel, bChannel, reverseDirection, CounterBase.EncodingType.k4X, preset);
    }

    public rjEncoder(int aChannel, int bChannel, boolean reverseDirection){
        this(aChannel, bChannel, reverseDirection, CounterBase.EncodingType.k4X, 0);
    }

    public rjEncoder(int aChannel, int bChannel){
        this(aChannel, bChannel, false, CounterBase.EncodingType.k4X, 0);
    }

    @Override
    public int get() {
        return super.get() + this.offset;
    }

    @Override
    public void reset() {
        super.reset();
        this.offset = 0;
        this.prev = 0;
    }

    public void set(int val){
        super.reset();
        this.offset = val;
    }

    public int speed(){
        return this.speed;
    }

    public void updateSpeed(){
        int curr = this.get();
        this.speed = curr - this.prev;
        this.prev = curr;
        
        if(this.pastSpeeds.size() >= NUM_SPEED_CYCLES) {
            this.pastSpeeds.remove(0);
        }
        this.pastSpeeds.add(this.speed);
    }

    public double getRunningSpeed() {
        double sum = 0.0;
        for(Integer speed : this.pastSpeeds){
            sum = sum + speed;
        }
        return sum / this.pastSpeeds.size();
    }

    public double rawSpeed(){
        return this.getRate();
    }

    
}