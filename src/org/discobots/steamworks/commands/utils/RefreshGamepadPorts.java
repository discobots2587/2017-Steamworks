package org.discobots.steamworks.commands.utils;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RefreshGamepadPorts extends Command {

    public RefreshGamepadPorts() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.oi.updateControllerList();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	end();
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
