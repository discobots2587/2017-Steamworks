package org.discobots.steamworks;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;


import org.discobots.steamworks.commands.drive.ArcadeDriveCommand;
import org.discobots.steamworks.commands.drive.CycleDriveCommand;
import org.discobots.steamworks.commands.drive.SpeedScaleCommand;
import org.discobots.steamworks.commands.hang.HangCommand;
import org.discobots.steamworks.commands.intake.GearIntakeCommand;
import org.discobots.steamworks.commands.intake.IntakeCommand;
import org.discobots.steamworks.commands.shoot.BlendCommand;
import org.discobots.steamworks.commands.shoot.ShootCommand;
import org.discobots.steamworks.commands.utils.ToggleCompressor;
import org.discobots.steamworks.utils.GamePad;
import org.discobots.steamworks.utils.GamePad.DPadButton;
import org.discobots.steamworks.utils.GamePad.Hand;
import org.discobots.steamworks.utils.TriggerToggle;
import org.discobots.steamworks.utils.Xbox;

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
	public Thread left;
	public Thread right;
	private double activeLX = 0.0;
	private double activeLY = 0.0;
	private double activeRY = 0.0;
	private double activeRX = 0.0;
	public boolean running = true;
	private double activeRT=0.0;
	private double activeLT=0.0;


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
		

		
		
		right = new Thread() {
			public void run() {
				while (true){
				while (running) {//loops
					double XRX = 0;
					double XRY = 0;
					double GenRY = 0;
					double GenRX = 0;
					double XRT=0;
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
								if (abs(XRT) < abs(gamePads[ports.get(i)].getRZ())){
									XRT=gamePads[ports.get(i)].getRZ();
								}
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
						
							activeRT = XRT;						

					} catch (Exception e) {
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						String error = "Right Hand Controller Glitch";
								error.concat(errors.toString());
						if(running)
						DriverStation.reportError(error, true);
						activeRX = 0.0;
						activeRY = 0.0;
						System.out.println("ERROR Right HAND");
						System.out.println("ERROR Right HAND");
						updateControllerList();
					}
				}
				activeRT=0;
				activeRX = 0.0;// when running set to false
				activeRY = 0.0;
				}
				
			}
			public double abs(double a)
			{
				if (a<0)
					a=-a;
				return a;
			}	};
		
	left = new Thread() {
		public void run() {
			while(true){
			while (running) {//loops
				double XLX = 0;
				double XLY = 0;
				double GenLX = 0;
				double GenLY = 0;
				double XLT =0;
				try {
					for (int i = ports.size()-1; i >= 0; i--)// possibly
																// better
																// way to
																// sort
																// using
																// comparator

					{
						if (gamePads[ports.get(i)] instanceof Xbox && ports.get(i)!=5) {//EXAMPLE -- currently excluding xbox controller in port 5 for other uses
							if (abs(XLX) < abs(gamePads[ports.get(i)].getLX()))
								XLX = gamePads[ports.get(i)].getLX();
							if (abs(XLY) < abs(gamePads[ports.get(i)].getLY()))
								XLY = gamePads[ports.get(i)].getLY();
							if (abs(XLT) < abs(gamePads[ports.get(i)].getLZ()))
										XLT=gamePads[ports.get(i)].getLZ();
						} else if (!(gamePads[ports.get(i)] instanceof Xbox)) {//if logitech or Generic HID
							if (abs(GenLX) < abs(gamePads[ports.get(i)].getLX()))
								GenLX = gamePads[ports.get(i)].getLX();
							if (abs(GenLY) < abs(gamePads[i].getLY()))
								GenLY = gamePads[ports.get(i)].getLY();
						}
					} // alternative method would be to actively sort and
						// compare gamepads/xbox controllers but could cause
						// conflicts with other parallel requests
					if (abs(GenLX) > abs(XLX) && abs(GenLX) > 0.1) {
						activeLX = GenLX;
					} else
						activeLX = XLX;

					if (abs(GenLY) > abs(XLY) && abs(GenLY) > 0.1) {
						activeLY = GenLY;
					} else
						activeLY = XLY;

						activeLT = XLT;

				} catch (Exception e) {
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					String error = "Left Hand Controller Glitch";
							error.concat(errors.toString());
					if(running)
					DriverStation.reportError(error, true);
					activeLX = 0.0;
					activeLY = 0.0;
					System.out.println("ERROR Left HAND");
					System.out.println("ERROR Left HAND");
					updateControllerList();
				}
			}activeLX = 0.0;activeLY = 0.0;activeRT=0.0;}
			// when running set to false
		}
		public double abs(double a)
		{
			if (a<0)
				a=-a;
			return a;
		}	};
		updateControllerList();
		StartThreads();
	}
	
	
	public void StartThreads()
	{
		if (!left.isAlive())
			left.start();
		if(!right.isAlive())
			right.start();

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
		}}.start();
		createMapping();
		running=true;
		if (!left.isAlive())
		{
			left.start();
		}
		if(!right.isAlive())
		{
			right.start();
	}}

	public void createMapping() {
		port0.clear();
		port1.clear();
		port2.clear();
		port3.clear();
		port4.clear();
		port5.clear();
		for (int i = 5; i >= 0; i--) {
			if (gamePads[i] instanceof GamePad && gamePads[i].getAxisCount() > 0) {
											// control scheme for xbox
											// controller even if in same port
					if (i == 0) {// Can also differentiate by specific port set
									// in DriverStation Software
						if (gamePads[i] instanceof Xbox) {// ability to create entirely separate

						port0.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port0.get(port0.size()-1).whenPressed(new IntakeCommand(1,true,10000));//ten seconds of intaking toggle
						port0.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, true));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port0.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port0.add(new JoystickButton(gamePads[i], Xbox.BTN_RB));
						port0.get(port0.size()-1).whenPressed(new ShootCommand(true));//toggles at 100% speed
						port0.add( new JoystickButton(gamePads[i], Xbox.BTN_LB));
						port0.get(port0.size()-1).whenPressed(new BlendCommand(-.75, true));//negative is blend into shooter currently//toggles
						port0.add(new JoystickButton(gamePads[i], Xbox.BTN_BACK));
						port0.get(port0.size()-1).whileHeld(new BlendCommand(.75));//in case blender gets jammed
						port0. add(new JoystickButton(gamePads[i], Xbox.BTN_START));
						port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
						port0.add( new JoystickButton(gamePads[i], Xbox.BTN_A));
						port0.get(port0.size()-1).whenPressed(new GearIntakeCommand());
						port0.add( new JoystickButton(gamePads[i], Xbox.BTN_X));
						port0.get(port0.size()-1).whileHeld(new ShootCommand(.75));//shoot 3/4 speed while held
						port0.add( new JoystickButton(gamePads[i], Xbox.BTN_B));
						port0.get(port0.size()-1).whenPressed(new ToggleCompressor());///Toggle Compressor
						port0.add( new JoystickButton(gamePads[i], Xbox.BTN_Y));
						port0.get(port0.size()-1).toggleWhenPressed(new HangCommand());//hanging on Y
						port0.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_R));
						port0.get(port0.size()-1).whenReleased(new SpeedScaleCommand(true));//turning speedscale
						port0.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_L));
						port0.get(port0.size()-1).whenReleased(new SpeedScaleCommand(false));//direct speedscale
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
					
					port0.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
					port0.get(port0.size()-1).whenPressed(new IntakeCommand(1,true,10000));//ten seconds of intaking toggle
					port0.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, true));
					port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
					port0.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
					port0.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
					port0.add(new JoystickButton(gamePads[i], GamePad.BTN_RB));
					port0.get(port0.size()-1).whenPressed(new ShootCommand(true));//toggles at 100% speed
					port0.add( new JoystickButton(gamePads[i], GamePad.BTN_LB));
					port0.get(port0.size()-1).whenPressed(new BlendCommand(-.75, true));//negative is blend into shooter currently//toggles
					port0.add(new JoystickButton(gamePads[i], GamePad.BTN_BACK));
					port0.get(port0.size()-1).whileHeld(new BlendCommand(.75));//in case blender gets jammed
					port0. add(new JoystickButton(gamePads[i], GamePad.BTN_START));
					port0.get(port0.size()-1).whenPressed(new CycleDriveCommand());
					port0.add( new JoystickButton(gamePads[i], GamePad.BTN_A));
					port0.get(port0.size()-1).whenPressed(new GearIntakeCommand());
					port0.add( new JoystickButton(gamePads[i], GamePad.BTN_X));
					port0.get(port0.size()-1).whileHeld(new ShootCommand(.75));//shoot 3/4 speed while held
					port0.add( new JoystickButton(gamePads[i], GamePad.BTN_B));
					port0.get(port0.size()-1).whenPressed(new ToggleCompressor());///Toggle Compressor
					port0.add( new JoystickButton(gamePads[i], GamePad.BTN_Y));
					port0.get(port0.size()-1).toggleWhenPressed(new HangCommand());//hanging on Y
					port0.add( new JoystickButton(gamePads[i], GamePad.AXISBTN_R));
					port0.get(port0.size()-1).whenReleased(new SpeedScaleCommand(true));//turning speedscale
					port0.add( new JoystickButton(gamePads[i], GamePad.AXISBTN_L));
					port0.get(port0.size()-1).whenReleased(new SpeedScaleCommand(false));//direct speedscale

					}
				}

				if (i == 1) {
					
					if (gamePads[i] instanceof Xbox){

						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port1.get(port1.size()-1).whenPressed(new IntakeCommand(1,true,10000));//ten seconds of intaking toggle
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, true));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port1.add(new JoystickButton(gamePads[i], Xbox.BTN_RB));
						port1.get(port1.size()-1).whenPressed(new ShootCommand(true));//toggles at 100% speed
						port1.add( new JoystickButton(gamePads[i], Xbox.BTN_LB));
						port1.get(port1.size()-1).whenPressed(new BlendCommand(-.75, true));//negative is blend into shooter currently//toggles
						port1.add(new JoystickButton(gamePads[i], Xbox.BTN_BACK));
						port1.get(port1.size()-1).whileHeld(new BlendCommand(.75));//in case blender gets jammed
						port1. add(new JoystickButton(gamePads[i], Xbox.BTN_START));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], Xbox.BTN_A));
						port1.get(port1.size()-1).whenPressed(new GearIntakeCommand());
						port1.add( new JoystickButton(gamePads[i], Xbox.BTN_X));
						port1.get(port1.size()-1).whileHeld(new ShootCommand(.75));//shoot 3/4 speed while held
						port1.add( new JoystickButton(gamePads[i], Xbox.BTN_B));
						port1.get(port1.size()-1).whenPressed(new ToggleCompressor());///Toggle Compressor
						port1.add( new JoystickButton(gamePads[i], Xbox.BTN_Y));
						port1.get(port1.size()-1).toggleWhenPressed(new HangCommand());//hanging on Y
						port1.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_R));
						port1.get(port1.size()-1).whenReleased(new SpeedScaleCommand(true));//turning speedscale
						port1.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_L));
						port1.get(port1.size()-1).whenReleased(new SpeedScaleCommand(false));//direct speedscale
						
					}
					if (!(gamePads[i] instanceof Xbox)){
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port1.get(port1.size()-1).whenPressed(new IntakeCommand(1,true,10000));//ten seconds of intaking toggle
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, true));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port1.add(new JoystickButton(gamePads[i], GamePad.BTN_RB));
						port1.get(port1.size()-1).whenPressed(new ShootCommand(true));//toggles at 100% speed
						port1.add( new JoystickButton(gamePads[i], GamePad.BTN_LB));
						port1.get(port1.size()-1).whenPressed(new BlendCommand(-.75, true));//negative is blend into shooter currently//toggles
						port1.add(new JoystickButton(gamePads[i], GamePad.BTN_BACK));
						port1.get(port1.size()-1).whileHeld(new BlendCommand(.75));//in case blender gets jammed
						port1. add(new JoystickButton(gamePads[i], GamePad.BTN_START));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], GamePad.BTN_A));
						port1.get(port1.size()-1).whenPressed(new GearIntakeCommand());
						port1.add( new JoystickButton(gamePads[i], GamePad.BTN_X));
						port1.get(port1.size()-1).whileHeld(new ShootCommand(.75));//shoot 3/4 speed while held
						port1.add( new JoystickButton(gamePads[i], GamePad.BTN_B));
						port1.get(port1.size()-1).whenPressed(new ToggleCompressor());///Toggle Compressor
						port1.add( new JoystickButton(gamePads[i], GamePad.BTN_Y));
						port1.get(port1.size()-1).toggleWhenPressed(new HangCommand());//hanging on Y
						port1.add( new JoystickButton(gamePads[i], GamePad.AXISBTN_R));
						port1.get(port1.size()-1).whenReleased(new SpeedScaleCommand(true));//turning speedscale
						port1.add( new JoystickButton(gamePads[i], GamePad.AXISBTN_L));
						port1.get(port1.size()-1).whenReleased(new SpeedScaleCommand(false));//direct speedscale

					}
				}

				if (i == 2) {
					if (gamePads[i] instanceof Xbox){

						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port2.get(port2.size()-1).whenPressed(new IntakeCommand(1,true,10000));//ten seconds of intaking toggle
						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, true));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port2.add(new JoystickButton(gamePads[i], Xbox.BTN_RB));
						port2.get(port2.size()-1).whenPressed(new ShootCommand(true));//toggles at 100% speed
						port2.add( new JoystickButton(gamePads[i], Xbox.BTN_LB));
						port2.get(port2.size()-1).whenPressed(new BlendCommand(-.75, true));//negative is blend into shooter currently//toggles
						port2.add(new JoystickButton(gamePads[i], Xbox.BTN_BACK));
						port2.get(port2.size()-1).whileHeld(new BlendCommand(.75));//in case blender gets jammed
						port2. add(new JoystickButton(gamePads[i], Xbox.BTN_START));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], Xbox.BTN_A));
						port2.get(port2.size()-1).whenPressed(new GearIntakeCommand());
						port2.add( new JoystickButton(gamePads[i], Xbox.BTN_X));
						port2.get(port2.size()-1).whileHeld(new ShootCommand(.75));//shoot 3/4 speed while held
						port2.add( new JoystickButton(gamePads[i], Xbox.BTN_B));
						port2.get(port2.size()-1).whenPressed(new ToggleCompressor());///Toggle Compressor
						port2.add( new JoystickButton(gamePads[i], Xbox.BTN_Y));
						port2.get(port2.size()-1).toggleWhenPressed(new HangCommand());//hanging on Y
						port2.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_R));
						port2.get(port2.size()-1).whenReleased(new SpeedScaleCommand(true));//turning speedscale
						port2.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_L));
						port2.get(port2.size()-1).whenReleased(new SpeedScaleCommand(false));//direct speedscale
					}
					if (!(gamePads[i] instanceof Xbox)){
						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port2.get(port2.size()-1).whenPressed(new IntakeCommand(1,true,10000));//ten seconds of intaking toggle
						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, true));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port2.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port2.add(new JoystickButton(gamePads[i], GamePad.BTN_RB));
						port2.get(port2.size()-1).whenPressed(new ShootCommand(true));//toggles at 100% speed
						port2.add( new JoystickButton(gamePads[i], GamePad.BTN_LB));
						port2.get(port2.size()-1).whenPressed(new BlendCommand(-.75, true));//negative is blend into shooter currently//toggles
						port2.add(new JoystickButton(gamePads[i], GamePad.BTN_BACK));
						port2.get(port2.size()-1).whileHeld(new BlendCommand(.75));//in case blender gets jammed
						port2. add(new JoystickButton(gamePads[i], GamePad.BTN_START));
						port2.get(port2.size()-1).whenPressed(new CycleDriveCommand());
						port2.add( new JoystickButton(gamePads[i], GamePad.BTN_A));
						port2.get(port2.size()-1).whenPressed(new GearIntakeCommand());
						port2.add( new JoystickButton(gamePads[i], GamePad.BTN_X));
						port2.get(port2.size()-1).whileHeld(new ShootCommand(.75));//shoot 3/4 speed while held
						port2.add( new JoystickButton(gamePads[i], GamePad.BTN_B));
						port2.get(port2.size()-1).whenPressed(new ToggleCompressor());///Toggle Compressor
						port2.add( new JoystickButton(gamePads[i], GamePad.BTN_Y));
						port2.get(port2.size()-1).toggleWhenPressed(new HangCommand());//hanging on Y
						port2.add( new JoystickButton(gamePads[i], GamePad.AXISBTN_R));
						port2.get(port2.size()-1).whenReleased(new SpeedScaleCommand(true));//turning speedscale
						port2.add( new JoystickButton(gamePads[i], GamePad.AXISBTN_L));
						port2.get(port2.size()-1).whenReleased(new SpeedScaleCommand(false));//direct speedscale

					}}
				
				if (i == 3) {
					if (gamePads[i] instanceof Xbox){

						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port3.get(port3.size()-1).whenPressed(new IntakeCommand(1,true,10000));//ten seconds of intaking toggle
						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, true));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port3.add(new JoystickButton(gamePads[i], Xbox.BTN_RB));
						port3.get(port3.size()-1).whenPressed(new ShootCommand(true));//toggles at 100% speed
						port3.add( new JoystickButton(gamePads[i], Xbox.BTN_LB));
						port3.get(port3.size()-1).whenPressed(new BlendCommand(-.75, true));//negative is blend into shooter currently//toggles
						port3.add(new JoystickButton(gamePads[i], Xbox.BTN_BACK));
						port3.get(port3.size()-1).whileHeld(new BlendCommand(.75));//in case blender gets jammed
						port3. add(new JoystickButton(gamePads[i], Xbox.BTN_START));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], Xbox.BTN_A));
						port3.get(port3.size()-1).whenPressed(new GearIntakeCommand()); 
						port3.add( new JoystickButton(gamePads[i], Xbox.BTN_X));
						port3.get(port3.size()-1).whileHeld(new ShootCommand(.75));//shoot 3/4 speed while held
						port3.add( new JoystickButton(gamePads[i], Xbox.BTN_B));
						port3.get(port3.size()-1).whenPressed(new ToggleCompressor());///Toggle Compressor
						port3.add( new JoystickButton(gamePads[i], Xbox.BTN_Y));
						port3.get(port3.size()-1).toggleWhenPressed(new HangCommand());//hanging on Y
						port3.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_R));
						port3.get(port3.size()-1).whenReleased(new SpeedScaleCommand(true));//turning speedscale
						port3.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_L));
						port3.get(port3.size()-1).whenReleased(new SpeedScaleCommand(false));//direct speedscale
					}
					if (!(gamePads[i] instanceof Xbox)){
						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port3.get(port3.size()-1).whenPressed(new IntakeCommand(1,true,10000));//ten seconds of intaking toggle
						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, true));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port3.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port3.add(new JoystickButton(gamePads[i], GamePad.BTN_RB));
						port3.get(port3.size()-1).whenPressed(new ShootCommand(true));//toggles at 100% speed
						port3.add( new JoystickButton(gamePads[i], GamePad.BTN_LB));
						port3.get(port3.size()-1).whenPressed(new BlendCommand(-.75, true));//negative is blend into shooter currently//toggles
						port3.add(new JoystickButton(gamePads[i], GamePad.BTN_BACK));
						port3.get(port3.size()-1).whileHeld(new BlendCommand(.75));//in case blender gets jammed
						port3. add(new JoystickButton(gamePads[i], GamePad.BTN_START));
						port3.get(port3.size()-1).whenPressed(new CycleDriveCommand());
						port3.add( new JoystickButton(gamePads[i], GamePad.BTN_A));
						port3.get(port3.size()-1).whenPressed(new GearIntakeCommand());
						port3.add( new JoystickButton(gamePads[i], GamePad.BTN_X));
						port3.get(port3.size()-1).whileHeld(new ShootCommand(.75));//shoot 3/4 speed while held
						port3.add( new JoystickButton(gamePads[i], GamePad.BTN_B));
						port3.get(port3.size()-1).whenPressed(new ToggleCompressor());///Toggle Compressor
						port3.add( new JoystickButton(gamePads[i], GamePad.BTN_Y));
						port3.get(port3.size()-1).toggleWhenPressed(new HangCommand());//hanging on Y
						port3.add( new JoystickButton(gamePads[i], GamePad.AXISBTN_R));
						port3.get(port3.size()-1).whenReleased(new SpeedScaleCommand(true));//turning speedscale
						port3.add( new JoystickButton(gamePads[i], GamePad.AXISBTN_L));
						port3.get(port3.size()-1).whenReleased(new SpeedScaleCommand(false));//direct speedscale

					}
				}
				if (i == 4) {
					if (gamePads[i] instanceof Xbox){

						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port4.get(port4.size()-1).whenPressed(new IntakeCommand(1,true,10000));//ten seconds of intaking toggle
						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, true));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port4.add(new JoystickButton(gamePads[i], Xbox.BTN_RB));
						port4.get(port4.size()-1).whenPressed(new ShootCommand(true));//toggles at 100% speed
						port4.add( new JoystickButton(gamePads[i], Xbox.BTN_LB));
						port4.get(port4.size()-1).whenPressed(new BlendCommand(-.75, true));//negative is blend into shooter currently//toggles
						port4.add(new JoystickButton(gamePads[i], Xbox.BTN_BACK));
						port4.get(port4.size()-1).whileHeld(new BlendCommand(.75));//in case blender gets jammed
						port4. add(new JoystickButton(gamePads[i], Xbox.BTN_START));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], Xbox.BTN_A));
						port4.get(port4.size()-1).whenPressed(new GearIntakeCommand()); 
						port4.add( new JoystickButton(gamePads[i], Xbox.BTN_X));
						port4.get(port4.size()-1).whileHeld(new ShootCommand(.75));//shoot 3/4 speed while held
						port4.add( new JoystickButton(gamePads[i], Xbox.BTN_B));
						port4.get(port4.size()-1).whenPressed(new ToggleCompressor());///Toggle Compressor
						port4.add( new JoystickButton(gamePads[i], Xbox.BTN_Y));
						port4.get(port4.size()-1).toggleWhenPressed(new HangCommand());//hanging on Y
						port4.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_R));
						port4.get(port4.size()-1).whenReleased(new SpeedScaleCommand(true));//turning speedscale
						port4.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_L));
						port4.get(port4.size()-1).whenReleased(new SpeedScaleCommand(false));//direct speedscale
					}
					if (!(gamePads[i] instanceof Xbox)){
						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port4.get(port4.size()-1).whenPressed(new IntakeCommand(1,true,10000));//ten seconds of intaking toggle
						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, true));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port4.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port4.add(new JoystickButton(gamePads[i], GamePad.BTN_RB));
						port4.get(port4.size()-1).whenPressed(new ShootCommand(true));//toggles at 100% speed
						port4.add( new JoystickButton(gamePads[i], GamePad.BTN_LB));
						port4.get(port4.size()-1).whenPressed(new BlendCommand(-.75, true));//negative is blend into shooter currently//toggles
						port4.add(new JoystickButton(gamePads[i], GamePad.BTN_BACK));
						port4.get(port4.size()-1).whileHeld(new BlendCommand(.75));//in case blender gets jammed
						port4. add(new JoystickButton(gamePads[i], GamePad.BTN_START));
						port4.get(port4.size()-1).whenPressed(new CycleDriveCommand());
						port4.add( new JoystickButton(gamePads[i], GamePad.BTN_A));
						port4.get(port4.size()-1).whenPressed(new GearIntakeCommand());
						port4.add( new JoystickButton(gamePads[i], GamePad.BTN_X));
						port4.get(port4.size()-1).whileHeld(new ShootCommand(.75));//shoot 3/4 speed while held
						port4.add( new JoystickButton(gamePads[i], GamePad.BTN_B));
						port4.get(port4.size()-1).whenPressed(new ToggleCompressor());///Toggle Compressor
						port4.add( new JoystickButton(gamePads[i], GamePad.BTN_Y));
						port4.get(port4.size()-1).toggleWhenPressed(new HangCommand());//hanging on Y
						port4.add( new JoystickButton(gamePads[i], GamePad.AXISBTN_R));
						port4.get(port4.size()-1).whenReleased(new SpeedScaleCommand(true));//turning speedscale
						port4.add( new JoystickButton(gamePads[i], GamePad.AXISBTN_L));
						port4.get(port4.size()-1).whenReleased(new SpeedScaleCommand(false));//direct speedscale

					}
				}

			if(i==5)
			{
				if (gamePads[i] instanceof Xbox){

					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
					port5.get(port5.size()-1).whenPressed(new IntakeCommand(1,true,10000));//ten seconds of intaking toggle
					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, true));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
					port5.add(new JoystickButton(gamePads[i], Xbox.BTN_RB));
					port5.get(port5.size()-1).whenPressed(new ShootCommand(true));//toggles at 100% speed
					port5.add( new JoystickButton(gamePads[i], Xbox.BTN_LB));
					port5.get(port5.size()-1).whenPressed(new BlendCommand(-.75, true));//negative is blend into shooter currently//toggles
					port5.add(new JoystickButton(gamePads[i], Xbox.BTN_BACK));
					port5.get(port5.size()-1).whileHeld(new BlendCommand(.75));//in case blender gets jammed
					port5. add(new JoystickButton(gamePads[i], Xbox.BTN_START));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], Xbox.BTN_A));
					port5.get(port5.size()-1).whenPressed(new GearIntakeCommand()); 
					port5.add( new JoystickButton(gamePads[i], Xbox.BTN_X));
					port5.get(port5.size()-1).whileHeld(new ShootCommand(.75));//shoot 3/4 speed while held
					port5.add( new JoystickButton(gamePads[i], Xbox.BTN_B));
					port5.get(port5.size()-1).whenPressed(new ToggleCompressor());///Toggle Compressor
					port5.add( new JoystickButton(gamePads[i], Xbox.BTN_Y));
					port5.get(port5.size()-1).toggleWhenPressed(new HangCommand());//hanging on Y
					port5.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_R));
					port5.get(port5.size()-1).whenReleased(new SpeedScaleCommand(true));//turning speedscale
					port5.add( new JoystickButton(gamePads[i], Xbox.AXISBTN_L));
					port5.get(port5.size()-1).whenReleased(new SpeedScaleCommand(false));//direct speedscale
				}
				if (!(gamePads[i] instanceof Xbox)){
					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, true));
					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
					port5.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
					port5.add(new JoystickButton(gamePads[i], gamePads[i].BTN_RB));
					port5.add( new JoystickButton(gamePads[i], gamePads[i].BTN_LB));
					port5.add(new JoystickButton(gamePads[i], gamePads[i].BTN_BACK));
					port5.add(new JoystickButton(gamePads[i], gamePads[i].BTN_START));
					port5.get(port5.size()-1).whenPressed(new CycleDriveCommand());
					port5.add( new JoystickButton(gamePads[i], gamePads[i].BTN_A));
					port5.get(port5.size()-1).whenPressed(new ShootCommand());
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
			}}}}

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
			return activeLX;
	}	

	

	public double getRawAnalogStickALY() {// left stick y-axis
			return activeLY;}
	

	public double getRawAnalogStickARX() {// Right stick x-axis
			return activeRX;

	}

	public double getRawAnalogStickARY() {// Right stick y-axis
			return activeRY;

	}
	public double getRawRT(){
	
		return activeRT;//for xbox controllers with variable trigger values
		
	}

	
	public double getRawLT(){
		return activeLT;//for xbox controllers with variable trigger values
		
	}

	public void setRumble(Hand hand, double rumbleFactor) {
		for (int i = ports.size()-1; i >= 0; i--)
		{
			try{
			if (gamePads[ports.get(i)] instanceof Xbox && ports.get(i)!=5)  {
					 gamePads[ports.get(i)].setRumble(hand, rumbleFactor);
					 
			}
				 }
		
	
			catch(Exception e){
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				String error = "ControllerRumbleGlitch";
						error.concat(errors.toString());
				DriverStation.reportError(error, true);
				System.out.println("ERROR Rumble");
				System.out.println("ERROR Rumble");
				}
	}}
	public void setRumble(double rumbleFactor) {

		for (int i = ports.size()-1; i >= 0; i--)
		{
			try{
			if (gamePads[ports.get(i)] instanceof Xbox && ports.get(i)!=5)  {
					 gamePads[ports.get(i)].setRumbleFull(rumbleFactor);
			}}
			
			catch(Exception e){
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				String error = "ControllerRumbleGlitch";
						error.concat(errors.toString());
				DriverStation.reportError(error, true);
				System.out.println("ERROR RumbleFull");
				System.out.println("ERROR RumbleFull");
				}
	}}

	
	
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
