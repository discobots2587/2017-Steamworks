package org.discobots.steamworks.commands.auton.subcommands;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Runs the shooter
 */
public class AutonomousShootCommand extends Command {
boolean shooterToggled=false;
double speed =0;
long endTime;
int time;
    public AutonomousShootCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.shootSub);
    }
    public AutonomousShootCommand(int time)
    {    	requires(Robot.shootSub);
    	speed=-1;
    	
    	this.time=time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	endTime= System.currentTimeMillis()+time;
    	} 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
      	
 
        Robot.blendSub.setBlend(speed);

        Robot.shootSub.setShootSpeed((speed));
    	
    }

    protected boolean isFinished() {
    	return endTime<=System.currentTimeMillis();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shootSub.setShootSpeed(0.0);
    	Robot.blendSub.setBlend(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
