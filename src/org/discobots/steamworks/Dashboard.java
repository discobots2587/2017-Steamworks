package org.discobots.steamworks;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {
	
	private static int driveCounter = 0;
 
	public static void init() {
	}

	public static void update() {
		driveCounter++;
		//pdpPTs.add(Robot.electricalSub.getPDPTotalCurrent());
		if (driveCounter == Integer.MAX_VALUE) {
			driveCounter = 0;
		}

		if (driveCounter % 5 == 0) { // 100ms
			SmartDashboard.putNumber("Robot Loop Execution Time",
					Robot.loopExecutionTime);

		} else if (driveCounter % 5 == 1) {
			//SmartDashboard.putData("DriveTrainCommand", Robot.driveTrainSub.getCurrentCommand());
		
		//SmartDashboard.putNumber("Potentiometer", Robot.armSub.potentiometer.getAverageVoltage());

		}
		if (driveCounter%10==1)
		{
			//SmartDashboard.putNumber("PRESSURE", Robot.electricalSub.getPressure());
			
			//SmartDashboard.putBoolean("Pressure Switch State", Robot.electricalSub.getPressureSwitchState());
		} 
		 if(driveCounter%100==1)//for very unimportant notifications
		{

		}
		}
}