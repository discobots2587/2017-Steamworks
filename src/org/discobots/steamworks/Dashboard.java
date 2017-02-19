
package org.discobots.steamworks;
import org.discobots.steamworks.commands.drive.ArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.SplitArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.TankDriveCommand;
import org.discobots.steamworks.commands.utils.RefreshGamepadPorts;

import edu.wpi.first.wpilibj.NamedSendable;
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
		SmartDashboard.putData("Reset GamePad Ports", new RefreshGamepadPorts());
		
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
			//SmartDashboard.getData(Robot.electricSub.shoots.getSmartDashboardType());//2013 motorcompressor has itable example
			SmartDashboard.putBoolean("Shooter Encoder stopped", Robot.electricSub.getShootEncoderStopped());
			SmartDashboard.putNumber("Shooter Filtered RPM", Robot.electricSub.getShootRPMfiltered());
			SmartDashboard.putNumber("Shooter Raw RPM", Robot.electricSub.getShootRPMraw());
			SmartDashboard.putNumber("ShooterDist", Robot.electricSub.getRotations());
		} else if (driveCounter % 5 == 1) {
			SmartDashboard.putBoolean("Shooter toggled in Subsystem?", Robot.shootSub.isShooterToggled());
			SmartDashboard.putBoolean("LeftHandRunning", Robot.oi.left.isAlive());
			SmartDashboard.putBoolean("running True?", Robot.oi.running);
			//SmartDashboard.putData("DriveTrainCommand", Robot.driveTrainSub.getCurrentCommand());			
			//SmartDashboard.putNumber("Potentiometer", Robot.armSub.potentiometer.getAverageVoltage());
			Robot.driveTrainSub.setSpeedScaling(TestPrefs.getDouble("SpeedScaling", 1.0));
			Robot.shootSub.setSetpoint(TestPrefs.getDouble("SetPoint", 1.0));
			SmartDashboard.putNumber("Axis ValueRx", Robot.oi.getRawAnalogStickARX());
			SmartDashboard.putNumber("PWM Hang", Robot.hangSub.getPWM());
			SmartDashboard.putNumber("PWM intake",Robot.intakeSub.getPWM());
			SmartDashboard.putNumber("PWM Shooter", Robot.shootSub.getPWM());
			SmartDashboard.putNumber("PWM Blender", Robot.blendSub.getPWM());

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