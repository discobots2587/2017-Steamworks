
package org.discobots.steamworks;
import org.discobots.steamworks.commands.drive.ArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.SplitArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.TankDriveCommand;
import org.discobots.steamworks.commands.utils.RefreshGamepadPorts;
import org.discobots.steamworks.commands.utils.SensorToggle;

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
		SmartDashboard.putData("Disable Sensors?", new SensorToggle());
		SmartDashboard.putData("Disable LIDAR?", new SensorToggle(1));
		SmartDashboard.putNumber("Hang Motor Speed", Robot.hangSub.getHangMotorSpeed());


	}

	public static void update() {
		driveCounter++;
		//pdpPTs.add(Robot.electricalSub.getPDPTotalCurrent());
		if (driveCounter == Integer.MAX_VALUE) {
			driveCounter = 0;
		}

		if (driveCounter % 5 == 0) { // 100ms
			SmartDashboard.putNumber("Hang Motor Speed", Robot.hangSub.getHangMotorSpeed());
			SmartDashboard.putNumber("Robot Loop Execution Time",
					Robot.loopExecutionTime);
			SmartDashboard.putBoolean("Shooter toggled in Subsystem?", Robot.shootSub.isShooterToggled());
			SmartDashboard.putBoolean("LeftHandRunning", Robot.oi.left.isAlive());
			SmartDashboard.putBoolean("running True?", Robot.oi.running);
			SmartDashboard.putString("Gear Solenoid State: ", Robot.gearSub.getGearSolenoidState());
		} else if (driveCounter % 5 == 1) {

			//SmartDashboard.putData("DriveTrainCommand", Robot.driveTrainSub.getCurrentCommand());			
			SmartDashboard.putNumber("Axis ValueRx", Robot.oi.getRawAnalogStickARX());
			SmartDashboard.putNumber("PRESSURE", Robot.electricSub.getPressure());
			SmartDashboard.putNumber("shooter Avg RPM", Robot.electricSub.getShootRPMAVG());//mason's custom rpm average
			SmartDashboard.putBoolean("Gear State (true=out) ", Robot.gearSub.isGearOut());


		}
		if (driveCounter%10==1)
		{
			SmartDashboard.putBoolean("Gear Loaded", Robot.electricSub.isGearLoaded());
			SmartDashboard.putBoolean("Shooter Encoder stopped", Robot.electricSub.getShootEncoderStopped());
			SmartDashboard.putNumber("SpeedScaling", Robot.driveTrainSub.getSpeedScaling());
			SmartDashboard.putNumber("The gear intake is: ", Robot.gearSub.getGearState());
			SmartDashboard.putBoolean("Direct Speed Scale On?", Robot.directScale);
			SmartDashboard.putBoolean("Turning Speed Scale On?", Robot.turnScale);
		//	SmartDashboard.putNumber("PWM Hang", Robot.hangSub.getPWM());
		//	SmartDashboard.putNumber("PWM intake",Robot.intakeSub.getPWM());
		//	SmartDashboard.putNumber("PWM Shooter", Robot.shootSub.getPWM());
		//	SmartDashboard.putNumber("PWM Blender", Robot.blendSub.getPWM());
			Robot.shootSub.setSetpoint(TestPrefs.getDouble("SetPoint", 1.0));
			Robot.driveTrainSub.setSpeedScaling(TestPrefs.getDouble("SpeedScaling", 1.0));
			Robot.driveTrainSub.setAutonKonstant(TestPrefs.getDouble("AutonConstant", 1.0));//auton speed/time scaling --settable via driver dashboard and independent of speed scaling
			Robot.gearLidar=TestPrefs.getBoolean("GearLidarSub", false);//create Gear Lidar subsystem on robot initialization?
			Robot.shooterLidar=TestPrefs.getBoolean("ShootLidarSub", false);//create shoot lidar on initialization?
			Robot.testing = TestPrefs.getBoolean("EnableDriveTrainTesting", false);
		//	SmartDashboard.putNumber("Shooter Raw RPM", Robot.electricSub.getShootRPMraw());
			if(Robot.gearLidar)
			{
			SmartDashboard.putBoolean("GEAR IS LOADABLE? ", Robot.gearDistSub.onTarget());//Is the robot within the required distance parameters to load a gear from the human player?
			SmartDashboard.putNumber("Gear Distance INCHES: ", Robot.gearDistSub.getGearDistInches());//Lidar Distance in INCEHS
			SmartDashboard.putNumber("Gear Distance CENTIMETERS: ", Robot.gearDistSub.getGearDistCM());//Lidar Distance in INCEHS
			}
			if(Robot.shooterLidar)
			{
				
			}
			
			
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