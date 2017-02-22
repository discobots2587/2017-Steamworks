package org.discobots.steamworks.subsystems;

import java.util.ArrayList;

import org.discobots.steamworks.HW;
import org.discobots.steamworks.utils.CounterEncoder;
import org.discobots.steamworks.utils.PressureSensor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 *
 */
public class ElectricalSubsystem extends Subsystem {
	private double sumRPM;
	ArrayList<Integer> ShootRPM;
	public int sensorToggle=0;
	PowerDistributionPanel pdp;
	Compressor cmp;
	PressureSensor ps;
	public CounterEncoder shoots;//shooter encoder
	public ITable shootTable;
	private int shootNum;
	public Encoder encoderRightDrive;
	public Encoder encoderLeftDrive;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public ElectricalSubsystem(){
		ShootRPM = new ArrayList<Integer>();
		 shootNum=0;
		shoots = new CounterEncoder(9, 2);
		shoots.initTable(shootTable);
		pdp = new PowerDistributionPanel();
		cmp = new Compressor();
		ps = new PressureSensor(HW.pressureSensor);
		encoderLeftDrive=new Encoder(2,3);//does not exist atm
		encoderRightDrive=new Encoder(0,1);
		
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

	public boolean getShootEncoderStopped()//is shoot counter not counting any values
	{
		return shoots.getStopped();
	}
	public double getShootRPMraw()
	{ 
		if (shoots.getRawRPM()<4000)
		return shoots.getRawRPM();
		else
			return 4000;
	}
	public double getShootRPMAVG(){
		if (shootNum>9)
		{
			ShootRPM.clear();
			shootNum=0;
		}
		if(shoots.getRawRPM()<4000 && shootNum<=9)
		{
			sumRPM=0;
			ShootRPM.add((int) shoots.getRawRPM());
			for (int i=0; i<=shootNum; i++)
			{
				sumRPM+=ShootRPM.get(i);
			}
			shootNum++;
			sumRPM/=shootNum;
			return sumRPM;
		}
			return sumRPM;
	}
	public double getRotations(){//does not currently return value
		return shoots.getDistance();
	}

}