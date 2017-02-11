package org.discobots.steamworks;
import org.discobots.steamworks.commands.drive.ArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.SplitArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.TankDriveCommand;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {
	static Preferences TestPrefs;
	public static LiveWindow lw;
	public static int driveCounter = 0;
 
	public static void init() {
		TestPrefs = Preferences.getInstance();
		SmartDashboard.putData("Arcade Drive", new ArcadeDriveCommand());
		SmartDashboard.putData("Split Arcade Drive", new SplitArcadeDriveCommand());
		SmartDashboard.putData("Tank Drive", new TankDriveCommand());
		
		
	}

	public static void update() {
		driveCounter++;
		//pdpPTs.add(Robot.electricalSub.getPDPTotalCurrent());
		if (driveCounter == Integer.MAX_VALUE) {
			driveCounter = 0;
		}

		if (driveCounter % 5 == 0) { // 100ms
			SmartDashboard.putNumber("Robot Loop Execution Time",
					Robot.loopExecutionTime);

		} else if (driveCounter % 5 == 1) {
			//SmartDashboard.putData("DriveTrainCommand", Robot.driveTrainSub.getCurrentCommand());
		
		//SmartDashboard.putNumber("Potentiometer", Robot.armSub.potentiometer.getAverageVoltage());
			Robot.driveTrainSub.setSpeedScaling(TestPrefs.getDouble("SpeedScaling", 1.0));
			SmartDashboard.putNumber("Axis ValueRx", Robot.oi.getRawAnalogStickARX());

		}
		if (driveCounter%10==1)
		{
			SmartDashboard.putNumber("SpeedScaling", Robot.driveTrainSub.getSpeedScaling());

						//SmartDashboard.putNumber("PRESSURE", Robot.electricalSub.getPressure());
			
			//SmartDashboard.putBoolean("Pressure Switch State", Robot.electricalSub.getPressureSwitchState());
		} 
		 if(driveCounter%100==1)//for very unimportant notifications
		{
				if (Robot.driveTrainSub.getCurrentCommand() instanceof SplitArcadeDriveCommand) {
					 SmartDashboard.putString("Current Drive: ", "Split Arcade Drive");	
				}
				else if (Robot.driveTrainSub.getCurrentCommand() instanceof ArcadeDriveCommand)
				{
					 SmartDashboard.putString("Current Drive: ", "Arcade Drive");	
				}
				else
				{
					 SmartDashboard.putString("Current Drive: ", "Tank Drive");	

				}
		}
		
	}
}