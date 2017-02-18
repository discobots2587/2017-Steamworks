package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Talon shootMotor;
	private boolean shooterToggled=false;
	private double pwm;
	public ShooterSubsystem(){
		this.shootMotor=new Talon(HW.motorShoot);
		pwm=0;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	public void setShootSpeed(double speed){
	pwm=speed;
	shootMotor.set(speed);
	}


	public boolean isShooterToggled() {
		return shooterToggled;
	}

	public void setShooterToggled(boolean shooterToggled) {
		this.shooterToggled = shooterToggled;
	}
	
	public double getPWM(){
		return pwm*100;
	}

}
