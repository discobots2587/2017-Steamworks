
package org.discobots.steamworks.subsystems;

import java.lang.Math;

import org.discobots.steamworks.HW;
import org.discobots.steamworks.commands.drive.SplitArcadeDriveCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
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
private double autonKonstant;
private Spark l1;
private Spark l2;
private Spark r1;
private Spark r2;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public DriveTrainSubsystem(){
		if (!DriverStation.getInstance().isTest())
		{
		robotDrive=new RobotDrive(HW.motorFrontLeft,HW.motorBackLeft,
								  HW.motorFrontRight,HW.motorBackRight);
		}
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
	public void customTank(double L, double R){
		l1 = new Spark(HW.motorFrontLeft);
		l2 = new Spark(HW.motorBackLeft);
		r1 = new Spark(HW.motorFrontRight);
		r2 = new Spark(HW.motorBackRight);

		r1.setSpeed(R);
		r2.setSpeed(R);
		l1.setSpeed(L);
		l2.setSpeed(L);
		
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
	public void setAutonKonstant(double autonKonstant)
	{
		this.autonKonstant=autonKonstant;
	}
	public double getAutonKonstant()
	{
		return autonKonstant;
	}
}
