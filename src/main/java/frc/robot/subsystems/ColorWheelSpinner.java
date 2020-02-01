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

    private char gameData;
   

    private final I2C.Port colorSensorPort = I2C.Port.kOnboard;
    //sets up the special port for the color sensor

    private final ColorSensorV3 colorSensor = new ColorSensorV3(colorSensorPort);
    //creates the color sensor object at the color sensor port

    private final ColorMatch colorMatcher = new ColorMatch();
    //registers and detects known colors


    private final Color red = ColorMatch.makeColor(0.47, 0.37, 0.15);
    private final Color yellow = ColorMatch.makeColor(0.31, 0.55, 0.13);
    private final Color green = ColorMatch.makeColor(0.19, 0.55, 0.25);
    private final Color blue = ColorMatch.makeColor(0.15, 0.44, 0.40);

    Color detectedColor;
    ColorMatchResult closestColor;
    //the numbers will be in the range of 0 to 1

    Color[] colorsWeCareAbout = {red ,yellow, green, blue};
    //group all of the the colors I care about   

    Spark spark;

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

    public Color targetColor() {
        String gameString = DriverStation.getInstance().getGameSpecificMessage();
        
       
            switch (gameString) {
                case "":

                break;
                case"B":

                break;
                case "R":
                
                break;
                case "Y":

                break;
            }
        
        else
        {
            // error
        }
    }

    public void toStringColor(ColorMatchResult closestColor)//dont open
    {
        if (closestColor.color == blue)
        {
            colorString = "Blue";
        }
        else if (closestColor.color == red)
        {
            colorString = "Red";
        }
        else if (closestColor.color == green)
        {
            colorString = "Green";
        } 
        else if (closestColor.color == yellow) 
        {
            colorString = "Yellow";
        } 
        else
        {
            colorString = "Unknown";
        }
    }
}
