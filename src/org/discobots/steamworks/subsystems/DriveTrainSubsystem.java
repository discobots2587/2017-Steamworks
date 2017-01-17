
package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;
import org.discobots.steamworks.Robot;
import org.discobots.steamworks.commands.drive.ArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.SplitArcadeDriveCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */


public class DriveTrainSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	/* Motors */


	public RobotDrive robotDrive;

	static final double CONSTANT_RAMP_LIMIT = 0.1; // ramping  -- an alternative speed limit from 2016
	// 0.05 = 4/10 seconds to full, 0.1 = 2/10 seconds to full
	boolean allowRamped = false;
	private double prevLeft = 0, prevRight = 0;
	private double prevY = 0, prevX = 0;
	public enum DriveCommandChoice { TANK, ARCADE, SPLITARCADE }
	DriveCommandChoice choice;
	CANTalon frontLeft,frontRight,backLeft,backRight;
	public double buttonSpeed;
	public int autonTimeTest;

	static double kSpeedScaling = 1.0;

	
	public DriveTrainSubsystem() {
		//choice = DriveCommandChoice.TANK; //default tank drive if no choice chosen (prevents null pointer exception)
		frontLeft = new CANTalon(HW.motorFrontLeft);
		frontRight = new CANTalon(HW.motorFrontRight);
		backLeft = new CANTalon(HW.motorBackLeft);
		backRight = new CANTalon(HW.motorBackRight);
		/* RobotDrive*/
		robotDrive = new RobotDrive(frontLeft,backLeft,frontRight,backRight);//motor order matters
		buttonSpeed = .5;
		autonTimeTest = 1000;
		
	}
	

	
	public double getFrontRight()
	{
		return frontRight.get();
	}

	
	public void setRamped(boolean a) {
		this.allowRamped = a;
	}

	public boolean getRamped() {
		return this.allowRamped;
	}

	public void tankDriveRamp(double leftStick, double rightStick) {
		if (!allowRamped) {
			tankDriveUnramped(leftStick, rightStick);
			return;
		}

		double left = leftStick, right = -rightStick;

		if (left - prevLeft > CONSTANT_RAMP_LIMIT) {
			left = prevLeft + CONSTANT_RAMP_LIMIT;
		} else if (prevLeft - left > CONSTANT_RAMP_LIMIT) {
			left = prevLeft - CONSTANT_RAMP_LIMIT;
		}

		if (right - prevRight > CONSTANT_RAMP_LIMIT) {
			right = prevRight + CONSTANT_RAMP_LIMIT;
		} else if (prevRight - right > CONSTANT_RAMP_LIMIT) {
			right = prevRight - CONSTANT_RAMP_LIMIT;
		}

		prevLeft = left;
		prevRight = right;

		robotDrive.tankDrive(left * kSpeedScaling, right * kSpeedScaling);
	}

	public void arcadeDriveRamp(double iy, double ix) {
		if (!allowRamped) {
			arcadeDriveUnramped(iy, ix);
			return;
		}
		double ox = ix, oy = -iy;

		if (oy - prevY > CONSTANT_RAMP_LIMIT) {
			oy = prevY + CONSTANT_RAMP_LIMIT;
		} else if (prevY - oy > CONSTANT_RAMP_LIMIT) {
			oy = prevY - CONSTANT_RAMP_LIMIT;
		}

		if (ox - prevX > CONSTANT_RAMP_LIMIT) {
			ox = prevX + CONSTANT_RAMP_LIMIT;
		} else if (prevX - ox > CONSTANT_RAMP_LIMIT) {
			ox = prevX - CONSTANT_RAMP_LIMIT;
		}

		prevX = ox;
		prevY = oy;
		robotDrive.arcadeDrive(ox * kSpeedScaling, oy * kSpeedScaling);
		// RobotDrive is dumb arcadeDrive so parameters are switched
	}



	public void tankDriveUnramped(double leftStick, double rightStick) {
		prevLeft = 0;
		prevRight = 0;
		prevX = 0;
		prevY = 0;
		robotDrive.tankDrive(leftStick * kSpeedScaling, -rightStick
				* kSpeedScaling);
	}

	public void arcadeDriveUnramped(double y, double x) {
		prevLeft = 0;
		prevRight = 0;
		prevX = 0;
		prevY = 0;
		robotDrive.arcadeDrive(x * kSpeedScaling, -y * kSpeedScaling);
		// robotdrive is dumb arcadeDrive so params are switched
	}

	public void initDefaultCommand() {//automatically set to run splitarcade drive command
		setDefaultCommand(new SplitArcadeDriveCommand());
	}

	public double getSpeedScaling() {
		return kSpeedScaling;
	}

	public void setSpeedScaling(double a) {
		kSpeedScaling = a;
	}
}

