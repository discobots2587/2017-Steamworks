package org.discobots.steamworks.commands.shoot;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExtendHoodCommand extends Command {
	double endT;
    public ExtendHoodCommand(double time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		endT= System.currentTimeMillis()+time;
    	requires(Robot.shootSub);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.shootSub.setShootHood(1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (endT<=System.currentTimeMillis());
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shootSub.setShootHood(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shootSub.setShootHood(0);
    	end();
    }
}
