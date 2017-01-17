package org.discobots.steamworks.commands.drive;

import org.discobots.steamworks.Dashboard;
import org.discobots.steamworks.Robot;
import org.discobots.steamworks.commands.drive.ArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.SplitArcadeDriveCommand;
//import org.discobots.steamworks.commands.drive.TankDriveCommand;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CycleDriveCommand extends Command {
	Command cmd;
	public CycleDriveCommand(){
		Command cmd = Robot.driveTrainSub.getCurrentCommand();	
	}
	
	public CycleDriveCommand(Command cmd) {
		
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	/*	Command driveCmd = Robot.driveTrainSub.getCurrentCommand();//swap commands with a single button
		if (driveCmd instanceof SplitArcadeDriveCommand) {
			new TankDriveCommand().start();
		} else if (driveCmd instanceof TankDriveCommand) {
			new ArcadeDriveCommand().start();
		} 
		else
		{
			new SplitArcadeDriveCommand().start();
		}
	*/
		//swap commands with a single button

		if (cmd instanceof SplitArcadeDriveCommand) {
			new ArcadeDriveCommand().start();
			 SmartDashboard.putString("Current Drive: ", "Arcade Drive");	
		}
		else
		{
			new SplitArcadeDriveCommand().start();
			 SmartDashboard.putString("Current Drive: ", "Split Arcade Drive");	

		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		end();
	}//do nothing

	// Make this return true when this Command no longer needs to run execute()
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
