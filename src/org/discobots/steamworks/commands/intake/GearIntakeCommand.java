package org.discobots.steamworks.commands.intake;

import org.discobots.steamworks.Robot;
import org.discobots.steamworks.commands.shoot.BlendCommand;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Lowers and raises gear intake
 */
public class GearIntakeCommand extends Command {

    public GearIntakeCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.gearSub);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(Robot.gearSub.getGear()){//if solenoid is on
    		Robot.gearSub.setGear(false);}//turn solenoid off
    	
    	
    //	else
    	//	Robot.gearIntakeSub.set(true);
 //   	if(Robot.oi.count%2==0){
    	//	Robot.oi.blendIn= new GearIntakeCommand(1.0);
    	//	Robot.oi.blendOut=new GearIntakeCommand(-1.0);
    //	}
   // 	else{
    	//	Robot.oi.blendIn= new BlendCommand(1.0);
    	//	Robot.oi.blendOut=new BlendCommand(-1.0);
 //   	}
   } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

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
