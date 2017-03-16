package org.discobots.steamworks.commands.drive;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpeedScaleCommand extends Command {
	boolean turning;
    public SpeedScaleCommand(boolean turning) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrainSub);
    	this.turning=turning;
    	
    }
    public SpeedScaleCommand(double speedScale)
    {
    	Robot.driveTrainSub.setSpeedScaling(speedScale);//take input value so can be set to driver's preferences
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(turning)
    	{
    		System.out.println("Setting TurnScale");
    		Robot.turnScale=!Robot.turnScale;
    		System.out.println("TurnScale" + Robot.turnScale);
    	}
    	if(!turning)
    	{
    		Robot.directScale=!Robot.directScale;
    	}
    	Robot.oi.StartThreads();    	
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
    }
}
