package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;
import org.discobots.steamworks.utils.LTRTXBOX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Talon intakeMotor;
	private double pwm;
	public IntakeSubsystem(){
		this.intakeMotor=new Talon(HW.motorIntake);
		pwm=0.0;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new LTRTXBOX());
	}
	
	public void setSpeed(double speed){
	intakeMotor.setSpeed(speed);
	pwm=speed;
	}
	public double getPWM(){
		return pwm*100;
	}
}
