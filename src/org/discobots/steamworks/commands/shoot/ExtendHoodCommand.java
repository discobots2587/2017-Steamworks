package org.discobots.steamworks.commands.shoot;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExtendHoodCommand extends Command {
	Long endT;
	int time;
    public ExtendHoodCommand(int time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shootSub);
    	this.time=time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		endT= (long) (System.currentTimeMillis()+time);
    	Robot.shootSub.setShootHood(1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shootSub.setShootHood(.5);
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
    	end();
    }
}
