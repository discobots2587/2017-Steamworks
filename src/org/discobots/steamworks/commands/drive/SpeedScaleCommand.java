package org.discobots.steamworks.commands.drive;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpeedScaleCommand extends Command {
	private double speedScale;
	private boolean isFast=true;
    public SpeedScaleCommand(boolean turning) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
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
    public SpeedScaleCommand(double speedScale)
    {	
    	this.speedScale=speedScale;
    	//take input value so can be set to driver's preferences
    }
    public SpeedScaleCommand(){
    	if(isFast){
    		speedScale=.5;
    		isFast=!isFast;}
    	else if(!isFast){
    		speedScale=1.0;
    		isFast=!isFast;
    	}
    	else{
    		speedScale=0.0;
    		isFast=false;		
    	}
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrainSub.setSpeedScaling(speedScale);
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
    }
}
