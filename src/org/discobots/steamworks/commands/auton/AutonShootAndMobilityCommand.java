package org.discobots.steamworks.commands.auton;

import org.discobots.steamworks.commands.auton.subcommands.AutonomousArcadeDrive;
import org.discobots.steamworks.commands.auton.subcommands.AutonomousShootCommand;
import org.discobots.steamworks.commands.auton.subcommands.WaitCommand;
import org.discobots.steamworks.commands.shoot.ExtendHoodCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *From the center position, delivers the gear to the center post on the ship
 */
public class AutonShootAndMobilityCommand extends CommandGroup {
    
    public  AutonShootAndMobilityCommand() {
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
    	addSequential(new ExtendHoodCommand(500));
    	addSequential(new AutonomousArcadeDrive(-7,0,500));
    	addSequential(new AutonomousArcadeDrive(0,7,2500));
    	addSequential(new ExtendHoodCommand(500));
    	addSequential(new WaitCommand(500));
    	addSequential(new AutonomousShootCommand(5000));
    	addSequential(new WaitCommand(500));
    	addSequential(new AutonomousArcadeDrive(-.5,0,2000));
    	addSequential(new WaitCommand(500));
    	addSequential(new AutonomousArcadeDrive(0.15,.6,6000));//drive forward


    	 	
    	
    	
    }
}
