package org.discobots.steamworks.utils;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class GamePad extends Joystick { // implements Comparable<GamePad>
	private ArrayList<Button> buttons;
	public boolean isXbox = false;

	public GamePad(int port, boolean isbox) {
		super(port);
		this.isXbox=isbox;
		buttons = new ArrayList<Button>();
	}

	private static String name = "genericHID";
	/***** MODE D CONFIGURATION *****/
	// Axis
	public final   int AXIS_LX = 0;
	public final   int AXIS_LY = 1;
	public final   int AXIS_RX = 2;
	public final   int AXIS_RY = 3;
	public final static   int DPAD_X = 4;
	public final static   int DPAD_Y = 5;
	// Buttons
	public   final int BTN_X = 1;
	public   final int BTN_A = 2;
	public   final int BTN_B = 3;
	public   final int BTN_Y = 4;

	public final int BTN_LB = 5;
	public   final int BTN_LT = 7;
	public final int BTN_RB = 6;
	public   final int BTN_RT = 8;

	public   final int BTN_BACK = 9;
	public   final int BTN_START = 10;

	public   final int AXISBTN_L = 11;
	public   final int AXISBTN_R = 12;

	public double getLZ() {
		// returns xbox left trigger
return 0;
}

	public double getRZ() {
		// returns xbox right trigger
return 0;
		}
	
	public double getLX() {
		return this.getRawAxis(AXIS_LX);
	}

	public double getLY() {
		return this.getRawAxis(AXIS_LY) * -1;
	}

	public double getRX() {
		return this.getRawAxis(AXIS_RX);
	}

	public double getRY() {
		return this.getRawAxis(AXIS_RY) * -1;
	}

	public double getDX() {
		return this.getRawAxis(DPAD_X);
	}

	public double getDY() {
		return this.getRawAxis(DPAD_Y) * -1;
	}

	public static class DPadButton extends Button {
		public static final double kDefaultThreshold = 0.7;
		private GamePad m_gp;
		private int m_buttonAxis;
		private boolean positive;

		public DPadButton(GamePad gp, int buttonAxis, boolean positive) {
			this(gp, buttonAxis, kDefaultThreshold, positive);
		}

		public DPadButton(GamePad gp, int buttonAxis, double threshold, boolean positive) {
			m_gp = gp;
			m_buttonAxis = buttonAxis;
			this.positive = positive;
		}

		public void setThreshold(double threshold) {
		}

		public boolean get() {

			if (positive) {
				if (m_buttonAxis == GamePad.DPAD_X) {
					return m_gp.getPOV() == 90;
				} else if (m_buttonAxis == GamePad.DPAD_Y) {
					return m_gp.getPOV() == 0;
				}
			} else {
				if (m_buttonAxis == GamePad.DPAD_X) {
					return m_gp.getPOV() == 270;
				} else if (m_buttonAxis == GamePad.DPAD_Y) {
					return m_gp.getPOV() == 180;
				}
			}
			return false;
		}

	}

	public int compareTo(GamePad arg0) {
		if ((this.isXbox == true && arg0.isXbox == true) || (this.isXbox == false && arg0.isXbox == false))
			return 0;
		if (this.isXbox == false && arg0.isXbox == true)
			return -1;
		else
			return 1;
	}

	public int makebuttons(Button but) {
		buttons.add(but);
		return buttons.size();
	}

	public int makebuttons(int i, boolean pad) {
		buttons.add(new DPadButton(this, i, pad));
		return buttons.size();
	}

	public Button getButton(int index) {
		return buttons.get(index);
	}

	public static enum Hand {
		LEFT, RIGHT
	}

	public void setRumble(Hand hand, double intensity) {// does nothing
		// TODO Auto-generated method stub

	}

	public String thegetName() {
		return name;
	}
}
