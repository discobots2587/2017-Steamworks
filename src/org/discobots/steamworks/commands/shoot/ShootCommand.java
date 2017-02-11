package org.discobots.steamworks.commands.shoot;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShootCommand extends Command {

    public ShootCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.shootSub);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shootSub.setSpeed(1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shootSub.setSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
