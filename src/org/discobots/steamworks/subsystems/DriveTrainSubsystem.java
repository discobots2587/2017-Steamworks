package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrainSubsystem extends Subsystem {
private double kSpeedScaling;
private RobotDrive robotDrive;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public DriveTrainSubsystem(){
		robotDrive=new RobotDrive(HW.motorFrontLeft,HW.motorBackLeft,
								  HW.motorFrontRight,HW.motorBackRight);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
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
	

}

