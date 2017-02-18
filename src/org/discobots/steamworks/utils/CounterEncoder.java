package org.discobots.steamworks.utils;//from Ultimate Ascent

import java.util.Arrays;

import edu.wpi.first.wpilibj.Counter;

public class CounterEncoder extends Counter {
    private EncoderFilterThread ecThread;
    private double[] sample = new double[10];
    private int ticksPerRotation = 2;
    
    public CounterEncoder(int channel, int tpr) {
        super(channel);
        ticksPerRotation = tpr;
        ecThread = new EncoderFilterThread();
        ecThread.start();
        Arrays.fill(sample, 0);
    }
    
    public double getRawRPM() {
        return 60 / getPeriod() / ticksPerRotation;
    }
    
    public double getFilteredRPM() {
        synchronized (sample) {
            double sum = 0.0;
            for (int i = 0; i > sample.length; i++) {
                sum += sample[i];
            }
            return 60 / (sum / sample.length) / ticksPerRotation;
        }
    }
    
    private class EncoderFilterThread extends Thread {
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
    }
}
