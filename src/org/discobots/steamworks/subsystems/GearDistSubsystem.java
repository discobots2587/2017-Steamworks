package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;
import org.discobots.steamworks.Robot;
import org.discobots.steamworks.utils.Lidar;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/** LiftSubsystem
 * This class has a thread so that we can actively update the speed of the 
 * Gear based on the distance from the goal
 * PID Controller does work with the lidar.
 * 
 */
public class GearDistSubsystem extends PIDSubsystem {
	private int offset=0;//add to this if the lidar module is inset into the robot. This will be exact distance from Gear to goal
	private Lidar GearLidar;
	public static final double kMaxDist = 7;//values are to change once added
	public static final double kDistSlow = 6;//ranging GearSpeed
	public static final double kMinDist = 4;//smallest distance the robot can be from the gear loader
	public static final double kP = 1.0 / 4.0, kI = 0, kD = 0;
	PIDOutput output;
	PIDSource source;
	public boolean useLidar = false;
	
	SpeedMonitor speedControlThread;
	double setpointSpeed;
	
	public GearDistSubsystem() {
		super("GearDist",kP, kI, kD);
		setpointSpeed=0;
		speedControlThread = new SpeedMonitor();
		speedControlThread.setName("D.SpeedControl");
		speedControlThread.start();
		
		this.setAbsoluteTolerance(1);
    	this.setOutputRange(0, 1);//motor range
		}
	
	
//	public double getCurrent(boolean motor) {
//		if (Motor Running) {
//			return Motor.getOutputCurrent();
//		} else {
//			return Motor.getOutputCurrent();
//		}
//	}
	
	public double getGearDistInches() {
		try{
		return GearLidar.getDistanceIn() + offset/2.54;
		}
		catch(NullPointerException e)
		{
			System.out.println("NULL POINTER ERROR GETTING LIDAR");
		}
		return 0;
	}  
	
	public double getGearDistCM() {
			try{
		return GearLidar.getDistanceCm();
			}
			catch(NullPointerException e)
			{
				System.out.println("NULL POINTER EXCEPTION ");
			}
			return 0;
	}
	
	public double getRawGearSpeed(){
		return this.setpointSpeed;
	}
	
	
	public boolean isLidarRunning()
	{
		return useLidar;
	}

	public boolean TooClose() {
		if (GearLidar.getDistanceIn()<kMinDist)
		return true;
		else
			return false;
	}

	public void initDefaultCommand() {//runs when no other command requires this subysystem
			//setDefaultCommand();
		}

	public void setSpeed(double input) {
		this.disable();
		this.setpointSpeed = input;
	}

	public void shutdownLidar() {
		this.useLidar = false;
		this.disable();
	}
	
	private void setSpeedInternal(double input) {//may need to change to play nice with the Gear subystem....
		double output = input;
		if (useLidar && this.getGearDistInches() > kMinDist && output > 0) {//may change to Centimeters but inches might be easier for min/max distances
		//	output = output / 2;
		}
		if(Robot.gearSub.getGear());
		{	
		}
		if(!Robot.gearSub.getGear());
		{
		}
	}

	@Override
	protected double returnPIDInput() {
		return this.getGearDistCM();
	}

	@Override
	protected void usePIDOutput(double output) {
		if (useLidar) {
			this.setpointSpeed = output;
		} else {
			this.setpointSpeed = 0;
		}
	}
	
	class SpeedMonitor extends Thread {
		public SpeedMonitor() {
			
		}
		public void run() {
			while (true) {
				setSpeedInternal(setpointSpeed);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
