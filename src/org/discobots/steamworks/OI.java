package org.discobots.steamworks;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;


import org.discobots.steamworks.commands.drive.ArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.ComboShiftCommnad;
import org.discobots.steamworks.commands.drive.CycleDriveCommand;

import org.discobots.steamworks.commands.drive.FullSpeedCommand;
import org.discobots.steamworks.commands.drive.HalfSpeedCommand;


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


import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {


	GamePad[] gamePads;
	public int numPads;
	private Thread left;
	private Thread right;
	private double activeLX = 0.0;
	private double activeLY = 0.0;
	private double activeRY = 0.0;
	private double activeRX = 0.0;
	public boolean running = true;
	
public double L1_triggerR;	
public double L1_triggerL;	

public ArrayList<Button> port0;
public ArrayList<Button> port1;
public ArrayList<Button> port2;
public ArrayList<Button> port3;
public ArrayList<Button> port4;
public ArrayList<Button> port5;

public ArrayList <Button> joystickButtons;
private ArrayList<Integer> ports;
	public OI() {
		gamePads=new GamePad[6];
		port0 = new ArrayList<Button>();
		port1 = new ArrayList<Button>();
		port2 = new ArrayList<Button>();
		port3 = new ArrayList<Button>();
		port4 = new ArrayList<Button>();
		port5 = new ArrayList<Button>();
		ports = new ArrayList<Integer>();
		
		
		updateControllerList();
	}

	public void updateControllerList() {
		new Thread()
		{
			
		public void run(){
			ports.clear();
		numPads = 0;
		running = false;
		for (int i = 0; i <= 5; i++)// check all ports
		{
			try{
			if (DriverStation.getInstance().getStickAxisCount(i) >= 6) {
				ports.add(i);
				numPads++;
				gamePads[i]= new Xbox(i, true);
				SmartDashboard.putString("Controller Debug Name", gamePads[i].thegetName());
				SmartDashboard.putString("Xbox in Ports", SmartDashboard.getString("Xbox in Ports", "") + i + " ");
			} else if (DriverStation.getInstance().getStickAxisCount(i) >= 1) {
				ports.add(i);
				gamePads[i]= new GamePad(i, false);
				numPads++;
				SmartDashboard.putString("GenericHIDcontrol in Ports",
						SmartDashboard.getString("GenericHIDcontrol in Ports", "") + i + " ");
			} else
				gamePads[i]=null;
			}
			catch(Exception e)
			{
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				String error = "Controller Glitch";
						error.concat(errors.toString());
				DriverStation.reportError(error, true);
				gamePads[i]=null;
			}

		}
		}}.run();
		createMapping();
		running=true;
	}

	public void createMapping() {
		port0.clear();
		port1.clear();
		port2.clear();
		port3.clear();
		port4.clear();
		port5.clear();
		for (int i = 5; i >= 0; i--) {
			if (gamePads[i] != null && gamePads[i].getAxisCount() > 0) {
				if (gamePads[i] instanceof Xbox) {// ability to create entirely separate
											// control scheme for xbox
											// controller even if in same port
					if (i == 0) {// Can also differentiate by specific port set
									// in DriverStation Software
						port0.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port0.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port0.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port0.add(new JoystickButton(gamePads[i], Xbox.BTN_RB));
						port0.add( new JoystickButton(gamePads[i], Xbox.BTN_LB));
						if (gamePads[i] instanceof Xbox) {
							L1_triggerR = gamePads[i].getLZ();// Right Trigger
							L1_triggerL = gamePads[i].getRZ();// left trigger
						} else {

						}
						port0.add(new JoystickButton(gamePads[i], Xbox.BTN_BACK));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0. add(new JoystickButton(gamePads[i], Xbox.BTN_START));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add( new JoystickButton(gamePads[i], Xbox.BTN_A));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add( new JoystickButton(gamePads[i], Xbox.BTN_X));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add( new JoystickButton(gamePads[i], Xbox.BTN_B));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add( new JoystickButton(gamePads[i], Xbox.BTN_Y));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_R));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_L));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
					}
					/*
					 * int temp; temp = gamePads[i].makebuttons(new               //Previous idea to set buttons and commands in each controller object - not working
					 * DPadButton(gamePads[i], Xbox.DPAD_Y, false));
					 * gamePads[i].getButton(temp-1).toggleWhenPressed(new
					 * CycleDriveCommand()); temp = gamePads[i].makebuttons(new
					 * JoystickButton(gamePads[i], Xbox.BTN_BACK));
					 * gamePads[i].getButton(temp-1).toggleWhenPressed(new
					 * CycleDriveCommand());
					 * SmartDashboard.putBoolean("Mapping Created for Xbox",
					 * true);
					 */
				}

				if (!(gamePads[i] instanceof Xbox)) {// ability to create entirely separate
											// control scheme for non xbox
											// controller even if in same port

					if (i == 0) {// Can also differentiate by specific port set
									// in DriverStation Software
						port0.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port0.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port0.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port0.add(new JoystickButton(gamePads[i], gamePads[i].BTN_RB));
						port0.add( new JoystickButton(gamePads[i], gamePads[i].BTN_LB));
						if (gamePads[i].isXbox) {
							L1_triggerR = gamePads[i].getLZ();// Right Trigger
							L1_triggerL = gamePads[i].getRZ();// left trigger
						} else {

						}
						port0.add(new JoystickButton(gamePads[i], gamePads[i].BTN_BACK));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0. add(new JoystickButton(gamePads[i], gamePads[i].BTN_START));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add( new JoystickButton(gamePads[i], gamePads[i].BTN_A));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add( new JoystickButton(gamePads[i], gamePads[i].BTN_X));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add( new JoystickButton(gamePads[i], gamePads[i].BTN_B));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add( new JoystickButton(gamePads[i], gamePads[i].BTN_Y));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add( new JoystickButton(gamePads[i], gamePads[i].AXISBTN_R));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add( new JoystickButton(gamePads[i], gamePads[i].AXISBTN_L));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());

					}
				}

				if (i == 1) {
					
					if (gamePads[i] instanceof Xbox){
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port1.add(new JoystickButton(gamePads[i], Xbox.BTN_RB));
						port1.add( new JoystickButton(gamePads[i], Xbox.BTN_LB));
						if (gamePads[i] instanceof Xbox) {
							L1_triggerR = gamePads[i].getLZ();// Right Trigger
							L1_triggerL = gamePads[i].getRZ();// left trigger
						} else {

						}
						port1.add(new JoystickButton(gamePads[i], Xbox.BTN_BACK));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1. add(new JoystickButton(gamePads[i], Xbox.BTN_START));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], Xbox.BTN_A));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], Xbox.BTN_X));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], Xbox.BTN_B));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], Xbox.BTN_Y));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_R));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_L));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
					}
					if (!(gamePads[i] instanceof Xbox)){
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port1.add(new JoystickButton(gamePads[i], gamePads[i].BTN_RB));
						port1.add( new JoystickButton(gamePads[i], gamePads[i].BTN_LB));
						if (gamePads[i].isXbox) {
							L1_triggerR = gamePads[i].getLZ();// Right Trigger
							L1_triggerL = gamePads[i].getRZ();// left trigger
						} else {

						}
						port1.add(new JoystickButton(gamePads[i], gamePads[i].BTN_BACK));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1. add(new JoystickButton(gamePads[i], gamePads[i].BTN_START));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], gamePads[i].BTN_A));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], gamePads[i].BTN_X));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], gamePads[i].BTN_B));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], gamePads[i].BTN_Y));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], gamePads[i].AXISBTN_R));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], gamePads[i].AXISBTN_L));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
					}
				}

				if (i == 2) {
					if (gamePads[i] instanceof Xbox){
						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port2.add(new JoystickButton(gamePads[i], Xbox.BTN_RB));
						port2.add( new JoystickButton(gamePads[i], Xbox.BTN_LB));
						if (gamePads[i] instanceof Xbox) {
							L1_triggerR = gamePads[i].getLZ();// Right Trigger
							L1_triggerL = gamePads[i].getRZ();// left trigger
						} else {

						}
						port2.add(new JoystickButton(gamePads[i], Xbox.BTN_BACK));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2. add(new JoystickButton(gamePads[i], Xbox.BTN_START));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], Xbox.BTN_A));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], Xbox.BTN_X));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], Xbox.BTN_B));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], Xbox.BTN_Y));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_R));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_L));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
					}
					if (!(gamePads[i] instanceof Xbox)){
						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port2.add(new JoystickButton(gamePads[i], gamePads[i].BTN_RB));
						port2.add( new JoystickButton(gamePads[i], gamePads[i].BTN_LB));
						if (gamePads[i].isXbox) {
							L1_triggerR = gamePads[i].getLZ();// Right Trigger
							L1_triggerL = gamePads[i].getRZ();// left trigger
						} else {

						}
						port2.add(new JoystickButton(gamePads[i], gamePads[i].BTN_BACK));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2. add(new JoystickButton(gamePads[i], gamePads[i].BTN_START));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], gamePads[i].BTN_A));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], gamePads[i].BTN_X));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], gamePads[i].BTN_B));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], gamePads[i].BTN_Y));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], gamePads[i].AXISBTN_R));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], gamePads[i].AXISBTN_L));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
					}
				}
				if (i == 3) {
					if (gamePads[i] instanceof Xbox){
						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port3.add(new JoystickButton(gamePads[i], Xbox.BTN_RB));
						port3.add( new JoystickButton(gamePads[i], Xbox.BTN_LB));
						if (gamePads[i] instanceof Xbox) {
							L1_triggerR = gamePads[i].getLZ();// Right Trigger
							L1_triggerL = gamePads[i].getRZ();// left trigger
						} else {

						}
						port3.add(new JoystickButton(gamePads[i], Xbox.BTN_BACK));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3. add(new JoystickButton(gamePads[i], Xbox.BTN_START));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], Xbox.BTN_A));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], Xbox.BTN_X));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], Xbox.BTN_B));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], Xbox.BTN_Y));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_R));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_L));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
					}
					if (!(gamePads[i] instanceof Xbox)){
						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port3.add(new JoystickButton(gamePads[i], gamePads[i].BTN_RB));
						port3.add( new JoystickButton(gamePads[i], gamePads[i].BTN_LB));
						if (gamePads[i].isXbox) {
							L1_triggerR = gamePads[i].getLZ();// Right Trigger
							L1_triggerL = gamePads[i].getRZ();// left trigger
						} else {

						}
						port3.add(new JoystickButton(gamePads[i], gamePads[i].BTN_BACK));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3. add(new JoystickButton(gamePads[i], gamePads[i].BTN_START));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], gamePads[i].BTN_A));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], gamePads[i].BTN_X));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], gamePads[i].BTN_B));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], gamePads[i].BTN_Y));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], gamePads[i].AXISBTN_R));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], gamePads[i].AXISBTN_L));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
					}
				}
				if (i == 4) {
					if (gamePads[i] instanceof Xbox){
						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port4.add(new JoystickButton(gamePads[i], Xbox.BTN_RB));
						port4.add( new JoystickButton(gamePads[i], Xbox.BTN_LB));
						if (gamePads[i] instanceof Xbox) {
							L1_triggerR = gamePads[i].getLZ();// Right Trigger
							L1_triggerL = gamePads[i].getRZ();// left trigger
						} else {

						}
						port4.add(new JoystickButton(gamePads[i], Xbox.BTN_BACK));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4. add(new JoystickButton(gamePads[i], Xbox.BTN_START));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], Xbox.BTN_A));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], Xbox.BTN_X));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], Xbox.BTN_B));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], Xbox.BTN_Y));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_R));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_L));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
					}
					if (!(gamePads[i] instanceof Xbox)){
						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port4.add(new JoystickButton(gamePads[i], gamePads[i].BTN_RB));
						port4.add( new JoystickButton(gamePads[i], gamePads[i].BTN_LB));
						if (gamePads[i].isXbox) {
							L1_triggerR = gamePads[i].getLZ();// Right Trigger
							L1_triggerL = gamePads[i].getRZ();// left trigger
						} else {

						}
						port4.add(new JoystickButton(gamePads[i], gamePads[i].BTN_BACK));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4. add(new JoystickButton(gamePads[i], gamePads[i].BTN_START));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], gamePads[i].BTN_A));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], gamePads[i].BTN_X));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], gamePads[i].BTN_B));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], gamePads[i].BTN_Y));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], gamePads[i].AXISBTN_R));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], gamePads[i].AXISBTN_L));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
					}
				}

			if(i==5)
			{
				if (gamePads[i] instanceof Xbox){
					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
					port5.add(new JoystickButton(gamePads[i], Xbox.BTN_RB));
					port5.add( new JoystickButton(gamePads[i], Xbox.BTN_LB));
					if (gamePads[i] instanceof Xbox) {
						L1_triggerR = gamePads[i].getLZ();// Right Trigger
						L1_triggerL = gamePads[i].getRZ();// left trigger
					} else {

					}
					port5.add(new JoystickButton(gamePads[i], Xbox.BTN_BACK));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5. add(new JoystickButton(gamePads[i], Xbox.BTN_START));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], Xbox.BTN_A));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], Xbox.BTN_X));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], Xbox.BTN_B));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], Xbox.BTN_Y));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_R));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_L));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
				}
				if (!(gamePads[i] instanceof Xbox)){
					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
					port5.add(new JoystickButton(gamePads[i], gamePads[i].BTN_RB));
					port5.add( new JoystickButton(gamePads[i], gamePads[i].BTN_LB));
					if (gamePads[i].isXbox) {
						L1_triggerR = gamePads[i].getLZ();// Right Trigger
						L1_triggerL = gamePads[i].getRZ();// left trigger
					} else {

					}
					port5.add(new JoystickButton(gamePads[i], gamePads[i].BTN_BACK));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5. add(new JoystickButton(gamePads[i], gamePads[i].BTN_START));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], gamePads[i].BTN_A));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], gamePads[i].BTN_X));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], gamePads[i].BTN_B));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], gamePads[i].BTN_Y));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], gamePads[i].AXISBTN_R));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], gamePads[i].AXISBTN_L));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
				}
			}
			}
		}}
	public void runThreads()
	{
		right = new Thread() {
			public void run() {
				while (running) {
					double XRX = 0;
					double XRY = 0;
					double GenRY = 0;
					double GenRX = 0;
					try {
						for (int i = ports.size()-1; i >= 0; i--)// possibly
																	// better
																	// way to
																	// sort
																	// using
																	// comparator

						{
							if (gamePads[ports.get(i)] instanceof Xbox && ports.get(i)!=5) {//EXAMPLE -- currently excluding xbox controller in port 5 for other uses
								if (abs(XRX) < abs(gamePads[ports.get(i)].getRX()))
									XRX = gamePads[ports.get(i)].getRX();
								if (abs(XRY) < abs(gamePads[ports.get(i)].getRY()))
									XRY = gamePads[ports.get(i)].getRY();
							} else if (!(gamePads[ports.get(i)] instanceof Xbox)) {//if logitech or Generic HID
								if (abs(GenRX) < abs(gamePads[ports.get(i)].getRX()))
									GenRX = gamePads[ports.get(i)].getRX();
								if (abs(GenRY) < abs(gamePads[i].getRY()))
									GenRY = gamePads[ports.get(i)].getRY();
							}
						} // alternative method would be to actively sort and
							// compare gamepads/xbox controllers but could cause
							// conflicts with other parallel requests
						if (abs(GenRX) > abs(XRX) && abs(GenRX) > 0.1) {
							activeRX = GenRX;
						} else
							activeRX = XRX;

						if (abs(GenRY) > abs(XRY) && abs(GenRY) > 0.1) {
							activeRY = GenRY;
						} else
							activeRY = XRY;

					} catch (Exception e) {
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						String error = "Right Hand Controller Glitch";
								error.concat(errors.toString());
						DriverStation.reportError(error, true);
						activeRX = 0.0;
						activeRY = 0.0;
						System.out.println("ERROR Right HAND");
						System.out.println("ERROR Right HAND");
						updateControllerList();
					}
					System.out.println("ActiveRY"+activeRY);
				}
				activeRX = 0.0;// when running set to false
				activeRY = 0.0;
			}
			public double abs(double a)
			{
				if (a<0)
					a=-a;
				return a;
			}	};
		
	left = new Thread() {
		public void run() {
			while (running) {
				double XLX = 0;
				double XRY = 0;
				double GenRY = 0;
				double GenLX = 0;
				try {
					for (int i = ports.size()-1; i >= 0; i--)// possibly
																// better
																// way to
																// sort
																// using
																// comparator

					{
						if (gamePads[ports.get(i)] instanceof Xbox && ports.get(i)!=5) {//EXAMPLE -- currently excluding xbox controller in port 5 for other uses
							if (abs(XLX) < abs(gamePads[ports.get(i)].getRX()))
								XLX = gamePads[ports.get(i)].getRX();
							if (abs(XRY) < abs(gamePads[ports.get(i)].getRY()))
								XRY = gamePads[ports.get(i)].getRY();
						} else if (!(gamePads[ports.get(i)] instanceof Xbox)) {//if logitech or Generic HID
							if (abs(GenLX) < abs(gamePads[ports.get(i)].getRX()))
								GenLX = gamePads[ports.get(i)].getRX();
							if (abs(GenRY) < abs(gamePads[i].getRY()))
								GenRY = gamePads[ports.get(i)].getRY();
						}
					} // alternative method would be to actively sort and
						// compare gamepads/xbox controllers but could cause
						// conflicts with other parallel requests
					if (abs(GenLX) > abs(XLX) && abs(GenLX) > 0.1) {
						activeLX = GenLX;
					} else
						activeLX = XLX;

					if (abs(GenRY) > abs(XRY) && abs(GenRY) > 0.1) {
						activeLY = GenRY;
					} else
						activeLY = XRY;

				} catch (Exception e) {
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					String error = "Left Hand Controller Glitch";
							error.concat(errors.toString());
					DriverStation.reportError(error, true);
					activeLX = 0.0;
					activeLY = 0.0;
					System.out.println("ERROR Left HAND");
					System.out.println("ERROR Left HAND");
					updateControllerList();
				}
				System.out.println("ActiveRY"+activeLY);
			}
			activeLX = 0.0;// when running set to false
			activeLY = 0.0;
		}
		public double abs(double a)
		{
			if (a<0)
				a=-a;
			return a;
		}	};

	}
	

	/*

	 * enum BlockSort implements Comparator<GamePad> { LX {
	 * 
	 * @Override public int compare(GamePad b1, GamePad b2) { return (int)
	 * (abs(b1.getLX()) -abs(b2.getLX())); } },
	 * 
	 * LY {
	 * 
	 * @Override public int compare(GamePad b1, GamePad b2) { return (int)
	 * (abs(b1.getLY()) - abs(b2.getLY())); } } }
	 */

		
	
	
	public double getRawAnalogStickALX()
	{
		if (running)
			return activeLX;
		else 
			return 0.0;
		}

	

	public double getRawAnalogStickALY() {// left stick y-axis
		if (running)
			return activeLY;
		else 
			return 0.0;
		}
	

	public double getRawAnalogStickARX() {// Right stick x-axis
		if (running)
			return activeRX;
		else {
			return 0.0;
		}
	}

	public double getRawAnalogStickARY() {// Right stick y-axis
		if (running)
			return activeRY;
		else {
			return 0.0;
		}

	}

	public double getRawAnalogStickBRX() {
		return (xbox.getRawAxis(4));// left stick x-axis

	}
	
	
	
	/*public double getRawAnalogStickALX() {// left stick y-axis/////////////////////////////CODE FOR IF THREAD IS NOT USED
	//	if (left.isAlive())
		double XLX = 0;
		double GenLX=0;
		if (running=true){
		try {
			for (int i = ports.size()-1; i >= 0; i--)// possibly
														// better
														// way to
														// sort
														// using
														// comparator


			{
				if (gamePads[ports.get(i)].isXbox == true) {
					if (abs(XLX) < abs(gamePads[ports.get(i)].getLX()))
						XLX = gamePads[ports.get(i)].getLX();
					
				} else if (gamePads[ports.get(i)] != null && gamePads[ports.get(i)].isXbox == false) {
					if (abs(GenLX) < abs(gamePads[ports.get(i)].getLX()))
						{GenLX = gamePads[ports.get(i)].getLX();}
				}
			} // alternative method would be to actively sort and
				// compare gamepads/xbox controllers but could cause
				// conflicts with other parallel requests
			if (abs(GenLX) > abs(XLX) && abs(GenLX) > 0.1) {
				activeLX = GenLX;
			} else
				activeLX = XLX;

}
	catch (Exception e)
		{
//			StringWriter errors = new StringWriter();
//			e.printStackTrace(new PrintWriter(errors));
//			String error = "Controller GET ActiveLX Glitch";
//					error.concat(errors.toString());
//			DriverStation.reportError(error, true);
		updateControllerList();
		}
			}
		SmartDashboard.putNumber("ActiveLX", activeLX);
		return activeLX;
		
			}*/

}
