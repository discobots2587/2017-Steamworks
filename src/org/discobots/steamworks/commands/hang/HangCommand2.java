package org.discobots.steamworks.commands.hang;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HangCommand2 extends Command {
double speed;
boolean toggled=false;

    public HangCommand2(double speed) {
        requires(Robot.hangSub);
        this.speed=speed;
        toggled=true;

     }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (toggled)
    		Robot.hangSub.setToggled(!Robot.hangSub.getToggled());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(toggled && Robot.hangSub.getToggled())
    	Robot.hangSub.setSpeed(speed);
    	if(toggled && !Robot.hangSub.getToggled())
        	Robot.hangSub.setSpeed(0);
    	if(!toggled)
    	{
    		Robot.hangSub.setSpeed(speed);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return(!toggled&&!Robot.hangSub.getToggled());
    }

    // Called once after isFinished returns true
    protected void end(){
        Robot.hangSub.setSpeed(0);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
