package org.discobots.steamworks;


import org.discobots.steamworks.commands.drive.ArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.ComboShiftCommnad;
import org.discobots.steamworks.commands.drive.CycleDriveCommand;
import org.discobots.steamworks.commands.drive.DownShiftCommand;
import org.discobots.steamworks.commands.drive.FullSpeedCommand;
import org.discobots.steamworks.commands.drive.HalfSpeedCommand;
import org.discobots.steamworks.commands.drive.ShiftCommand;
import org.discobots.steamworks.commands.drive.UpShiftCommand;
import org.discobots.steamworks.commands.gearIntake.GearIntakeCommand;
import org.discobots.steamworks.commands.gearIntake.GearShiftCommand;
import org.discobots.steamworks.commands.hang.HangCommand;
import org.discobots.steamworks.commands.hang.ToggleHangCommand;
import org.discobots.steamworks.commands.intake.IntakeCommand;
import org.discobots.steamworks.commands.shoot.BlendCommand;
import org.discobots.steamworks.commands.shoot.ShootCommand;
import org.discobots.steamworks.utils.GamePad;
import org.discobots.steamworks.utils.GamePad.DPadButton;
import org.discobots.steamworks.utils.TriggerToggle;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private GamePad gp1 = new GamePad(0);
	private GamePad xbox = new GamePad(1); 
	
	public Command blendIn = new BlendCommand(1.0);
	public Command blendOut = new BlendCommand(-1.0);
	public int count = 0;
	public int gearCount =0;
