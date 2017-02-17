package org.discobots.steamworks.commands.shoot;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the blender
 */
public class BlendCommand extends Command {
	private double speed;
	private boolean toggle=false;
    public BlendCommand(double s) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.shootSub);
    	speed = s;
    }   
    public BlendCommand(double s,boolean toggle) {//allows toggling of blend command
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.shootSub);
    	speed = s;
    	this.toggle=toggle;
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	
    } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shootSub.setBlend(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return toggle;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shootSub.setBlend(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
