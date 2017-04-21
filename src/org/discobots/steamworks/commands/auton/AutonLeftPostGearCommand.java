	package org.discobots.steamworks.commands.auton;

import org.discobots.steamworks.commands.auton.subcommands.AutonomousArcadeDrive;
import org.discobots.steamworks.commands.auton.subcommands.WaitCommand;
import org.discobots.steamworks.commands.shoot.ExtendHoodCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *From the right starting position, delivers the gear to the right post on the ship
 *////////////////////////////////////////WORKS PERFECTLY DO NOT CHANGE
///////////////////////////////////////WORKS PERFECTLY DO NOT CHANGE
///////////////////////////////////////WORKS PERFECTLY DO NOT CHANGE
///////////////////////////////////////WORKS PERFECTLY DO NOT CHANGE
///////////////////////////////////////WORKS PERFECTLY DO NOT CHANGE
///////////////////////////////////////WORKS PERFECTLY DO NOT CHANGE
///////////////////////////////////////WORKS PERFECTLY DO NOT CHANGE
public class AutonLeftPostGearCommand extends CommandGroup {///////////////////////////////////////WORKS PERFECTLY DO NOT CHANGE
    
    public  AutonLeftPostGearCommand() {
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
    	addSequential(new AutonomousArcadeDrive(0,0.7,200));//drive forwards
    	addSequential(new AutonomousArcadeDrive(0.15,0.6,1500));//turn right arc
    	addSequential(new AutonomousArcadeDrive(0.3,0.5,2000));//turn right arc
    	addSequential(new AutonomousArcadeDrive(0.55,0.0,900));//pivot turn right XD
    	addSequential(new AutonomousArcadeDrive(0.2,0.55,2400));//arc right 
    	addSequential(new AutonomousArcadeDrive(0,0.7,900));//drive straight
    	addSequential(new edu.wpi.first.wpilibj.command.WaitCommand(1.5));
    	addSequential(new AutonomousArcadeDrive(0.2, -0.6, 1000));//backup
    	addSequential(new AutonomousArcadeDrive(0.15,0.55, 2500));//try again
    	addSequential(new edu.wpi.first.wpilibj.command.WaitCommand(1.5));
    	addSequential(new AutonomousArcadeDrive(0.2, -0.6, 1000));//backup 
    	addSequential(new AutonomousArcadeDrive(-0.15, 0.55, 2500));//try again
    	
    	
    }
}
