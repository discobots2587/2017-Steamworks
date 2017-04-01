package org.discobots.steamworks.commands.auton;

import org.discobots.steamworks.commands.auton.subcommands.AutonomousArcadeDrive;
import org.discobots.steamworks.commands.auton.subcommands.AutonomousTankDrive;
import org.discobots.steamworks.commands.auton.subcommands.WaitCommand;
import org.discobots.steamworks.commands.shoot.ExtendHoodCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PivotRightGearAuton extends CommandGroup {

    public PivotRightGearAuton() {
    	
    	
    	addSequential(new ExtendHoodCommand(1000));
    	addSequential(new AutonomousArcadeDrive(0,0.7,1500));//straight
    	addSequential(new AutonomousArcadeDrive(0,0.5,1000));
    	addSequential(new WaitCommand(500));
    	addSequential(new AutonomousTankDrive(-.5,.5,1000)); //pivot rotate check direction
    	addSequential(new AutonomousArcadeDrive(0,0.5,1000));

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
    }
}
