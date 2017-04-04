package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Talon shootsMotor;
	private boolean shooterToggled=false;
	private Spark shootHood;
	public ShooterSubsystem(){
		shootHood=new Spark(HW.motorHood);
		shootsMotor=new Talon(HW.motorShoot);
		/*new Thread(){
			public void run(){
				while(true)
				{
				if(isShooterToggled())
				{
					setShootSpeed(1);
				}
			}
			}
		}.start();*/
	}
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	
	public boolean isShooterToggled() {
		return shooterToggled;
	}


	public void setShootSpeed(double speed)
	{
		shootsMotor.set(-speed);
	}
	
	public void setShooterToggled(boolean shooterToggled) {
		this.shooterToggled = shooterToggled;
	}


	public double getShootHoodSpd() {
		return shootHood.get();
	}


	public void setShootHood(double speed) {
		shootHood.set(speed);
	}

}
