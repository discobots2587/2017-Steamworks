package org.discobots.steamworks.commands.intake;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the motor on the gear intake
 */
public class GearIntakeCommand extends Command {
	private double speed;

    public GearIntakeCommand(double speed) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.gearSub);
    	this.speed=speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gearSub.setSpeed(speed);//sets gear speed
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gearSub.setSpeed(0.0);//turn off gearintake
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