//set buttons for each joystick
	// JOYSTICK 2
	private Button b_dpadU = new DPadButton(xbox, GamePad.DPAD_Y, true);
	private Button b_dpadD = new DPadButton(xbox, GamePad.DPAD_Y, false);
	private Button b_dpadR = new DPadButton(xbox, GamePad.DPAD_X, true);
	private Button b_dpadL = new DPadButton(xbox, GamePad.DPAD_X, false);
	private Button b_bumpR = new JoystickButton(xbox, 6);
	private Button b_bumpL = new JoystickButton(xbox, 5);
	public double b_triggerR = xbox.getRawAxis(3);//Right Trigger
	public double b_triggerL = xbox.getRawAxis(2);//left trigger
	public TriggerToggle b_trigR = new TriggerToggle(xbox,3);
	public TriggerToggle b_trigL = new TriggerToggle(xbox,2);
	private Button b_sBack = new JoystickButton(xbox, 7);
	private Button b_sStar = new JoystickButton(xbox, 8);
	private Button b_btnA = new JoystickButton(xbox, 1);
	private Button b_btnX = new JoystickButton(xbox, 3);
	private Button b_btnB = new JoystickButton(xbox, 2);
	private Button b_btnY = new JoystickButton(xbox, 4);
	private Button b_clicR = new JoystickButton(xbox, 10);
	private Button b_clicL = new JoystickButton(xbox, 9);
	// JOYSTICK 1
	private Button b2_dpadU = new DPadButton(gp1, GamePad.DPAD_Y, true);
	private Button b2_dpadD = new DPadButton(gp1, GamePad.DPAD_Y, false);
	private Button b2_dpadR = new DPadButton(gp1, GamePad.DPAD_X, true);
	private Button b2_dpadL = new DPadButton(gp1, GamePad.DPAD_X, false);
	private Button b2_bumpR = new JoystickButton(gp1, GamePad.BTN_RB);
	private Button b2_bumpL = new JoystickButton(gp1, GamePad.BTN_LB);
	private Button b2_trigR = new JoystickButton(gp1, GamePad.BTN_RT);
	private Button b2_trigL = new JoystickButton(gp1, GamePad.BTN_LT);
	private Button b2_sBack = new JoystickButton(gp1, GamePad.BTN_BACK);
	private Button b2_sStar = new JoystickButton(gp1, GamePad.BTN_START);
	private Button b2_btnA = new JoystickButton(gp1, GamePad.BTN_A);
	private Button b2_btnX = new JoystickButton(gp1, GamePad.BTN_X);
	private Button b2_btnB = new JoystickButton(gp1, GamePad.BTN_B);
	private Button b2_btnY = new JoystickButton(gp1, GamePad.BTN_Y);
	private Button b2_clicR = new JoystickButton(gp1, GamePad.AXISBTN_R);
	private Button b2_clicL = new JoystickButton(gp1, GamePad.AXISBTN_L);
	
	public OI() {
		//JOYSTICK 2************************************************************************************
		b2_sBack.whenPressed(new CycleDriveCommand());
		
		IntakeCommand in = new IntakeCommand(1.0);
		IntakeCommand out = new IntakeCommand(-1.0);
		GearIntakeCommand g = new GearIntakeCommand(1.0);
		ToggleHangCommand h = new ToggleHangCommand(.75);
		ShootCommand shoot = new ShootCommand();

		
		
		b2_btnB.toggleWhenPressed(h);
		
		b2_bumpL.toggleWhenPressed(in);
		b2_trigL.toggleWhenPressed(out);
		
		b2_bumpR.toggleWhenPressed(blendIn);//very experimental might not work
		b2_trigR.toggleWhenPressed(blendOut);//
		
		b2_btnA.toggleWhenPressed(shoot);

		b2_btnX.whenPressed(new GearShiftCommand());
		
		/*b2_dpadR.whenPressed(new FullSpeedCommand());
		b2_dpadL.whenPressed(new HalfSpeedCommand());
		
		b2_dpadU.whenPressed(new UpShiftCommand());
		b2_dpadD.whenPressed(new DownShiftCommand());*/
		
		b2_dpadU.whenPressed(new ComboShiftCommnad(true));
		b2_dpadD.whenPressed(new ComboShiftCommnad(false));
		
		
	//	b2_bumpR.whenPressed(new MoveArmCommand(ArmSubsystem.armSpeed));
		//b2_bumpR.whenReleased(new MaintainArmPosCommand());		
	
	//	b2_btnB.whileHeld(new SetShooter(1));
	//	b2_btnB.whenReleased(new SetShooter(0));
		/*	
		b2_bumpR.whileHeld(new MoveArmCommand(.75));
		b2_bumpL.whileHeld(new MoveArmCommand(-.75));
		b2_bumpL.whenReleased(new BrakeCommand(true));
		b2_bumpL.whenPressed(new BrakeCommand(false));
		b2_bumpR.whenReleased(new BrakeCommand(true));
		b2_bumpR.whenPressed(new BrakeCommand(false));*/
		
		/*b2_dpadU.whenPressed(new SetArmPosCommand(2.7));
		b2_dpadD.whenPressed(new SetArmPosCommand(0.7)); //Preferred shooting position
		b2_dpadR.whenPressed(new ShiftCommand());


		b2_bumpR.whenPressed(new SetIntakeCommand(1));
		b2_bumpR.whenReleased(new SetIntakeCommand(0));
		b2_bumpL.whileHeld(new SetIntakeCommand(-1));
		b2_bumpL.whenPressed(new SetIntakeCommand(0));
		
		b2_btnX.whenPressed(new MoveTail(0.2));
		b2_btnA.whenPressed(new IntakeClawCommand());

		
		b2_sStar.whenPressed(new SensorToggle());
		b2_btnY.whenPressed(new ToggleCompressor());*/
		
		//JOYSTICK 1******************************************************************
		b_sBack.whenPressed(new CycleDriveCommand());

		
		b_bumpL.toggleWhenPressed(in);
		b_trigL.toggleWhenActive(out);
		
		b_bumpR.toggleWhenPressed(blendIn);
		b_trigR.toggleWhenActive(blendOut);
		
		b_btnB.whileHeld(new ShootCommand());

		b_btnX.whenPressed(new GearShiftCommand());
		
		/*b_dpadR.whenPressed(new FullSpeedCommand());
		b_dpadL.whenPressed(new HalfSpeedCommand());
		
		b2_dpadU.whenPressed(new UpShiftCommand());
		b2_dpadD.whenPressed(new DownShiftCommand());*/
		
		b2_dpadU.whenPressed(new ComboShiftCommnad(true));
		b2_dpadD.whenPressed(new ComboShiftCommnad(false));
		
/*		b2_btnB.whileHeld(new LinearPunchStartCommand());
		b_btnB.whileHeld(new LinearPunchStartCommand());
		b2_btnB.whenReleased(new LinearPunchEndCommand());
		b_btnB.whenReleased(new LinearPunchEndCommand());
	*/	

		//b_dpadU.whenPressed(new SetArmPosCommand(3.558));
		//b_dpadL.whenPressed(new SetArmPosCommand(4));
		/*b_dpadD.whenPressed(new SetArmPosCommand(0.7));
		b_dpadU.whenPressed(new SetArmPosCommand(2.7));

		b_dpadR.whenPressed(new ShiftCommand());
		
		b_sBack.whenPressed(new CycleDriveCommand());
		b_bumpR.whileHeld(new SetIntakeCommand(-1)); //pulls ball in
		b_bumpR.whenReleased(new SetIntakeCommand(0));
		b_bumpL.whileHeld(new SetIntakeCommand(1)); //pushes ball out
		b_bumpL.whenReleased(new SetIntakeCommand(0));
		
		b_btnA.whenPressed(new IntakeClawCommand());
	//	b_btnX.whenPressed(new MoveTail(0.2));  //TAIL WAS REMOVED
		
		
		b_sStar.whenPressed(new SensorToggle());
		b_btnY.whenPressed(new ToggleCompressor());
		
		b_btnB.whenPressed(new SetShooter());
		b2_btnB.whenPressed(new SetShooter());*/

		// This is for the version with single click loading and firing, no whenReleased
	//	b_btnB.whenPressed(new LinearPunchStartCommand());
		//b_btnB.whenReleased(new LinearPunchEndCommand());
	//	b2_bumpR.whenPressed(new LinearPunchStartCommand());
	//	b2_bumpR.whenReleased(new LinearPunchEndCommand());
		
		
		
		
		//b_bumpR.whileHeld(new MoveArmCommand(ArmSubsystem.armSpeed));
//		b_bumpR.whenReleased(new MoveArmCommand(0));
		//b_bumpL.whileHeld(new MoveArmCommand(-ArmSubsystem.armSpeed));
	//	b_bumpL.whenReleased(new MoveArmCommand(0));
		
	//	b_triggerR.whileHeld(new SetIntakeCommandCommand(.5));
	//	b_triggerL.whileHeld(new SetIntakeCommandCommand(-.5));
	}
	/*
	 * returns left tick x-axis
	 */
	public double getRawAnalogStickALX() {
		if(gp1.getRawAxis(0)>0.1||gp1.getRawAxis(0)<=-0.1)
			return (gp1.getRawAxis(0));
		else
			return (xbox.getRawAxis(0));
	}
	

	/*
	 * returns left stick y-axis
	 */
	public double getRawAnalogStickALY() {
		if(gp1.getRawAxis(1)>0.1||gp1.getRawAxis(1)<-0.1)
			return gp1.getRawAxis(1);
		else
			return (xbox.getRawAxis(1));
	}
	/*
	 * returns right stick x-axis
	 */
	public double getRawAnalogStickARX() {
		if(gp1.getRawAxis(2)>0.1||gp1.getRawAxis(2)<-0.1)
			return gp1.getRawAxis(2);
		else
			return (xbox.getRawAxis(4));
	}
	/*
	 * returns right stick y-axis
	 */
	public double getRawAnalogStickARY() {
		if(gp1.getRawAxis(3)>0.1||gp1.getRawAxis(3)<-0.1)
			return -gp1.getRawAxis(3);
		else
			return (-xbox.getRawAxis(5));
	}

	public double getRawAnalogStickBLX() {
		return (xbox.getRawAxis(0));
	}

	public double getRawAnalogStickBLY() {
		return (-xbox.getRawAxis(1));// left stick y-axis

	}

	public double getRawAnalogStickBRX() {
		return (xbox.getRawAxis(4));// left stick x-axis

	}

	public double getRawAnalogStickBRY() {
		return (xbox.getRawAxis(5));// left stick x-axis

	}
	public double getRT(){
		if(b2_trigR.get())//logitch controller does not have an axis on right trigger
			return 1.0;
		else
			return (xbox.getRawAxis(3));
		
	}
	public double getLT(){
		if(b2_trigL.get())//logitch controller does not have an axis on left trigger
			return 1.0;
		else
			return (xbox.getRawAxis(2));
	} 
}
