package org.discobots.steamworks;

//import com.ctre.CANTalon;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class HW {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example, with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	/* CAN */// Check roboRio Web Interface for these values
	public final static int motorShoot = 6;
	public final static int motorBlend = 7;
	
	/* PWM */
	public final static int  motorFrontLeft = 2;
	public final static int motorBackLeft = 3;
	public final static int motorBackRight = 0;	 
	public final static int motorFrontRight = 1;
	public final static int motorHang=  4;
	public final static int motorIntake= 5;
	//public final static int motorShoot = 6;


	
	/* Pneumatics */
	public final static int shifter = 0;//not a double solenoid
	//public final static int shifterLeft = 1;
	public final static int gearSolenoid = 2;



	
	
	/* Analog */
	public final static int potentiometer = 0;
	public final static int pressureSensor=1;

	
	/* Digital */
	public final static int shooterEncoder=9;//infrared
	
	
	/* I2C */
	public final static int i2cLidarAddress = 0x62;



}
