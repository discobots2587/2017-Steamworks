package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BlendSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private Talon motorBlend;
	private double pwm;
	private boolean blendToggle=false;

	public BlendSubsystem()
	{
		this.motorBlend = new Talon(HW.motorBlend);
		pwm=0.0;
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	public void setBlend(double speed){
	pwm=speed;
	motorBlend.set(speed);
	}

	public boolean isBlendToggle() {
		return blendToggle;
	}

	public void setBlendToggle(boolean blendToggle) {
		this.blendToggle = blendToggle;
	}
	public double getPWM(){
		return pwm;
	}

}

