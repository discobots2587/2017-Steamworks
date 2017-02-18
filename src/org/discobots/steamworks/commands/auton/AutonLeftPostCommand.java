package org.discobots.steamworks.commands.auton;

import org.discobots.steamworks.commands.auton.subcommands.AutonRedoCommandGroup;
import org.discobots.steamworks.commands.auton.subcommands.AutonomousArcadeDrive;
import org.discobots.steamworks.commands.auton.subcommands.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *From the left starting position, delivers the gear to the left post on ship
 */
public class AutonLeftPostCommand extends CommandGroup {
    
    public  AutonLeftPostCommand() {
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
    	addSequential(new AutonomousArcadeDrive(0.15,0.6,2200));//fast forward
    	addSequential(new WaitCommand(500));
    	addSequential(new AutonomousArcadeDrive(0.15,0.5,4000));//creep up
    	addSequential(new WaitCommand(500));
    	addSequential(new AutonomousArcadeDrive(-0.5,0.0,1000));//turn
    	addSequential(new WaitCommand(500));
    	addSequential(new AutonomousArcadeDrive(0.15,0.5,3000));//creep into position
    	addSequential(new WaitCommand(35000));
    	addSequential(new AutonRedoCommandGroup());
    	
    	
    	
    }
}
