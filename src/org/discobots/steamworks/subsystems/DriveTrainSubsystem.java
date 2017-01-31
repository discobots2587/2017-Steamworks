package org.discobots.steamworks.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrainSubsystem extends Subsystem {
private double speedScale;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	public void setSpeedScaling(double speedScale) {
		this.speedScale=speedScale;
	}
	public double getSpeedScaling(){
		return speedScale;
}

}

