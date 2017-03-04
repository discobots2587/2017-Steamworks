package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.Robot;
import org.discobots.steamworks.utils.Lidar;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/** LiftSubsystem
 * This class has a thread so that we can actively update the speed of the 
 * shooter based on the distance from the goal
 * PID Controller does work with the lidar.
 * 
 */
public class RangeFinderSubsystem extends PIDSubsystem {
	private int offset=0;//add to this if the lidar module is inset into the robot. This will be exact distance from shooter to goal
	private Lidar shootLidar;
	public static final double kMaxDist = 7;//value to change once added
	public static final double kDistSlow = 6;//ranging shooterSpeed
	public static final double kMinDist = 4;//smallest distance at which it will slow

	public static final double kP = 1.0 / 4.0, kI = 0, kD = 0;
	PIDOutput output;
	PIDSource source;
	
	SpeedMonitor speedControlThread;
	double setpointSpeed;
	
	private boolean useLidar = true;

	public RangeFinderSubsystem() {
		super("ShootDist",kP, kI, kD);
		setpointSpeed=0;
		speedControlThread = new SpeedMonitor();
		speedControlThread.setName("D.SpeedControl");
		speedControlThread.start();
		
		this.setAbsoluteTolerance(1);
    	this.setOutputRange(0, 1);
	}
	
//	public double getCurrent(boolean motor) {
//		if (Motor Running) {
//			return Motor.getOutputCurrent();
//		} else {
//			return Motor.getOutputCurrent();
//		}
//	}
	
	public double getLiftDistInches() {
		return shootLidar.getDistanceIn() + offset;
	}  
	
	public double getRawLiftSpeed(){
		return this.setpointSpeed;
	}
	
	
	public boolean isLidarRunning()
	{
		return useLidar;
	}

	public boolean TooClose() {
		if (shootLidar.getDistanceIn()<kMinDist)
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
	
	private void setSpeedInternal(double input) {
		double output = input;
		if (useLidar && this.getLiftDistInches() > kMinDist && output > 0) {
		//	output = output / 2;
		}
		if(Robot.shootSub.isShooterToggled())
		{
		Robot.shootSub.setShootSpeed(output);
		}
		else
		{
		Robot.shootSub.setShootSpeed(0);
		}
	}

	@Override
	protected double returnPIDInput() {
		return this.getLiftDistInches();
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
