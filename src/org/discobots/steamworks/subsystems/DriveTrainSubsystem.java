
package org.discobots.steamworks.subsystems;

import java.lang.Math;

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
public int gearCount=0;

Solenoid shifter;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public DriveTrainSubsystem(){
		robotDrive=new RobotDrive(HW.motorFrontLeft,HW.motorBackLeft,
								  HW.motorFrontRight,HW.motorBackRight);
		//robotDrive.setSafetyEnabled(true);
		shifter = new Solenoid(HW.shifter);
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
	/*
	 * the relationship between the input from joystick and output to the motors is exponential
	 * instead of linear. Zero input will result in zero out put. .1 to .8 results in an output of
	 * about .3. 1.0 input results in 1.0 output. 
	 */
	public void arcadeExpDrive(double y,double x){
		double moveValue= 0.0;
		if(y!=0.0){
			moveValue=Math.exp(15.0*(y-1.02)+.3);
		}
		double rotateValue=0.0;
		if(x!=0.0)		
			rotateValue=Math.exp(15.0*(x-1.02)+.3);
		robotDrive.arcadeDrive(moveValue, rotateValue);
	}

	public void setLRShifter(boolean val){
		shifter.set(val);
	}
	public boolean getLRShifter(){
		return shifter.get();
	}
}
