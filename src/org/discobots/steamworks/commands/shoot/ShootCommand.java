package org.discobots.steamworks.commands.shoot;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Runs the shooter
 */
public class ShootCommand extends Command {
double speed =1;
long endtime;
    public ShootCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.shootSub);
    }
    public ShootCommand(double speed) {
        // Use requires() here to declare subsystem dependencies
    	this.speed=speed;
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	    Robot.shootSub.setShooterToggled(!Robot.shootSub.isShooterToggled());	
    	SmartDashboard.putBoolean("ShooterToggledCommand", Robot.shootSub.isShooterToggled());
    	} 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
      	if (Robot.shootSub.isShooterToggled())
        	Robot.shootSub.setShootSpeed(speed);
          	if (!Robot.shootSub.isShooterToggled())
            	Robot.shootSub.setShootSpeed((0));
    }

    protected boolean isFinished() {
    		return true;
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
