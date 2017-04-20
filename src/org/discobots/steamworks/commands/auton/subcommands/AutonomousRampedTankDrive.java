package org.discobots.steamworks.commands.auton.subcommands;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousRampedTankDrive extends Command {
boolean turnRamp=false;
boolean straightRamp=false;
long endTime;
double speedScale;
double left;
double right;
    public AutonomousRampedTankDrive(double left, double right, long timeMili, boolean straightRamp, boolean turnRamp) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	endTime=System.currentTimeMillis()+timeMili;
    	this.left=left;
    	this.right=right;
    	this.turnRamp=turnRamp;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        		if(!Robot.turnScale&&!Robot.directScale)
            	Robot.driveTrainSub.tankDrive(left,-right);
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

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
