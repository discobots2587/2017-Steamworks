package org.discobots.steamworks.commands.intake;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeCommand extends Command {

    public IntakeCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.intakeSub);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intakeSub.setSpeed(0.75);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
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
