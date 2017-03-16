package org.discobots.steamworks.commands.drive;

import org.discobots.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SplitArcadeDriveCommand extends Command {
	double speedScale;
	double turnSpeedScale;

	public SplitArcadeDriveCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrainSub);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Robot.driveTrainSub.arcadeDrive(Robot.oi.getRawAnalogStickARX(),
		// Robot.oi.getRawAnalogStickALY());
		if (!Robot.testing) {
			if (!Robot.turnScale && !Robot.directScale) {
				Robot.driveTrainSub.arcadeDrive(-Robot.oi.getRawAnalogStickARX(),
						Robot.oi.getRawAnalogStickALY());
			}
			if (Robot.turnScale && !Robot.directScale) {
				turnSpeedScale = Math.pow(Robot.driveTrainSub.getSpeedScaling(), 1-Math.abs(Robot.oi.getRawAnalogStickARX()));
				Robot.driveTrainSub.arcadeDrive(Robot.oi.getRawAnalogStickARX() * turnSpeedScale,
						Robot.oi.getRawAnalogStickALY());
			}
			if (!Robot.turnScale && Robot.directScale) {
				//turnSpeedScale = Math.pow(Robot.driveTrainSub.getSpeedScaling(), 1-Math.abs(Robot.oi.getRawAnalogStickARX()));//can apply in reverse so smaller values have larger speedscaling
				speedScale = Math.pow(Robot.driveTrainSub.getSpeedScaling(), 1-Math.abs(Robot.oi.getRawAnalogStickALY()));

				Robot.driveTrainSub.arcadeDrive(Robot.oi.getRawAnalogStickARX() * speedScale,
						Robot.oi.getRawAnalogStickALY());
			}
			if(Robot.turnScale && Robot.directScale) 
			{	speedScale = Math.pow(Robot.driveTrainSub.getSpeedScaling(), 1-Math.abs(Robot.oi.getRawAnalogStickALY()));
				turnSpeedScale = Math.pow(Robot.driveTrainSub.getSpeedScaling(), Math.abs(Robot.oi.getRawAnalogStickARX()));//can apply in reverse so smaller values have larger speedscaling
				Robot.driveTrainSub.arcadeDrive(Robot.oi.getRawAnalogStickARX() * speedScale,
						Robot.oi.getRawAnalogStickALY()*turnSpeedScale);			}
		} else {
			Robot.driveTrainSub.backTest(-Robot.oi.getRawAnalogStickARY(), Robot.oi.getRawAnalogStickALY());
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrainSub.arcadeDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
