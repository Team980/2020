/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;


public class ColorWheelSpinner extends SubsystemBase {
   
    private String[] hackyCircularChart = {"R", "G", "B", "Y", "R", "G", "B","Y", "R", "G", "B", "Y"};//start index at 4 end at 8
    //default is clockwise

    private final Spark spark = new Spark(10);

    private final I2C.Port colorSensorPort = I2C.Port.kOnboard;
    //sets up the special port for the color sensor

    private final ColorSensorV3 colorSensor = new ColorSensorV3(colorSensorPort);
    //creates the color sensor object at the color sensor port

    private final ColorMatch colorMatcher = new ColorMatch();
    //registers and detects known colors

    private final Color Red = ColorMatch.makeColor(0.47, 0.37, 0.15);
    private final Color Green = ColorMatch.makeColor(0.19, 0.55, 0.25);
    private final Color Blue = ColorMatch.makeColor(0.15, 0.44, 0.40);
    private final Color Yellow = ColorMatch.makeColor(0.31, 0.55, 0.13);

    Color detectedColor;
    ColorMatchResult closestColor;
    //the numbers will be in the range of 0 to 1

    Color[] colorsWeCareAbout = {Red, Green, Blue, Yellow};
    //group all of the the colors I care about   

    String colorString;
    //what color the sensor sees in a string

    public ColorWheelSpinner() 
    {
        for (Color color : colorsWeCareAbout)
        {
            colorMatcher.addColorMatch(color);//add all of our colors to the black box
        }
    
    }

    @Override
    public void periodic()
    {
        detectedColor = colorSensor.getColor();
        closestColor = colorMatcher.matchClosestColor(detectedColor);

        toStringColor(closestColor);

        SmartDashboard.putString("The Color: ", colorString);
        SmartDashboard.putNumber("Red: ", detectedColor.red);
        SmartDashboard.putNumber("Green: ", detectedColor.green);
        SmartDashboard.putNumber("Blue: ", detectedColor.blue);
        SmartDashboard.putNumber("Confidence: ", closestColor.confidence);
        
    }

    public void goToColor() 
    {
        String gameString = DriverStation.getInstance().getGameSpecificMessage();

        if(gameString == "" || gameString == colorString)
        {
            spark.set(0);
        }
        else
        {
            int Direction = (leftTurnDistance(gameString)-rightTurnDistance(gameString)) > 0 ? 1: -1;
            spark.set(Direction);
        }
        
    }
    private int leftTurnDistance(String currentColor)
    {
        int startIndex = arrayIndexOf(hackyCircularChart,currentColor) + 4;
        for(int i = startIndex; i > 0; i--)
        {
            if(colorString == hackyCircularChart[i])
            {
                return startIndex - i;
            }
        }
        return 0;
    }
    private int rightTurnDistance( String current)
    {
        int startIndex = arrayIndexOf(hackyCircularChart,current) + 4;
        for (int i = startIndex; i < hackyCircularChart.length; i++)
        {
            if(colorString == hackyCircularChart[i])
            {
                return i-startIndex;
            }
        }
        return 0;
    }
    private int arrayIndexOf(String[] array, String item)
    {
        for (int i = 0; i < array.length; i++) 
        {
            if(array[i] == item)
            {
                return i;
            }   
        }
        return -1;
    }

    private void toStringColor(ColorMatchResult closestColor)//dont open
    {
        if (closestColor.color == Blue)
        {
            colorString = "B";
        }
        else if (closestColor.color == Red)
        {
            colorString = "R";
        }
        else if (closestColor.color == Green)
        {
            colorString = "G";
        } 
        else if (closestColor.color == Yellow) 
        {
            colorString = "Y";
        } 
        else
        {
            colorString = "Unknown";
        }
    }
}
