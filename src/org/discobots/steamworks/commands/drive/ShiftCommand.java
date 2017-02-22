package org.discobots.steamworks.commands.drive;

import org.discobots.steamworks.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * shift through different combinations of speedscaling and gears
 */
public class ShiftCommand extends Command {

	private boolean fin=true;
	public boolean end=false;
	private boolean direction = false;

    public ShiftCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.driveTrainSub);
    	this.direction = Robot.driveTrainSub.getLRShifter();

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(direction)
    		Robot.driveTrainSub.setLRShifter(false);
    	else if(!direction){
    		Robot.driveTrainSub.setLRShifter(true);
    	}
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
