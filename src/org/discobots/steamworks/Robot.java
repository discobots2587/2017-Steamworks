
package org.discobots.steamworks;

import org.discobots.steamworks.commands.ExampleCommand;
import org.discobots.steamworks.subsystems.ExampleSubsystem;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	
	
	private CameraServer LogicC615;
	public static double totalTime;
	public static long TeleopStartTime;
	public static long loopExecutionTime = 0;
	
	
	Command autonomousCommand,driveCommand;
    SendableChooser driveChooser, autonChooser;
    

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    public void robotInit() {
    	//init camera and start simple stream process...
    	//IMPORTANT -- camera system and code is redone for 2017-- Cameras should no longer have to be initialized separately ...
   try{ 	
	   LogicC615 = CameraServer.getInstance();//initialize server
        //camera name taken from RoboRio
        UsbCamera C615 = new UsbCamera("cam0", 0);
       // LogicC615.openCamera(); 
       // LogicC615.startCapture();
       LogicC615.startAutomaticCapture(C615);//automatically start streaming footage 
   }catch(Exception e){
	    System.err.println("Mason has DiscoSwag \n BUT there is a Vision Error: " + e.getMessage());
   }

 
    	/* Subsystems */

    //	linearPunchSub = new LinearPunchSubsystem();

    	
    	
    	/* Dashboard Choosers */
    	
    	autonChooser = new SendableChooser();
    	//autonChooser.addObject("DumbPostitioningAuton", new DumbPositioningAuton());
		
		
		driveChooser = new SendableChooser();
		//driveChooser.addObject("Tank Drive", new TankDriveCommand());
		//driveChooser.addObject("Arcade Drive", new ArcadeDriveCommand());
		//driveChooser.addDefault("Split Arcade Drive", new SplitArcadeDriveCommand());
		SmartDashboard.putData("Choose Driving Controls", driveChooser);

        //gamepad mapping
    	oi = new OI();
		
		// dashboard init
		Dashboard.init();
		Dashboard.update();
		
		
    }

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
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
		 autonomousCommand = (Command) autonChooser.getSelected();    	//Starts chosen Auton Command
	    	// schedule the autonomous command (example)
	        if (autonomousCommand != null) autonomousCommand.start();
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
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
