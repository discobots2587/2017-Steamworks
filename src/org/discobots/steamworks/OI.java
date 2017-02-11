package org.discobots.steamworks;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.discobots.steamworks.commands.drive.CycleDriveCommand;
import org.discobots.steamworks.utils.GamePad;
import org.discobots.steamworks.utils.GamePad.DPadButton;
import org.discobots.steamworks.utils.GamePad.Hand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.discobots.steamworks.utils.Xbox;

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
	
	
	private Button x0_dpadD;
	private Button x0_dpadR;
	private Button x0_dpadL;
	private Button x0_bumpR;
	private Button x0_bumpL;
	public double x0_triggerR; 
	public double x0_triggerL;
	private Button x0_sBack;
	private Button x0_sStar;
	private Button x0_btnA;
	private Button x0_btnX;
	private Button x0_btnB;
	private Button x0_btnY;
	private Button x0_clicR; 
	private Button x0_clicL;
	
	private Button x1_dpadD;
	private Button x1_dpadR;
	private Button x1_dpadL;
	private Button x1_bumpR;
	private Button x1_bumpL;
	public double x1_triggerR; 
	public double x1_triggerL;
	private Button x1_sBack;
	private Button x1_sStar;
	private Button x1_btnA;
	private Button x1_btnX;
	private Button x1_btnB;
	private Button x1_btnY;
	private Button x1_clicR; 
	private Button x1_clicL;
	
	
	private Button x2_dpadD;
	private Button x2_dpadR;
	private Button x2_dpadL;
	private Button x2_bumpR;
	private Button x2_bumpL;
	public double x2_triggerR; 
	public double x2_triggerL;
	private Button x2_sBack;
	private Button x2_sStar;
	private Button x2_btnA;
	private Button x2_btnX;
	private Button x2_btnB;
	private Button x2_btnY;
	private Button x2_clicR; 
	private Button x2_clicL;
	
	private Button x3_dpadD;
	private Button x3_dpadR;
	private Button x3_dpadL;
	private Button x3_bumpR;
	private Button x3_bumpL;
	public double x3_triggerR; 
	public double x3_triggerL;
	private Button x3_sBack;
	private Button x3_sStar;
	private Button x3_btnA;
	private Button x3_btnX;
	private Button x3_btnB;
	private Button x3_btnY;
	private Button x3_clicR; 
	private Button x3_clicL;
	
	private Button x4_dpadD;
	private Button x4_dpadR;
	private Button x4_dpadL;
	private Button x4_bumpR;
	private Button x4_bumpL;
	public double x4_triggerR; 
	public double x4_triggerL;
	private Button x4_sBack;
	private Button x4_sStar;
	private Button x4_btnA;
	private Button x4_btnX;
	private Button x4_btnB;
	private Button x4_btnY;
	private Button x4_clicR; 
	private Button x4_clicL;
	
	private Button x5_dpadD;
	private Button x5_dpadR;
	private Button x5_dpadL;
	private Button x5_bumpR;
	private Button x5_bumpL;
	public double x5_triggerR; 
	public double x5_triggerL;
	private Button x5_sBack;
	private Button x5_sStar;
	private Button x5_btnA;
	private Button x5_btnX;
	private Button x5_btnB;
	private Button x5_btnY;
	private Button x5_clicR; 
	private Button x5_clicL;
	
	private Button L1_dpadD;
	private Button L1_dpadR;
	private Button L1_dpadL;
	private Button L1_bumpR;
	private Button L1_bumpL;
	public double L1_triggerR; 
	public double L1_triggerL;
	private Button L1_sBack;
	private Button L1_sStar;
	private Button L1_btnA;
	private Button L1_btnX;
	private Button L1_btnB;
	private Button L1_btnY;
	private Button L1_clicR; 
	private Button L1_clicL;
public ArrayList<Button> port0;
public ArrayList<Button> port1;
public ArrayList<Button> port2;
public ArrayList<Button> port3;
public ArrayList<Button> port4;
public ArrayList<Button> port5;

