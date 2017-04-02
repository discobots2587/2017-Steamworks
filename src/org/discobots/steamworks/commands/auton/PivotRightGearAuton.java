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
    	
    	
    	addSequential(new AutonomousArcadeDrive(0,0.7,1500));//straight
    //	addParallel(new ExtendHoodCommand(500));
    	addSequential(new AutonomousArcadeDrive(0,0.5,1000));
    	addSequential(new WaitCommand(500));
    	addSequential(new AutonomousArcadeDrive(-0.5,0, 1000));
    	addSequential(new AutonomousArcadeDrive(0,0.5,1000));

    	/*
    	addSequential(new AutonomousArcadeDrive(-0.2,0.6,2200));//arc turn
    	addSequential(new WaitCommand(500));
    	addSequential(new AutonomousArcadeDrive(-0.2,0.5,4000));//arc turn
    	addSequential(new WaitCommand(500));
    	addSequential(new AutonomousArcadeDrive(-0.5,0.0,1000));//pivot turn XD 
    	addSequential(new WaitCommand(500));
    	addSequential(new AutonomousArcadeDrive(-0.17,0.5,3000));
    	addSequential(new edu.wpi.first.wpilibj.command.WaitCommand(2));
    	addSequential(new AutonomousArcadeDrive(-0.23, -0.6, 1000));//backup
    	addSequential(new AutonomousArcadeDrive(-0.2,0.55, 2500));//try again
    	addSequential(new edu.wpi.first.wpilibj.command.WaitCommand(2));
    	addSequential(new AutonomousArcadeDrive(-0.25, -0.7, 1000));//backup 
    	addSequential(new AutonomousArcadeDrive(0.2, 0.7, 2500));
    	
    	*/
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
