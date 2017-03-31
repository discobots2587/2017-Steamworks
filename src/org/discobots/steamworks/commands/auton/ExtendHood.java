package org.discobots.steamworks.commands.auton;

import org.discobots.steamworks.commands.shoot.ExtendHoodCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ExtendHood extends CommandGroup {

    public ExtendHood() {
        this.addSequential(new ExtendHoodCommand(500));
    }
}
