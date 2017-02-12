package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Spark intakeMotor;
	public IntakeSubsystem(){
		this.intakeMotor=new Spark(HW.motorIntake);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void setSpeed(double speed){
	intakeMotor.setSpeed(speed);
	}
}
