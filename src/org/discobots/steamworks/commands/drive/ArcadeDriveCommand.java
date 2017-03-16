package org.discobots.steamworks.commands.drive;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArcadeDriveCommand extends Command {

    public ArcadeDriveCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrainSub);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.driveTrainSub.arcadeDrive(Robot.oi.getRawAnalogStickALX(),Robot.oi.getRawAnalogStickALY());
    	if(Robot.testing)
    	Robot.driveTrainSub.frontTest(-Robot.oi.getRawAnalogStickALY(), Robot.oi.getRawAnalogStickARY());
    	else{
    		if(!Robot.directScale&&!Robot.turnScale)
        		Robot.driveTrainSub.arcadeDrive(Robot.oi.getRawAnalogStickALX(),-Robot.oi.getRawAnalogStickALY());
    		if(!Robot.directScale&&Robot.turnScale)
    		Robot.driveTrainSub.arcadeDrive(Robot.oi.getRawAnalogStickALX()*Robot.driveTrainSub.getSpeedScaling(),-Robot.oi.getRawAnalogStickALY());
    		if(Robot.directScale&&!Robot.turnScale)
        		Robot.driveTrainSub.arcadeDrive(Robot.oi.getRawAnalogStickALX(),Robot.oi.getRawAnalogStickALY()*Robot.driveTrainSub.getSpeedScaling());
    		if(Robot.directScale&&Robot.turnScale)
        		Robot.driveTrainSub.arcadeDrive(Robot.oi.getRawAnalogStickALX()*Robot.driveTrainSub.getSpeedScaling(),Robot.oi.getRawAnalogStickALY()*Robot.driveTrainSub.getSpeedScaling());
    	}

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrainSub.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
