package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Spark shootMotor;
	private boolean shooterToggled=false;
	private double pwm;
	private double setPoint;
	public ShooterSubsystem(){
		this.shootMotor=new Spark(HW.motorShoot);
		pwm=0;
		setPoint=.75;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	public void setShootSpeed(double speed){
	if(speed<=setPoint){
		pwm=speed;
		shootMotor.set(speed);}
	else if(speed>setPoint){
		pwm=setPoint;
		shootMotor.set(speed);
	}
	}


	public boolean isShooterToggled() {
		return shooterToggled;
	}

	public void setSetpoint(double set){
		setPoint=set;
	}
	
	public void setShooterToggled(boolean shooterToggled) {
		this.shooterToggled = shooterToggled;
	}
	
	public double getSetpoint(){
		return setPoint;
	}
	
	public double getPWM(){
		return pwm*100;
	}

}