public ArrayList <Button> joystickButtons;
private ArrayList<Integer> ports;
	public OI() {
		port0 = new ArrayList<Button>();
		port1 = new ArrayList<Button>();
		port2 = new ArrayList<Button>();
		port3 = new ArrayList<Button>();
		port4 = new ArrayList<Button>();
		port5 = new ArrayList<Button>();

		ports = new ArrayList<Integer>();
		gamePads = new GamePad[6];
		
		
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
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_Y, false));
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_X, true));
						port1.add(new DPadButton(gamePads[i], GamePad.DPAD_X, false));
						port1.add(new JoystickButton(gamePads[i], gamePads[i].BTN_RB));
						port1.add( new JoystickButton(gamePads[i], gamePads[i].BTN_LB));
						if (gamePads[i] instanceof Xbox) {
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
						System.out.println("Joystick RIGHT"+gamePads[i].AXISBTN_R);
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
						port1.add( new JoystickButton(gamePads[i], gamePads[i].AXISBTN_L));
						port1.get(port1.size()-1).whenPressed(new CycleDriveCommand());
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

				if (!gamePads[i].isXbox) {// ability to create entirely separate
											// control scheme for non xbox
											// controller even if in same port

					if (i == 0) {// Can also differentiate by specific port set
									// in DriverStation Software
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

				if (i == 1) {
					x2_dpadD = new DPadButton(gamePads[i], GamePad.DPAD_Y, false);
					x2_dpadR = new DPadButton(gamePads[i], GamePad.DPAD_X, true);
					x2_dpadL = new DPadButton(gamePads[i], GamePad.DPAD_X, false);
					x2_bumpR = new JoystickButton(gamePads[i], gamePads[i].BTN_RB);
					x2_bumpL = new JoystickButton(gamePads[i], gamePads[i].BTN_LB);
					if (gamePads[i].isXbox) {
						x2_triggerR = gamePads[i].getLZ();// Right Trigger
						x2_triggerL = gamePads[i].getRZ();// left trigger
					} else {

					}
					x2_sBack = new JoystickButton(gamePads[i], gamePads[i].BTN_BACK);
					x2_sStar = new JoystickButton(gamePads[i], gamePads[i].BTN_START);
					x2_btnA = new JoystickButton(gamePads[i], gamePads[i].BTN_A);
					x2_btnX = new JoystickButton(gamePads[i], gamePads[i].BTN_X);
					x2_btnB = new JoystickButton(gamePads[i], gamePads[i].BTN_B);
					x2_btnY = new JoystickButton(gamePads[i], gamePads[i].BTN_Y);
					x2_clicR = new JoystickButton(gamePads[i], gamePads[i].AXISBTN_R);
					x2_clicL = new JoystickButton(gamePads[i], gamePads[i].AXISBTN_L);
				}

				if (i == 2) {
					x3_dpadD = new DPadButton(gamePads[i], GamePad.DPAD_Y, false);
					x3_dpadR = new DPadButton(gamePads[i], GamePad.DPAD_X, true);
					x3_dpadL = new DPadButton(gamePads[i], GamePad.DPAD_X, false);
					x3_bumpR = new JoystickButton(gamePads[i], gamePads[i].BTN_RB);
					x3_bumpL = new JoystickButton(gamePads[i], gamePads[i].BTN_LB);
					if (gamePads[i].isXbox) {
						x3_triggerR = gamePads[i].getLZ();// Right Trigger
						x3_triggerL = gamePads[i].getRZ();// left trigger
					} else {

					}
					x3_sBack = new JoystickButton(gamePads[i], gamePads[i].BTN_BACK);
					x3_sStar = new JoystickButton(gamePads[i], gamePads[i].BTN_START);
					x3_btnA = new JoystickButton(gamePads[i], gamePads[i].BTN_A);
					x3_btnX = new JoystickButton(gamePads[i], gamePads[i].BTN_X);
					x3_btnB = new JoystickButton(gamePads[i], gamePads[i].BTN_B);
					x3_btnY = new JoystickButton(gamePads[i], gamePads[i].BTN_Y);
					x3_clicR = new JoystickButton(gamePads[i], gamePads[i].AXISBTN_R);
					x3_clicL = new JoystickButton(gamePads[i], gamePads[i].AXISBTN_L);
				}
				if (i == 3) {
					x4_dpadD = new DPadButton(gamePads[i], GamePad.DPAD_Y, false);
					x4_dpadR = new DPadButton(gamePads[i], GamePad.DPAD_X, true);
					x4_dpadL = new DPadButton(gamePads[i], GamePad.DPAD_X, false);
					x4_bumpR = new JoystickButton(gamePads[i], gamePads[i].BTN_RB);
					x4_bumpL = new JoystickButton(gamePads[i], gamePads[i].BTN_LB);
					if (gamePads[i].isXbox) {
						x4_triggerR = gamePads[i].getLZ();// Right Trigger
						x4_triggerL = gamePads[i].getRZ();// left trigger
					} else {

					}
					x4_sBack = new JoystickButton(gamePads[i], gamePads[i].BTN_BACK);
					x4_sStar = new JoystickButton(gamePads[i], gamePads[i].BTN_START);
					x4_btnA = new JoystickButton(gamePads[i], gamePads[i].BTN_A);
					x4_btnX = new JoystickButton(gamePads[i], gamePads[i].BTN_X);
					x4_btnB = new JoystickButton(gamePads[i], gamePads[i].BTN_B);
					x4_btnY = new JoystickButton(gamePads[i], gamePads[i].BTN_Y);
					x4_clicR = new JoystickButton(gamePads[i], gamePads[i].AXISBTN_R);
					x4_clicL = new JoystickButton(gamePads[i], gamePads[i].AXISBTN_L);
				}
				if (i == 4) {
					x5_dpadD = new DPadButton(gamePads[i], GamePad.DPAD_Y, false);
					x5_dpadR = new DPadButton(gamePads[i], GamePad.DPAD_X, true);
					x5_dpadL = new DPadButton(gamePads[i], GamePad.DPAD_X, false);
					x5_bumpR = new JoystickButton(gamePads[i], gamePads[i].BTN_RB);
					x5_bumpL = new JoystickButton(gamePads[i], gamePads[i].BTN_LB);
					if (gamePads[i].isXbox) {
						x5_triggerR = gamePads[i].getLZ();// Right Trigger
						x5_triggerL = gamePads[i].getRZ();// left trigger
					} else {

					}
					x5_sBack = new JoystickButton(gamePads[i], gamePads[i].BTN_BACK);
					x5_sStar = new JoystickButton(gamePads[i], gamePads[i].BTN_START);
					x5_btnA = new JoystickButton(gamePads[i], gamePads[i].BTN_A);
					x5_btnX = new JoystickButton(gamePads[i], gamePads[i].BTN_X);
					x5_btnB = new JoystickButton(gamePads[i], gamePads[i].BTN_B);
					x5_btnY = new JoystickButton(gamePads[i], gamePads[i].BTN_Y);
					x5_clicR = new JoystickButton(gamePads[i], gamePads[i].AXISBTN_R);
					x5_clicL = new JoystickButton(gamePads[i], gamePads[i].AXISBTN_L);
				}

			}
		}
	}

	// comparator if alternative is implemented
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
/*
		right = new Thread() {
			public void run() {
				while (running) {
					double XRX = 0;
					double XRY = 0;
					double GenRY = 0;
					double GenRX = 0;
					try {
						for (int i = gamePads.length; i >= 0; i--)// possibly
																	// better
																	// way to
																	// sort
																	// using
																	// comparator

						{
							if (gamePads[i] != null && gamePads[i].isXbox == true) {
								if (abs(XRX) < abs(gamePads[i].getRX()))
									XRX = gamePads[i].getRX();
								if (abs(XRY) < abs(gamePads[i].getRY()))
									XRY = gamePads[i].getRY();
							} else if (gamePads[i] != null && gamePads[i].isXbox == false) {
								if (abs(GenRX) < abs(gamePads[i].getRX()))
									GenRX = gamePads[i].getRX();
								if (abs(GenRY) < abs(gamePads[i].getRY()))
									GenRY = gamePads[i].getRY();
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
						String error = "Controller Glitch";
								error.concat(errors.toString());
						DriverStation.reportError(error, true);
						activeRX = 0.0;
						activeRY = 0.0;
						System.out.println("ERROR LEFT HAND");
						System.out.println("ERROR LEFT HAND");

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
			}	*/
		
