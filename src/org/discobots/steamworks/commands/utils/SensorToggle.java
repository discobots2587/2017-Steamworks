package org.discobots.steamworks.commands.utils;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SensorToggle extends Command {
int temp;
    public SensorToggle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);\
    	temp=-1;
    }
    public SensorToggle(int temp) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.temp=temp;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.armSub.sensorToggle = !Robot.armSub.sensorToggle;
    	Robot.electricSub.disableSensors(temp);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
