package org.discobots.steamworks.commands.intake;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 * runs the ball intake
 */
public class IntakeCommand extends Command {
	private double speed;
	boolean toggled = false;
	long endtime;

    public IntakeCommand(double speed) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.intakeSub);
    	this.speed=speed;
    }
    public IntakeCommand(double speed, boolean toggled, long timeMillis) {
    	requires(Robot.intakeSub);
    	this.speed=speed;
    	this.toggled=toggled;
    	endtime=timeMillis;
    }



	// Called just before this Command runs the first time
    protected void initialize() {
    	if (toggled=true)
    	{
        	endtime+=System.currentTimeMillis();
    	}
    	else{
    		Robot.intakeSub.setSpeed(speed);
    	}
    } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intakeSub.setSpeed(speed);
    	if(endtime<System.currentTimeMillis()&&toggled==true)
    	{
    		toggled=!toggled;//ends when time limit up
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return toggled;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intakeSub.setSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
