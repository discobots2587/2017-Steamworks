package org.discobots.steamworks;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.discobots.steamworks.commands.drive.CycleDriveCommand;
import org.discobots.steamworks.utils.GamePad;
import org.discobots.steamworks.utils.GamePad.DPadButton;
import org.discobots.steamworks.utils.GamePad.Hand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
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
	
	private Button g1_dpadD;
	private Button g1_dpadR;
	private Button g1_dpadL;
	private Button g1_bumpR;
	private Button g1_bumpL;
	public double g1_triggerR; 
	public double g1_triggerL;
	private Button g1_sBack;
	private Button g1_sStar;
	private Button g1_btnA;
	private Button g1_btnX;
	private Button g1_btnB;
	private Button g1_btnY;
	private Button g1_clicR; 
	private Button g1_clicL;
public ArrayList <Button> joystickButtons;
	public OI() {
		gamePads = new GamePad[6];
/*		left = new Thread() {
			public void run() {
				while (running) {
					double XLX = 0;
					double XLY = 0;
					double GenLY = 0;
					double GenLX = 0;
					try {
						for (int i = gamePads.length-1; i >= 0; i--)// possibly
																	// better
																	// way to
																	// sort
																	// using
																	// comparator

						{
							if (gamePads[i] != null && gamePads[i].isXbox == true) {
								if (Math.abs(XLX) < Math.abs(gamePads[i].getLX()))
									XLX = gamePads[i].getLX();
								if (Math.abs(XLY) < Math.abs(gamePads[i].getLY()))
									XLY = gamePads[i].getLY();
							} else if (gamePads[i] != null && gamePads[i].isXbox == false) {
								if (Math.abs(GenLX) < Math.abs(gamePads[i].getLX()))
									GenLX = gamePads[i].getLX();
								if (Math.abs(GenLY) < Math.abs(gamePads[i].getLY()))
									GenLY = gamePads[i].getLY();
							}
						} // alternative method would be to actively sort and
							// compare gamepads/xbox controllers but could cause
							// conflicts with other parallel requests
						if (Math.abs(GenLX) > Math.abs(XLX) && Math.abs(GenLX) > 0.1) {
							activeLX = GenLX;
						} else
							activeLX = XLX;

						if (Math.abs(GenLY) > Math.abs(XLY) && Math.abs(GenLY) > 0.1) {
							activeLY = GenLY;
						} else
							activeLY = XLY;

					} catch (Exception e) {
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						String error = "Controller Glitch";
								error.concat(errors.toString());
						DriverStation.reportError(error, true);

						activeLX = 0.0;
						activeLY = 0.0;

					}
				}
				activeLX = 0.0;// when running set to false
				activeLY = 0.0;
			}
		};

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
								if (Math.abs(XRX) < Math.abs(gamePads[i].getRX()))
									XRX = gamePads[i].getRX();
								if (Math.abs(XRY) < Math.abs(gamePads[i].getRY()))
									XRY = gamePads[i].getRY();
							} else if (gamePads[i] != null && gamePads[i].isXbox == false) {
								if (Math.abs(GenRX) < Math.abs(gamePads[i].getRX()))
									GenRX = gamePads[i].getRX();
								if (Math.abs(GenRY) < Math.abs(gamePads[i].getRY()))
									GenRY = gamePads[i].getRY();
							}
						} // alternative method would be to actively sort and
							// compare gamepads/xbox controllers but could cause
							// conflicts with other parallel requests
						if (Math.abs(GenRX) > Math.abs(XRX) && Math.abs(GenRX) > 0.1) {
							activeRX = GenRX;
						} else
							activeRX = XRX;

						if (Math.abs(GenRY) > Math.abs(XRY) && Math.abs(GenRY) > 0.1) {
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
		};
		while(!DriverStation.getInstance().isDSAttached())//wait for driverstation attachment
		{
			}
	*/	updateControllerList();
	}

	public void updateControllerList() {
		new Thread()
		{
			
		public void run(){
		numPads = 0;
		running = false;
		for (int i = 0; i <= 5; i++)// check all ports
		{
			try{
			if (DriverStation.getInstance().getStickAxisCount(i) == 6) {
				numPads++;
				gamePads[i]= new Xbox(i, true);
				SmartDashboard.putString("Controller Debug Name", gamePads[i].thegetName());
				SmartDashboard.putString("Xbox in Ports", SmartDashboard.getString("Xbox in Ports", "") + i + " ");
			} else if (DriverStation.getInstance().getStickAxisCount(i) >= 1) {
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
				DriverStation.reportError(error, false);
				gamePads[i]=null;

			}

		}
		}}.run();
		createMapping();
		running=true;
		//startThreads();
	}
	public void createMapping() {

		for (int i=5; i>=0; i--) {
		if (gamePads[i]!=null&&gamePads[i].getAxisCount()>0){
			if (gamePads[i].isXbox) {
					/*
					 * int temp; temp = gamePads[i].makebuttons(new
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
			
			if (!gamePads[i].isXbox) {
			}
			if (i == 1) {
				x1_dpadD = new DPadButton(gamePads[i], GamePad.DPAD_Y, false);
				x1_dpadR = new DPadButton(gamePads[i], GamePad.DPAD_X, true);
				x1_dpadL = new DPadButton(gamePads[i], GamePad.DPAD_X, false);
				x1_bumpR = new JoystickButton(gamePads[i], gamePads[i].BTN_RB);
				x1_bumpL = new JoystickButton(gamePads[i], gamePads[i].BTN_LB);
				if (gamePads[i].isXbox) {
					x1_triggerR = gamePads[i].getLZ();// Right Trigger
					x1_triggerL = gamePads[i].getRZ();// left trigger
				} else {

				}
				x1_sBack = new JoystickButton(gamePads[i], gamePads[i].BTN_BACK);
				x1_sStar = new JoystickButton(gamePads[i], gamePads[i].BTN_START);
				x1_btnA = new JoystickButton(gamePads[i], gamePads[i].BTN_A);
				x1_btnX = new JoystickButton(gamePads[i], gamePads[i].BTN_X);
				x1_btnB = new JoystickButton(gamePads[i], gamePads[i].BTN_B);
				x1_btnY = new JoystickButton(gamePads[i], gamePads[i].BTN_Y);
				x1_clicR = new JoystickButton(gamePads[i], gamePads[i].AXISBTN_R);
				x1_clicL = new JoystickButton(gamePads[i], gamePads[i].AXISBTN_L);
			}
		}
		}
	}

	

	// comparator if alternative is implemented
	/*
	 * enum BlockSort implements Comparator<GamePad> { LX {
	 * 
	 * @Override public int compare(GamePad b1, GamePad b2) { return (int)
	 * (Math.abs(b1.getLX()) -Math.abs(b2.getLX())); } },
	 * 
	 * LY {
	 * 
	 * @Override public int compare(GamePad b1, GamePad b2) { return (int)
	 * (Math.abs(b1.getLY()) - Math.abs(b2.getLY())); } } }
	 */
	public void startThreads() {
		left.run();
		right.run();
	}

	public double getRawAnalogStickALX() {// left stick y-axis
	//	if (left.isAlive())
		SmartDashboard.putNumber("ALX", activeLX);
			return activeLX;
		//else {
	//		return 0.0;
		
	}

	public double getRawAnalogStickALY() {// left stick y-axis
		if (true)
			return activeLY;
		else {
			return 0.0;
		}
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
