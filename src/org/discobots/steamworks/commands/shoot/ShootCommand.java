package org.discobots.steamworks.commands.shoot;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the shooter
 */
public class ShootCommand extends Command {
boolean shooterToggled=false;
double speed =1;
long endtime;
    public ShootCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.shootSub);
    }
    public ShootCommand(boolean toggleShooter)
    {
    shooterToggled=toggleShooter;			
    }
    public ShootCommand(boolean toggleShooter, double speed)
    {
    shooterToggled=toggleShooter;			
    this.speed=speed;
    }
    public ShootCommand(double speed)
    {
    this.speed = speed;	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	endtime = System.currentTimeMillis()+10000;//ten second limit

    } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shootSub.setSpeed(speed);
    	if (System.currentTimeMillis()>endtime)
    		shooterToggled=false;

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !shooterToggled;
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
