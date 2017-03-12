package org.discobots.steamworks.subsystems;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LoggerSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    
    int getTrailingInteger(String str)
    {
        int positionOfLastDigit = getPositionOfLastDigit(str);
        if (positionOfLastDigit == str.length())
        {
            // string does not end in digits
            return -1;
        }
        SmartDashboard.putNumber("int from file read", Integer.parseInt(str.substring(positionOfLastDigit)));
        return Integer.parseInt(str.substring(positionOfLastDigit));
    }
    
    
    int getPositionOfLastDigit(String str)
    {
        int pos;
        for (pos=str.length()-1; pos>=0; --pos)
        {
            char c = str.charAt(pos);
            if (!Character.isDigit(c)) break;
        }
        return pos + 1;
    }
    
    
    
    public void WriteFile(){
    	List<String> newLines = new ArrayList<>();
    	try{
    	for (String line : Files.readAllLines(Paths.get("/home/lvuser/log.txt"), StandardCharsets.UTF_8)) {
		    if (line.contains("Comp Gears Loaded")) {
		       newLines.add(line.replace("Comp Gears Loaded", ""+getTrailingInteger(line)+1));
		       SmartDashboard.putNumber("Comp Gears Loaded", getTrailingInteger(line));
		    } else {
		       newLines.add(line);
		    }
		}
		Files.write(Paths.get("/home/lvuser/log.txt"), newLines, StandardCharsets.UTF_8);
		}
		catch(Exception e)
		{
			File file = new File("/home/lvuser/log.txt");
			if(!file.exists()){
			file.mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				DriverStation.reportError("Error Creating Log File", true);
				e1.printStackTrace();
			}}
		}}
    
    	
    	
    	
    }

