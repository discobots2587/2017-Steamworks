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
double speedScaleTurn;
double left;
double right;
long startTime;
double scaledLeft;
double scaledRight;
long midTime;
long lengthLim;
    public AutonomousRampedTankDrive(double left, double right, long timeMili, boolean straightRamp, boolean turnRamp) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	endTime=System.currentTimeMillis()+timeMili;
    	startTime=System.currentTimeMillis();
    	this.left=left;
    	this.right=right;
    	this.turnRamp=turnRamp;
    	midTime=System.currentTimeMillis()+(timeMili/2);
    	lengthLim=timeMili;
 	
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	 if(System.currentTimeMillis()<midTime){
    		 scaledLeft = (1-(endTime-System.currentTimeMillis())/lengthLim)*left;
    	 	 scaledRight = (1-(endTime-System.currentTimeMillis())/lengthLim)*right;
    	 	 }
    	 else
    	 {
    		 scaledLeft = left;
    		 scaledRight = right;
    	 }

    	 
        		if(!turnRamp&&!straightRamp)
            	Robot.driveTrainSub.tankDrive(scaledLeft,-scaledRight);
        		if(turnRamp&&!straightRamp){
        			speedScale = Math.pow(Robot.driveTrainSub.getSpeedScaling(),1-(Math.abs((scaledLeft)+(scaledRight)/2)));
                	Robot.driveTrainSub.tankDrive(Robot.oi.getRawAnalogStickARY()*speedScale,-scaledLeft*speedScale);
        		}
        		if(!turnRamp&&straightRamp)
        		{
        			speedScale = Math.pow(Robot.driveTrainSub.getSpeedScaling(),(Math.abs((scaledLeft)+(Robot.oi.getRawAnalogStickARY())/2)));
                	Robot.driveTrainSub.tankDrive(Robot.oi.getRawAnalogStickARY()*speedScale,-scaledLeft*speedScale);
        		}
        		if(turnRamp&&straightRamp)
                	Robot.driveTrainSub.tankDrive(Robot.oi.getRawAnalogStickARY()*Robot.driveTrainSub.getSpeedScaling(),-scaledLeft*Robot.driveTrainSub.getSpeedScaling());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return System.currentTimeMillis()>=endTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrainSub.tankDrive(0,0);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
