package org.discobots.steamworks.utils;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.buttons.Button;

public class Xbox extends GamePad {

	public Xbox(int port) {
		super(port);
	}

	String name = "XboxOne";
	public boolean isXbox = true;
	// Axis
	public static final int AXIS_LX = 0;
	public static final int AXIS_LY = 1;
	public static final int AXIS_RX = 4;
	public static final int AXIS_RY = 5;
	public static final int DPAD_X = 4;// true is right on xbox
	public static final int DPAD_Y = 5;// true is up on xbox
	public static final int AXIS_LZ = 2;
	public static final int AXIS_RZ = 3;

	// Buttons
	public static final int BTN_X = 3;
	public static final int BTN_A = 1;
	public static final int BTN_B = 2;
	public static final int BTN_Y = 4;

	public static final int BTN_LB = 5;
	public static final int BTN_RB = 6;

	public static final int BTN_BACK = 7;
	public static final int BTN_START = 8;

	public static final int AXISBTN_L = 10;// when you click the joysticks
	public static final int AXISBTN_R = 9;

	public double getLZ() {
		// returns xbox left trigger
		return this.getRawAxis(AXIS_LZ);
	}

	public double getRZ() {
		// returns xbox right trigger
		return this.getRawAxis(AXIS_RZ);
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
		private Xbox m_gp;
		private int m_buttonAxis;
		private boolean positive;

		public DPadButton(Xbox gp, int buttonAxis, boolean positive) {
			this(gp, buttonAxis, kDefaultThreshold, positive);
		}

		public DPadButton(Xbox gp, int buttonAxis, double threshold, boolean positive) {
			m_gp = gp;
			m_buttonAxis = buttonAxis;
			this.positive = positive;
		}

		public void setThreshold(double threshold) {// nothing implemented
													// here...
		}

		public boolean get() {// not sure exactly what this does

			if (positive) {
				if (m_buttonAxis == Xbox.DPAD_X) {
					return m_gp.getPOV() == 90;
				} else if (m_buttonAxis == Xbox.DPAD_Y) {
					return m_gp.getPOV() == 0;
				}
			} else {
				if (m_buttonAxis == Xbox.DPAD_X) {
					return m_gp.getPOV() == 270;
				} else if (m_buttonAxis == Xbox.DPAD_Y) {
					return m_gp.getPOV() == 180;
				}
			}
			return false;
		}

	}

	public void setRumble(Hand hand, double intensity) { // set for single side
															// of controller

		final float amount = new Float(intensity);

		if (hand == Hand.LEFT) {
			this.setRumble(RumbleType.kLeftRumble, amount);
		}
		if (hand == Hand.RIGHT) {
			this.setRumble(RumbleType.kRightRumble, amount);
		}
	}

	public void setRumble(double intensity) { // set rumble for both hands
		final float amount = new Float(intensity);

		this.setRumble(RumbleType.kLeftRumble, amount);
		this.setRumble(RumbleType.kRightRumble, amount);
	}

}