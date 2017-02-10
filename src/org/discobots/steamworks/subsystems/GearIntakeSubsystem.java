package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearIntakeSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Spark intakeGearMotor;
	private Solenoid gearSolenoid;
	public GearIntakeSubsystem(){
		this.gearSolenoid = new Solenoid(HW.gearSolenoid);
		this.intakeGearMotor=new Spark(HW.motorGearIntake);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void setSpeed(double speed){
	intakeGearMotor.setSpeed(speed);
	}
	public void set(boolean val)
	{
		gearSolenoid.set(val);
	}
	public boolean get(){
		return gearSolenoid.get();
	}
}