public double abs(double input)
{
	if(input<0)
		return -input;
	return input;
}

	public double getRawAnalogStickALX() {// left stick y-axis
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
		
			}

	public double getRawAnalogStickALY() {// left stick y-axis
		if (true)
			return activeLY;
		else 
			return 0.0;
		}
	

	public double getRawAnalogStickARX() {// Right stick x-axis
		if (true)
			return activeRX;
		else {
			return 0.0;
		}
	}

	public double getRawAnalogStickARY() {// Right stick y-axis
		if (true)
			return activeRY;
		else {
			return 0.0;
		}
	}

	public void setRumble(Hand hand, double intensity) { // set for single side
															// of controller
		for (GamePad i : gamePads) {
			if (i.isXbox)
				i.setRumble(hand, intensity); // set for single side of
												// controller
		}
	}

	/*
	 * public double getRawAnalogStickBLX() { return (xbox.getRawAxis(0));//
	 * left stick x-axis }
	 * 
	 * public double getRawAnalogStickBLY() { return (-xbox.getRawAxis(1));//
	 * left stick y-axis
	 * 
	 * }
	 * 
	 * public double getRawAnalogStickBRX() { return (xbox.getRawAxis(4));//
	 * right stick x-axis
	 * 
	 * }
	 * 
	 * public double getRawAnalogStickBRY() { return (xbox.getRawAxis(5));//
	 * right stick x-axis
	 * 
	 * } public double getRT(){
	 * if(gp1.getRawAxis(3)<-0.1||gp1.getRawAxis(3)>0.1) return
	 * gp1.getRawAxis(3); else return (xbox.getRawAxis(3));
	 * 
	 * } public double getLT(){
	 * if(gp1.getRawAxis(2)<-0.1||gp1.getRawAxis(2)>0.1) return
	 * gp1.getRawAxis(2); else return (xbox.getRawAxis(2)); }
	 */
}
// }
