
package org.discobots.steamworks;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.concurrent.TimeUnit;

import org.discobots.steamworks.commands.auton.AutonCenterPostCommand;
import org.discobots.steamworks.commands.auton.AutonLeftPostCommand;
import org.discobots.steamworks.commands.auton.AutonRightPostCommand;
import org.discobots.steamworks.commands.drive.ArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.CycleDriveCommand;
import org.discobots.steamworks.commands.drive.SplitArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.TankDriveCommand;
import org.discobots.steamworks.subsystems.BlendSubsystem;
import org.discobots.steamworks.subsystems.DriveTrainSubsystem;
import org.discobots.steamworks.subsystems.ElectricalSubsystem;
import org.discobots.steamworks.subsystems.GearIntakeSubsystem;
import org.discobots.steamworks.subsystems.HangSubsystem;
import org.discobots.steamworks.subsystems.IntakeSubsystem;
import org.discobots.steamworks.subsystems.ShooterSubsystem;

public class Robot extends IterativeRobot {
	public static OI oi;
	public static DriveTrainSubsystem driveTrainSub;
	public static ShooterSubsystem shootSub;
	public static IntakeSubsystem intakeSub;
	public static HangSubsystem hangSub;
	public static GearIntakeSubsystem gearSub;
	public static ElectricalSubsystem electricSub;
	public static BlendSubsystem blendSub;
	private CameraServer GeniusCam;
	public static double totalTime;
	public static long TeleopStartTime;
	public static long loopExecutionTime = 0;
	Thread Camthread;
	Command autonomousCommand, driveCommand;
	SendableChooser<Command> driveChooser, autonChooser;
	private boolean simple = false;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		shootSub = new ShooterSubsystem();
		intakeSub = new IntakeSubsystem();
		hangSub = new HangSubsystem();
		gearSub = new GearIntakeSubsystem();
		driveTrainSub = new DriveTrainSubsystem();
		electricSub = new ElectricalSubsystem();
		blendSub = new BlendSubsystem();
		if (simple == true)
			oi = new SimpleOI();
		else
			oi = new OI();

		autonChooser = new SendableChooser<Command>();
		// autonChooser.addObject("DumbPostitioningAuton", new
		// DumbPositioningAuton());
		autonChooser.addDefault("AutonCenter", new AutonCenterPostCommand());
		autonChooser.addObject("AutonRight", new AutonRightPostCommand());
		autonChooser.addObject("AutonLeft", new AutonLeftPostCommand());

		driveChooser = new SendableChooser<Command>();
		driveChooser.addObject("Tank Drive", new TankDriveCommand());
		driveChooser.addObject("Arcade Drive", new ArcadeDriveCommand());
		driveChooser.addDefault("Split Arcade Drive", new SplitArcadeDriveCommand());

		Camthread = new Thread() {
			@Override
			public void run() {
				System.out.println("cameratherad created");

				try {
					GeniusCam = CameraServer.getInstance();// initialize server
					// camera name taken from RoboRio
					// UsbCamera C615 = new UsbCamera("C615", 1);
					// LogicC615.openCamera();
					// LogicC615.startCapture();
					// if (C615.isConnected())
					{
						// C615.setResolution(480, 320);
						// GeniusCam.startAutomaticCapture(C615);//
						// automatically start
						// streaming
					} // footage
				} catch (Exception e) {
					System.err.println("There is a Vision Error w/ C615: " + e.getMessage());
				}
				try {
					// camera name taken from RoboRio
					UsbCamera Genius = new UsbCamera("cam2", 0);
					// Genius.openCamera();
					System.out.println(Genius.getPath());
					GeniusCam.startAutomaticCapture(Genius);// automatically
															// start
					Genius.setFPS(15);
					Genius.setResolution(320, 240);

				} // footage
				catch (Exception e) {
					System.err.println("There is a Vision Error w/ Genius: " + e.getMessage());
					System.out.println("Genius being added");
				}
			}
		};
		Camthread.run();

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

		oi.setRumble(0); // for
		oi.updateControllerList();
		for (long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(1); stop > System.nanoTime();) { // rumbles
																										// upon
																										// disable
			oi.setRumble(1); // for
			TeleopStartTime = System.currentTimeMillis(); // one // 1
															// second
		}
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
		long end = System.currentTimeMillis();
		loopExecutionTime = end - start;
		Robot.oi.running = true;
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
