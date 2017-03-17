package org.discobots.steamworks.commands.intake;

import org.discobots.steamworks.Robot;
import org.discobots.steamworks.commands.shoot.BlendCommand;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Lowers and raises gear intake
 */
public class GearIntakeCommand extends Command {
private long waitTime;
private boolean shouldEnd=false;
int set;
    public GearIntakeCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.gearSub);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	if(Robot.gearSub.isGearOut()){//if solenoid is on
set=-1;}
    		else
set=1; //   	if(Robot.oi.count%2==0){
    	//	Robot.oi.blendIn= new GearIntakeCommand(1.0);
    	//	Robot.oi.blendOut	if(waitTime<=System.currentTimeMillis())
    //	}
   // 	else{
    	//	Robot.oi.blendIn= new BlendCommand(1.0);
    	//	Robot.oi.blendOut=new BlendCommand(-1.0);
 //   	}
	
    	waitTime=System.currentTimeMillis()+700;
    	System.out.println("Gear Intake Command Run");
    	
    	
   } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	while(waitTime>=System.currentTimeMillis())
    	{
    		Robot.gearSub.setGearState(set);
    	}
    	if(waitTime<System.currentTimeMillis())
    	{
    		shouldEnd=true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    return shouldEnd;
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.gearSub.setGearState(0);//turn solenoid off
    	System.out.println("Gear Intake Command Ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
