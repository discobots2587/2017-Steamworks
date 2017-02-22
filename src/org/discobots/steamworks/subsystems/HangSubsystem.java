package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class HangSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Spark hangMotor;
	private double pwm;
	public HangSubsystem(){
		this.hangMotor=new Spark(HW.motorHang);
		pwm=0.0;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	public void setSpeed(double speed){
	pwm=speed;
	hangMotor.setSpeed(speed);
	}
	public double getPWM(){
		return pwm*100;
	}
}
