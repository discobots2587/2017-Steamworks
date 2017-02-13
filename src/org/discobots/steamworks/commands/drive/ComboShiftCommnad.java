package org.discobots.steamworks.commands.drive;

import org.discobots.steamworks.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ComboShiftCommnad extends Command {
	private int time;
	private long endTime;
	private double speed;
	private boolean fin=false;
	public boolean end=false;
	private boolean direction = false;

    public ComboShiftCommnad(boolean direction) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.driveTrainSub);
    	this.direction = direction;

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(direction)
    		Robot.gearSub.gearCount++;
    	else
    		Robot.gearSub.gearCount--;
    	if(Robot.gearSub.gearCount==0){
    		Robot.driveTrainSub.setLRShifter(false);
    		Robot.driveTrainSub.setSpeedScaling(.5);
    	}
    	else if(Robot.gearSub.gearCount==1){
    		Robot.driveTrainSub.setLRShifter(false);
    		Robot.driveTrainSub.setSpeedScaling(1.0);
    	}
    	else if(Robot.gearSub.gearCount==2){
    		Robot.driveTrainSub.setLRShifter(true);
    		Robot.driveTrainSub.setSpeedScaling(.5);
    	}
    	else if(Robot.gearSub.gearCount==3){
    		Robot.driveTrainSub.setLRShifter(true);
    		Robot.driveTrainSub.setSpeedScaling(1.0);
    	}
    	else if(direction){
    		Robot.gearSub.gearCount=3;
    	}
    	else if(!direction){
    		Robot.gearSub.gearCount=0;
    	}
    	else
    	{   		
    	}

    	
    } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	
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
    	end();
    }
}
