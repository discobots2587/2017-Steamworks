package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;
import org.discobots.steamworks.commands.drive.SplitArcadeDriveCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrainSubsystem extends Subsystem {
private double kSpeedScaling=.75;
public RobotDrive robotDrive;
DoubleSolenoid shifter;
Solenoid shifterRight;
Solenoid shifterLeft;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public DriveTrainSubsystem(){
		robotDrive=new RobotDrive(HW.motorFrontLeft,HW.motorBackLeft,
								  HW.motorFrontRight,HW.motorBackRight);
		//robotDrive.setSafetyEnabled(true);
		shifter = new DoubleSolenoid(HW.shifterRight,HW.shifterLeft);
		shifterRight = new Solenoid(HW.shifterRight);
		shifterLeft = new Solenoid(HW.shifterLeft);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new SplitArcadeDriveCommand());
    }

	public void setSpeedScaling(double speedScale) {
		this.kSpeedScaling=speedScale;
	}
	public double getSpeedScaling(){
		return kSpeedScaling;
}
	public void arcadeDrive(double y, double x) {

		robotDrive.arcadeDrive(x * kSpeedScaling, y * kSpeedScaling);
		// robotdrive is dumb arcadeDrive so params are switched
	}
	public void tankDrive(double leftStick, double rightStick) {

		robotDrive.tankDrive(leftStick * kSpeedScaling, -rightStick
				* kSpeedScaling);
	}
	public void setShifter(Value val)
	{
		shifter.set(val);
	}
	public Value getShifter(){
		return shifter.get();
	}
	public void setLRShifter(boolean val){
		shifterRight.set(val);
		shifterLeft.set(val);
	}
}

