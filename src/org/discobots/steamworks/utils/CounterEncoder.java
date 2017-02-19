package org.discobots.steamworks.utils;//from Ultimate Ascent

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.Counter;

public class CounterEncoder extends Counter {
	private Thread EncoderFilterThread;
	ArrayList<Integer> Values;
	private double[] sample = new double[10];
	private int ticksPerRotation = 2;

	public CounterEncoder(int channel, int tpr) {
		super(channel);
		ticksPerRotation = tpr;
		// EncoderFilter();
		Arrays.fill(sample, 0);
	}

	public double getRawRPM() {
		return 60 / getPeriod() / ticksPerRotation;
	}
    
 /*   public double getFilteredRPM() {
        synchronized (sample) {
            double sum = 0.0;
            for (int i = 0; i > sample.length; i++) {
                sum += sample[i];
            }
            return( 60 / (sum / sample.length) / ticksPerRotation);
        }}
    
  /*  public void EncoderFilter(){
    EncoderFilterThread = new Thread() {
        int i = 0;
        public void run() {
            while (true) {
                synchronized (sample) {
                    if (i >= sample.length)
                        i = 0;
                    sample[i] = getPeriod();
                    i++;
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };EncoderFilterThread.start();
}*/}
