package org.discobots.steamworks.commands.drive;

import org.discobots.steamworks.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpeedShiftCommand extends Command {

	private boolean fin=true;
	private double speed;

    public SpeedShiftCommand(double speed) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.driveTrainSub);
    	this.speed=speed;

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrainSub.setSpeedScaling(speed);
    } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return fin;
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
