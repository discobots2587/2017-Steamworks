package org.discobots.steamworks;

import java.io.PrintWriter;
import java.io.StringWriter;
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

	public OI() {
		gamePads = new GamePad[6];
		left = new Thread() {
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
		while(!DriverStation.getInstance().isDSAttached())
		{
		}
		updateControllerList();
	}

	public void createMapping() {

		for (GamePad i : gamePads) {
			try{
		if (i!=null&&i.getAxisCount()>0){
			if (i.isXbox) {
				i.getButton(i.makebuttons(new DPadButton(i, GamePad.DPAD_Y, false)))
						.toggleWhenPressed(new CycleDriveCommand());
				i.getButton(i.makebuttons(new JoystickButton(i, Xbox.BTN_BACK)))
						.toggleWhenPressed(new CycleDriveCommand());
			} else if (!i.isXbox) {

			}
		}
	} catch(Exception e)
			{
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		String error = "Controller Glitch";
				error.concat(errors.toString());
		DriverStation.reportError(error, true);
			}
		}
			}

	public void updateControllerList() {
		numPads = 0;
		running = false;
		for (int i = 0; i <= 5; i++)// check all ports
		{
			try{
			if (DriverStation.getInstance().getStickAxisCount(i) == 6) {
				numPads++;
				gamePads[i]= new Xbox(i);
				SmartDashboard.putString("Xbox in Ports", SmartDashboard.getString("Xbox in Ports", "") + i + " ");
			} else if (DriverStation.getInstance().getStickAxisCount(i) >= 1) {
				gamePads[i]= new GamePad(i);
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
		createMapping();
		running=true;
		startThreads();
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
		if (left.isAlive())
			return activeLX;
		else {
			updateControllerList();
			return 0.0;
		}
	}

	public double getRawAnalogStickALY() {// left stick y-axis
		if (left.isAlive())
			return activeLY;
		else {
			updateControllerList();
			return 0.0;
		}
	}

	public double getRawAnalogStickARX() {// Right stick x-axis
		if (left.isAlive())
			return activeRX;
		else {
			updateControllerList();
			return 0.0;
		}
	}

	public double getRawAnalogStickARY() {// Right stick y-axis
		if (left.isAlive())
			return activeRY;
		else {
			updateControllerList();
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
