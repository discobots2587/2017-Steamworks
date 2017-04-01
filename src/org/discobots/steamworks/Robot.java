
package org.discobots.steamworks;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.concurrent.TimeUnit;

import org.discobots.steamworks.commands.auton.AutonCenterPostCommand;
import org.discobots.steamworks.commands.auton.AutonRightPostGearCommand;
import org.discobots.steamworks.commands.auton.AutonMobilityCommand;
import org.discobots.steamworks.commands.auton.AutonLeftPostGearCommand;
import org.discobots.steamworks.commands.auton.AutonShootAndMobilityCommand;
import org.discobots.steamworks.commands.drive.ArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.CycleDriveCommand;
import org.discobots.steamworks.commands.drive.SplitArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.TankDriveCommand;
import org.discobots.steamworks.commands.hang.HangCommand;
import org.discobots.steamworks.subsystems.BlendSubsystem;
import org.discobots.steamworks.subsystems.DriveTrainSubsystem;
import org.discobots.steamworks.subsystems.ElectricalSubsystem;
import org.discobots.steamworks.subsystems.GearDistSubsystem;
import org.discobots.steamworks.subsystems.GearIntakeSubsystem;
import org.discobots.steamworks.subsystems.HangSubsystem;
import org.discobots.steamworks.subsystems.IntakeSubsystem;
import org.discobots.steamworks.subsystems.LoggerSubsystem;
import org.discobots.steamworks.subsystems.ShootDistSubsystem;
import org.discobots.steamworks.subsystems.ShooterSubsystem;

public class Robot extends IterativeRobot {
	public static boolean testing;
	public static OI oi;
	public static DriveTrainSubsystem driveTrainSub;
	public static ShooterSubsystem shootSub;
	public static IntakeSubsystem intakeSub;
	public static HangSubsystem hangSub;
	public static GearIntakeSubsystem gearSub;
	public static ElectricalSubsystem electricSub;
	public static BlendSubsystem blendSub;
	public static double totalTime;
	public static long TeleopStartTime;
	public static long loopExecutionTime = 0;
	public static boolean directScale = false;
	public static boolean turnScale=false;
	public static LoggerSubsystem logSub;
	public static ShootDistSubsystem shootDistSub;
	public static GearDistSubsystem gearDistSub;
	public static boolean shooterLidar=false;//USE THIS TO MANUALLY TOGGLE ON SHOOTER LIDAR -- ITS NOT YET INTEGRATED THOUGH...
	public static boolean gearLidar=true;//USE THIS TO MANUALLY TOGGLE ON SHOOTER LIDAR -- ITS NOT YET INTEGRATED THOUGH...
	Thread Camthread;
	Command autonomousCommand, driveCommand;
	SendableChooser<Command> driveChooser, autonChooser;
	private boolean simple = false;
	public double autonKonstant;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.g
	 */
	@Override
	public void robotInit() {
		testing = false;//////////////////////////////FOR TESTING DRIVETRAIN ONLY
		shootSub = new ShooterSubsystem();
		intakeSub = new IntakeSubsystem();
		hangSub = new HangSubsystem();
		gearSub = new GearIntakeSubsystem();
		driveTrainSub = new DriveTrainSubsystem();
		electricSub = new ElectricalSubsystem();
		blendSub = new BlendSubsystem();
		logSub = new LoggerSubsystem();
		if(shooterLidar)
			shootDistSub=new ShootDistSubsystem();
		if(gearLidar)
			gearDistSub=new GearDistSubsystem();
			
		if (simple == true){
			oi = new SimpleOI();}
		else{
			oi = new OI();}

		autonChooser = new SendableChooser<Command>();
		autonChooser.addDefault("AutonCenter", new AutonCenterPostCommand());
		autonChooser.addObject("AutonRightPost", new AutonRightPostGearCommand());
		autonChooser.addObject("AutonLeftPost", new AutonLeftPostGearCommand());
		autonChooser.addObject("AutonMobility", new AutonMobilityCommand());
		autonChooser.addObject("AutonShootAndMobility", new AutonShootAndMobilityCommand());
		

		driveChooser = new SendableChooser<Command>();
		driveChooser.addObject("Tank Drive", new TankDriveCommand());
		driveChooser.addObject("Arcade Drive", new ArcadeDriveCommand());
		driveChooser.addDefault("Split Arcade Drive", new SplitArcadeDriveCommand());
		SmartDashboard.putData("Choose Controls", driveChooser);
		SmartDashboard.putData("Choose Auton", autonChooser);
		Camthread = new Thread() {
			@Override
			public void run() {
				System.out.println("cameratherad created");

				//try {
				//	UsbCamera C615 = CameraServer.getInstance().startAutomaticCapture(1);
				//	C615.setResolution(320, 240);
				//	if (!C615.isConnected())
				//		C615.free();

				//} catch (Exception e) {
				//	System.err.println("There is a Vision Error w/ C615: " + e.getMessage());
				//}
				try {
					// camera name taken from RoboRio
					UsbCamera Genius = CameraServer.getInstance().startAutomaticCapture(0);
					// Genius.openCamera();
					Genius.setFPS(15);
				} // footage
				catch (Exception e) {
					System.err.println("There is a Vision Error w/ Genius: " + e.getMessage());
					System.out.println("Genius being added");
				}
			}
		};
		Camthread.start();

		Dashboard.init();
		Dashboard.update();
		SmartDashboard.putData("Choose Controls", driveChooser);
		SmartDashboard.putData("Choose Auton", autonChooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		oi.updateControllerList();
		oi.running = true;
		SmartDashboard.putData("Choose Controls", driveChooser);
		SmartDashboard.putData("Choose Auton", autonChooser);
	}

	@Override
	public void disabledPeriodic() {
		long start = System.currentTimeMillis();
		Scheduler.getInstance().run();
		Dashboard.update();
		long end = System.currentTimeMillis();
		loopExecutionTime = end - start;
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		autonomousCommand = (Command) autonChooser.getSelected(); // Starts
																	// chosen
																	// Auton
																	// Command
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		driveCommand = (Command) driveChooser.getSelected();

		oi.updateControllerList();
		for (long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(1); stop > System.nanoTime();) { // rumbles upon
																										// disable
			TeleopStartTime = System.currentTimeMillis(); // one // 1
															// second
			oi.setRumble(1.0);
		}
		oi.setRumble(0);
		oi.running = true;

		if (driveCommand != null) // Starts chosen driving Command
			driveCommand.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		long start = System.currentTimeMillis(); // measures loop execution
													// times
		Scheduler.getInstance().run();
		Dashboard.update();
		Robot.oi.running = true;
		long end = System.currentTimeMillis();
		//if(DriverStation.getInstance().isFMSAttached())
		//{
		//	if(DriverStation.getInstance().getMatchTime()==35)
		//	{
			//	oi.updateControllerList();
			//	oi.running=true;
			//	if(Robot.hangSub.getHangMotorSpeed()==0);
			//	new HangCommand().start();
		//	}
		//}
		/*if(Robot.hangSub.getHangMotorSpeed()==0 && hangSub.autoHanging==false)
			{
			hangSub.autoHanging=true;
			new HangCommand().start();
			}
		loopExecutionTime = end - start;*/
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		long start = System.currentTimeMillis();
		LiveWindow.run();
		Scheduler.getInstance().run();
		Dashboard.update();
		long end = System.currentTimeMillis();
		loopExecutionTime = end - start;
		totalTime = (double) ((System.currentTimeMillis() - TeleopStartTime) / 1000);
	}
}
