package org.discobots.steamworks.commands.utils;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SensorToggle extends Command {

    public SensorToggle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrainSub);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.armSub.sensorToggle = !Robot.armSub.sensorToggle;
    	if(Robot.electricSub.sensorToggle==0)
    		Robot.electricSub.sensorToggle=1;
    	else if(Robot.electricSub.sensorToggle==1)
    		Robot.electricSub.sensorToggle=2;//some sensors turned off
    	else
    		Robot.electricSub.sensorToggle=0;
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
