package org.discobots.steamworks.utils;


import org.discobots.steamworks.Robot;
import org.discobots.steamworks.utils.GamePad.Hand;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LTRTXBOX extends Command {//computes the interim values for the xbox triggers and does speed scaling based on pressure
	
	double TriggerValue;
	Hand left = GamePad.Hand.LEFT;
	Hand right = GamePad.Hand.RIGHT;
    public LTRTXBOX() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.intakeSub);
    	//requires(Robot.armSub);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		double rightT = Robot.oi.getRawRT();
    		double leftT = Robot.oi.getRawLT();
    	   TriggerValue = rightT-leftT;//constantly updates every 20 milliseconds
    	  // if(Math.abs(liftSpeed)<.15)
    		//   liftSpeed=0;
  //  	   Robot.armSub.setSpeed(liftSpeed);
    	 
    	   
    	   if (TriggerValue>.2)
    		   Robot.oi.setRumble(right, TriggerValue/2);
    	   if(TriggerValue<-.2)
    		   Robot.oi.setRumble(left,-TriggerValue/2);
        	   if(-.75<=TriggerValue&&TriggerValue<-.3)//deadband
        	   {
        		   TriggerValue=-.75;
        		  // if(Robot.armSub.potentiometer.getAverageVoltage()< Robot.armSub.upperArmLim) //2015 we used POT values for PID
        			 //  Robot.armSub.setSpeed(TriggerValue);
        	   } else if (.75>=TriggerValue&&TriggerValue>.3){//minimum intake speed of +/- .75
        		   TriggerValue=.75;
        		  //if(Robot.armSub.potentiometer.getAverageVoltage()> Robot.armSub.lowerArmLim)
        			  // Robot.armSub.setSpeed(TriggerValue);
        	   }
        	   else if(-.3<=TriggerValue&&.3>=TriggerValue)
        	   {
        		   TriggerValue=0;//deadband of -.2 to .2
        	   }
    	   Robot.intakeSub.setSpeed(TriggerValue);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//liftSpeed=0;//always set speeds to zero in end
   // 	Robot.oi.setRumble(0);
    	TriggerValue=0;
    	Robot.intakeSub.setSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
