package org.discobots.steamworks.commands.intake;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 * runs the ball intake
 */
public class IntakeCommand extends Command {
	private double speed;

    public IntakeCommand(double speed) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.intakeSub);
    	this.speed=speed;
    }


	// Called just before this Command runs the first time
    protected void initialize() {
    	
    } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intakeSub.setSpeed(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
