package org.discobots.steamworks.subsystems;

import org.discobots.steamworks.HW;
import org.discobots.steamworks.utils.PressureSensor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 *
 */
public class ElectricalSubsystem extends Subsystem {
	public int sensorToggle=0;
	PowerDistributionPanel pdp;
	Compressor cmp;
	PressureSensor ps;
	Encoder shoots;//shooter encoder
	ITable shootsTable;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public ElectricalSubsystem(){
		shoots = new Encoder(0,1, false);
		shoots.setIndexSource(HW.shooterEncoder);
		shoots.setMaxPeriod(.1);
		shoots.setMinRate(10);
		shoots.setDistancePerPulse(2);
		shoots.setSamplesToAverage(5);//averages last 5 samples
		shoots.initTable(shootsTable);
		pdp = new PowerDistributionPanel();
		cmp = new Compressor();
		ps = new PressureSensor(HW.pressureSensor);
		shootsTable = shoots.getTable();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public double getPDPVoltage() {
		return pdp.getVoltage();
	}

	public double getCurrentFromPDPChannel(int channel) {
		return pdp.getCurrent(channel);
	}

	public double getPDPTotalCurrent() {
		double totalCurrent = 0;
		for (int i = 0; i < 16; i++) {
			totalCurrent += pdp.getCurrent(i);
		}
		return totalCurrent;
	}
	public void setCompressor (boolean var){
		if (var){// could also add (&& getPressure() < MAX PRESSURE VALUE)
				cmp.start();
		}else{
				cmp.stop();
		}
	}
	public boolean getCompressorState(){
		return cmp.enabled();
	}
	public boolean getPressureSwitchState(){
		return cmp.getPressureSwitchValue();
	}
	public double getPressure() {
		return ps.getPSI();
	}
	public double getCompressorControlLoopState(){
		return cmp.getCompressorCurrent();
	}
	public double rawShootDist(){
		return shoots.getRaw();
	}
	public int shootRotateCount(){
		return shoots.get();
	}
	public double shootRate(){
		return shoots.getRate();
	}

}