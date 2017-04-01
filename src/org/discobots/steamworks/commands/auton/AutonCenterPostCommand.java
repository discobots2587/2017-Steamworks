package org.discobots.steamworks.commands.auton;

import org.discobots.steamworks.commands.auton.subcommands.AutonomousArcadeDrive;
import org.discobots.steamworks.commands.shoot.ExtendHoodCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *From the center position, delivers the gear to the center post on the ship
 */
public class AutonCenterPostCommand extends CommandGroup {
    
    public  AutonCenterPostCommand() {
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
    	addSequential(new ExtendHoodCommand(1000));
    	addSequential(new AutonomousArcadeDrive(0,0.7,1500));//straight
    	addSequential(new AutonomousArcadeDrive(0,0.5,1000));
    	addSequential(new WaitCommand(1));
    	addSequential(new WaitCommand(2));
    	addSequential(new AutonomousArcadeDrive(-0.15,-0.7, 600));//backup at angle
    	addSequential(new AutonomousArcadeDrive(0,-0.7, 400));//backup straight
    	addSequential(new AutonomousArcadeDrive(-0.2, 0.65,1200));//forwards again greater angle
    	addSequential(new WaitCommand(.5));
    	addSequential(new WaitCommand(2));
    	addSequential(new AutonomousArcadeDrive(0.15,-0.7, 600));//backup at angle
    	addSequential(new AutonomousArcadeDrive(0,-0.7, 400));//backup no angle
    	addSequential(new AutonomousArcadeDrive(0.2, 0.65,1200));//forwards again greater angle
    	addSequential(new WaitCommand(.5));
    	addSequential(new WaitCommand(1.5));
    	addSequential(new AutonomousArcadeDrive(0.2,-0.7, 750));//backup at angle
    	addSequential(new AutonomousArcadeDrive(0.3, 0.55,1200));//forwards again greater angle
    	
    	
    }
}
