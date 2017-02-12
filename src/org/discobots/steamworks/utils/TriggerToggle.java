
package org.discobots.steamworks.utils;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class TriggerToggle extends Trigger {
	
	private final GenericHID m_joystick;
	private final int m_axisNumber;	
	
	private boolean active;
	private boolean pressed;
	
	public TriggerToggle(GenericHID joystick, int axisNumber) {
		m_joystick = joystick;
		m_axisNumber = axisNumber;
	}

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		if(pressed) {
			if(m_joystick.getRawAxis(m_axisNumber) == 0) {
				active = !active;
				pressed = false;
			}
		}
		if(m_joystick.getRawAxis(m_axisNumber) == 1) {
			pressed = true;
		}
		return active;
	}
}
