package org.discobots.steamworks.commands.auton;

import org.discobots.steamworks.commands.auton.subcommands.AutonomousArcadeDrive;
import org.discobots.steamworks.commands.auton.subcommands.AutonomousTankDrive;
import org.discobots.steamworks.commands.auton.subcommands.WaitCommand;
import org.discobots.steamworks.commands.shoot.ExtendHoodCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *From the left starting position, delivers the gear to the left post on ship
 */
public class AutonRightPostGearCommand extends CommandGroup {///////////////////////CREATED BASED ON WORKING RIGHT POST
    
    public  AutonRightPostGearCommand() {//seems to work ok but could probably speed up some...
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	//addParallel(new ExtendHoodCommand(500));
    	addSequential(new AutonomousArcadeDrive(0,0.7,500));
    	addSequential(new AutonomousArcadeDrive(-0.3,0.6,1500));//arc turnewd
    	addSequential(new AutonomousArcadeDrive(-0.3,0.6,2000));//arc turn
    	addSequential(new AutonomousArcadeDrive(-0.55,0.0,900));//pivot turn XD 
    	addSequential(new AutonomousArcadeDrive(-0.2,0.6,2400));
    	addSequential(new edu.wpi.first.wpilibj.command.WaitCommand(2));
    	addSequential(new AutonomousArcadeDrive(-0.23, -0.6, 1000));//backup
    	addSequential(new AutonomousArcadeDrive(-0.2,0.55, 2500));//try again
    	addSequential(new edu.wpi.first.wpilibj.command.WaitCommand(2));
    	addSequential(new AutonomousArcadeDrive(-0.25, -0.7, 1000));//backup 
    	addSequential(new AutonomousArcadeDrive(0.2, 0.7, 2500));
    	
    }
}
