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
public class ShootDistSubsystem extends PIDSubsystem {
	private int offset=0;//add to this if the lidar module is inset into the robot. This will be exact distance from shooter to goal
	private Lidar shootLidar;
	public static final double kMaxDist = 7;//value to change once added
	public static final double kDistSlow = 6;//ranging shooterSpeed
	public static final double kMinDist = 4;//smallest distance at which it will slow
	double g = 9.81; // gravity
	double y = 0; // target y--Height difference from where ball has no force acting on it from robot
	double o = 45; // launch angle --- WILL NEED TO FIGURE THIS OUT exactly -- spin affects the angle only when ball leaves robot i believe
	public static final double kP = 1.0 / 4.0, kI = 0, kD = 0;
	PIDOutput output;
	PIDSource source;
	public boolean useLidar = false;
	
	SpeedMonitor speedControlThread;
	double setpointSpeed;
	
	public ShootDistSubsystem() {
		super("ShootDist",kP, kI, kD);
		if(useLidar){
		setpointSpeed=0;
		speedControlThread = new SpeedMonitor();
		speedControlThread.setName("D.SpeedControl");
		speedControlThread.start();
		
		this.setAbsoluteTolerance(1);
    	this.setOutputRange(0, 1);
		}
	}
	
//	public double getCurrent(boolean motor) {
//		if (Motor Running) {
//			return Motor.getOutputCurrent();
//		} else {
//			return Motor.getOutputCurrent();
//		}
//	}
	
	public double getShooterDistInches() {
		if(useLidar){
		return shootLidar.getDistanceIn() + offset/2.54;
		}
		else return 0;
	}  
	
	public double getShooterDistCM() {
		if(useLidar){
		return shootLidar.getDistanceCm();
		}
		else return 0;
	}
	
	public double getRawShootSpeed(){
		if(useLidar){
		return this.setpointSpeed;}
		else return 0;
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
	
	public double CalculatedNeededBallVelo(double distCM){
		if(useLidar)
		{
		double v = (Math.sqrt(g) * Math.sqrt(distCM) * Math.sqrt((Math.tan(o)*Math.tan(o))+1)) / Math.sqrt(2 * Math.tan(o) - (2 * g * y) / distCM); // velocity needed of ball
		return v;
		//http://gamedev.stackexchange.com/questions/17467/calculating-velocity-needed-to-hit-target-in-parabolic-arc
		}
		else
			return 0;
	}
	
	private void setSpeedInternal(double input) {//may need to change to play nice with the shooter subystem....
		double output = input;
		if (useLidar && this.getShooterDistInches() > kMinDist && output > 0) {//may change to Centimeters but inches might be easier for min/max distances
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
		return this.getShooterDistCM();
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
