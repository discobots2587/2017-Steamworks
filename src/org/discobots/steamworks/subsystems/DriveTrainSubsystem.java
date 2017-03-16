
package org.discobots.steamworks.subsystems;

import java.lang.Math;

import org.discobots.steamworks.HW;
import org.discobots.steamworks.commands.drive.SplitArcadeDriveCommand;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrainSubsystem extends Subsystem {
private double kSpeedScaling=.75;
public RobotDrive robotDrive;
public int gearCount=0;
Solenoid shifter;
private double autonKonstant;
Spark frontLeft;
Spark frontRight;
//Spark backLeft;
//Spark backRight;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public DriveTrainSubsystem(){
		 frontLeft = new Spark(HW.motorFrontLeft);
		 frontRight = new Spark(HW.motorFrontRight);

		robotDrive=new RobotDrive(frontLeft,
								  frontRight);
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
				robotDrive.arcadeDrive(-x, -y);// robotdrive is dumb arcadeDrive so params are switched
	}
	public void tankDrive(double leftStick, double rightStick) {//.2 used to determine if turning
		robotDrive.tankDrive(-leftStick, rightStick);
	}
	public void customTank(double L, double R){
		frontLeft.setSpeed(L*kSpeedScaling);
		//backLeft.setSpeed(L*kSpeedScaling);
		frontRight.setSpeed(R*kSpeedScaling);
		//backRight.setSpeed(R*kSpeedScaling);
	}
	public void frontTest(double L, double R){
		frontLeft.setSpeed(L*kSpeedScaling);
		frontRight.setSpeed(R*kSpeedScaling);
	}
	public void backTest(double L, double R){
		//backLeft.setSpeed(L*kSpeedScaling);
		//backRight.setSpeed(R*kSpeedScaling);
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
	public void setAutonKonstant(double autonKonstant)
	{
		this.autonKonstant=autonKonstant;
	}
	public double getAutonKonstant()
	{
		return autonKonstant;
	}
	public String getGear(){
		if(getLRShifter())
			return "High Gear";
		else if(getLRShifter())
			return "Low Gear";
		else
			return "Unknown Gear";
	}
}
