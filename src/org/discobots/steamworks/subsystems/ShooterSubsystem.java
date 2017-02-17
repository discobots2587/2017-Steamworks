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
	private Talon motorBlend;
	private boolean blendToggle=false;
	private boolean shooterToggled=false;
	public final double kSpeed =1.0;
	public ShooterSubsystem(){
		this.shootMotor=new Talon(HW.motorShoot);
		this.motorBlend = new Talon(HW.motorBlend);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	public void setShootSpeed(double speed){
	shootMotor.set(speed*kSpeed);
	}
	public void setBlend(double speed){
	motorBlend.set(speed*kSpeed);
	}

	public boolean isShooterToggled() {
		return shooterToggled;
	}

	public void setShooterToggled(boolean shooterToggled) {
		this.shooterToggled = shooterToggled;
	}

	public boolean isBlendToggle() {
		return blendToggle;
	}

	public void setBlendToggle(boolean blendToggle) {
		this.blendToggle = blendToggle;
	}
}
