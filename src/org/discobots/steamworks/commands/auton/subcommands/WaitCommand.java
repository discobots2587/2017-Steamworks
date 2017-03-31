package org.discobots.steamworks.commands.auton.subcommands;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WaitCommand extends Command {
long endTime;
    public WaitCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    public WaitCommand(int time)
    {
    	//long multi = (long)(100*Robot.driveTrainSub.getAutonKonstant());
    	long multi=100;
    	endTime = System.currentTimeMillis() + time*multi/100;
    	requires(Robot.driveTrainSub);
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis()>=endTime;
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