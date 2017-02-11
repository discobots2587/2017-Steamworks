package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private CANTalon shootMotor;
	private CANTalon motorBlend;
	public final double kSpeed =1.0;
	public ShooterSubsystem(){
		this.shootMotor=new CANTalon(HW.motorShoot);
		this.motorBlend = new CANTalon(HW.motorBlend);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	public void setSpeed(double speed){
	shootMotor.set(speed*kSpeed);
	}
	public void setBlend(double speed){
	motorBlend.set(speed*kSpeed);
	}
}
