package org.discobots.steamworks.commands.drive;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HalfSpeedCommand extends Command {
	/*private int time;
	private long endTime;
	private double speed;
	private boolean fin=false;
	public boolean end=false;*/

    public HalfSpeedCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.driveTrainSub);

    }

    // Called just before this Command runs the first time
    protected void initialize() {

    	Robot.driveTrainSub.setSpeedScaling(0.5);
    } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
