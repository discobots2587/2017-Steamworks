package org.discobots.steamworks.commands.shoot;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the shooter
 */
public class ShootCommand extends Command {
boolean shooterToggled=false;
double speed =0;
long endtime;
    public ShootCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.shootSub);
    }
    public ShootCommand(boolean toggleShooter)
    {
    	shooterToggled=true;
    	speed=-1;
    }
    public ShootCommand(boolean toggleShooter, double speed)
    {
    shooterToggled=true;
    this.speed=-speed;
    }
    public ShootCommand(double speed)
    {
    this.speed = -speed;	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (shooterToggled)
    	    Robot.shootSub.setShooterToggled(!Robot.shootSub.isShooterToggled());			    		
    } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shootSub.setShootSpeed(speed);
    }

    protected boolean isFinished() {
    	if(shooterToggled)
    	return !Robot.shootSub.isShooterToggled();
    	else 
    		return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shootSub.setShooterToggled(false);
    	Robot.shootSub.setShootSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
