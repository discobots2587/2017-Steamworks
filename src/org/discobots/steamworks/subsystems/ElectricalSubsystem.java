package org.discobots.steamworks.subsystems;


import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.discobots.steamworks.HW;
import org.discobots.steamworks.Robot;
import org.discobots.steamworks.utils.CounterEncoder;
import org.discobots.steamworks.utils.PressureSensor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 *
 */
public class ElectricalSubsystem extends Subsystem {
	private double sumRPM;
	ArrayList<Integer> ShootRPM;
	private int sensorToggle=0;
	PowerDistributionPanel pdp;
	Compressor cmp;
	PressureSensor ps;
	public CounterEncoder shoots;//shooter encoder
	private int shootNum;
	public Encoder encoderRightDrive;
	public Encoder encoderLeftDrive;
	File file = new File("Output.txt");
	List<String> newLines = new ArrayList<>();
	private boolean isShootsDisabled=false;//enables/disables rpm calculations
	public DigitalInput GearLoaded; //proximity sensor to check if gear is loaded
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public ElectricalSubsystem(){
		GearLoaded=new DigitalInput(HW.gearDetect);
		ShootRPM = new ArrayList<Integer>();
		shootNum=0;
		shoots = new CounterEncoder(9, 2);
		pdp = new PowerDistributionPanel();
		cmp = new Compressor();
		ps = new PressureSensor(HW.pressureSensor);
		encoderLeftDrive=new Encoder(HW.EncoderLeftDrive1,HW.EncoderLeftDrive2);//does not exist atm
		encoderRightDrive=new Encoder(HW.EncoderRightDrive1,HW.EncoderRightDrive2);// comp bot only
		disableSensors(0);
	}
	
	public void disableSensors(int sensorToggle)
	{
		if(sensorToggle>=0)
		this.sensorToggle=sensorToggle;
		else
		{
			this.sensorToggle+=-sensorToggle;
			if(this.sensorToggle>2)
			{
				this.sensorToggle=0;
			}
		}
		switch (sensorToggle)
	    {
			 case 0:
	        	 SmartDashboard.putString("Sensors Disabled: ", "None");
				try{	
	        	 Robot.gearDistSub.useLidar=true;
					Robot.gearDistSub.enable();
				}catch(NullPointerException e)
				{
					System.out.println("No Lidar Subsystem Created");
				}
				try{	
		        	 Robot.shootDistSub.useLidar=true;
						Robot.shootDistSub.enable();
					}catch(NullPointerException e)
					{
						System.out.println("No Lidar Subsystem Created");
					}
					isShootsDisabled=false;
				break;
	         case 1:
	        	 SmartDashboard.putString("Sensors Disabled: ", "LIDAR");

	        	 try {
					Robot.gearDistSub.shutdownLidar();
				} catch (NullPointerException e) {
					System.out.println("No Lidar Subsystem Created");
				}
	        	 try {
						Robot.shootDistSub.shutdownLidar();
					} catch (NullPointerException e) {
						System.out.println("No Lidar Subsystem Created");
					}
	        	 isShootsDisabled=false;
	        	break;
	        case 2: 
	        	 SmartDashboard.putString("Sensors Disabled: ", "LIDAR, ShootEncoder");
	        	 SmartDashboard.putString("Sensors Disabled: ", "LIDAR");

	        	 try {
					Robot.gearDistSub.shutdownLidar();
				} catch (NullPointerException e) {
					System.out.println("No Lidar Subsystem Created");
				}
	        	 try {
						Robot.shootDistSub.shutdownLidar();
					} catch (NullPointerException e) {
						System.out.println("No Lidar Subsystem Created");
					}
	        	 isShootsDisabled=true;
	        	 try{
	        	 shoots.free();
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 System.out.println("exception encountered resetting shoots");
	        	 }
	        	 break;
	        }
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
	
	public boolean isGearLoaded()//uses D-IO to see if a gear has been loaded
	{
		
		try{
			if (DriverStation.getInstance().isFMSAttached()&&SmartDashboard.getBoolean("Gear Loaded", false)!=GearLoaded.get()){
				System.out.println("WRITING TO FILE");
				Robot.logSub.WriteFile();//logs that a gear was loaded
			}			
		}
		catch(NullPointerException e)
		{
			System.out.println("null ptr error writng file to Robo Rio");
		}
		return(GearLoaded.get());
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
		if(!isShootsDisabled){
		if (shoots.getRawRPM()<4000)
		return shoots.getRawRPM();
		else
			return 4000;
		}
		else
			return 0;
	}
	public double getShootRPMAVG(){
		if(!isShootsDisabled)
		{
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
		else 
			return 5000;//returns rpm is 5000 by default if disabled
	}
	public double getRotations(){//does not currently return value
		if(!isShootsDisabled){
		return shoots.getDistance();
		}
		return 0;
	}

}