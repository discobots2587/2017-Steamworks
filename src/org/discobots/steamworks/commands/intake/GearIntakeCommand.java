package org.discobots.steamworks.commands.intake;

import org.discobots.steamworks.Robot;
import org.discobots.steamworks.commands.shoot.BlendCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid;
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
    	
    	if(Robot.gearSub.getGearSolenoidState().equals(DoubleSolenoid.Value.kForward))
    		Robot.gearSub.setGearState(-1);
    	else if(Robot.gearSub.getGearSolenoidState().equals(DoubleSolenoid.Value.kReverse))
    		Robot.gearSub.setGearState(1);
    	else
    		Robot.gearSub.setGearState(0);
    	System.out.println("Gear Intake Command Run");
    	
    	
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
    	System.out.println("Gear Intake Command Ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
