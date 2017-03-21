package org.discobots.steamworks.commands.drive;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TankDriveCommand extends Command {

    private double speedScale;

	public TankDriveCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrainSub);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.testing){
    		if(!Robot.turnScale&&!Robot.directScale)
        	Robot.driveTrainSub.tankDrive(Robot.oi.getRawAnalogStickALY(),-Robot.oi.getRawAnalogStickARY());
    		if(Robot.turnScale&&!Robot.directScale){
    			speedScale = Math.pow(Robot.driveTrainSub.getSpeedScaling(),1-(Math.abs((Robot.oi.getRawAnalogStickALY())+(Robot.oi.getRawAnalogStickARY())/2)));
            	Robot.driveTrainSub.tankDrive(Robot.oi.getRawAnalogStickARY()*speedScale,-Robot.oi.getRawAnalogStickALY()*speedScale);
    		}
    		if(!Robot.turnScale&&Robot.directScale)
    		{
    			speedScale = Math.pow(Robot.driveTrainSub.getSpeedScaling(),(Math.abs((Robot.oi.getRawAnalogStickALY())+(Robot.oi.getRawAnalogStickARY())/2)));
            	Robot.driveTrainSub.tankDrive(Robot.oi.getRawAnalogStickARY()*speedScale,-Robot.oi.getRawAnalogStickALY()*speedScale);
    		}
    		if(Robot.turnScale&&Robot.directScale)
            	Robot.driveTrainSub.tankDrive(Robot.oi.getRawAnalogStickARY()*Robot.driveTrainSub.getSpeedScaling(),-Robot.oi.getRawAnalogStickALY()*Robot.driveTrainSub.getSpeedScaling());
    		}
    	else
    	{
    		Robot.driveTrainSub.customTank(Robot.oi.getRawAnalogStickALY(), -Robot.oi.getRawAnalogStickARY());
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrainSub.tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
