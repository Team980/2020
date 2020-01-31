/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

public class ColorWheelSpinner extends SubsystemBase
{

  private final I2C.Port colorSensorPort = I2C.Port.kOnboard;
  //sets up the special port for the color sensor

  private final ColorSensorV3 colorSensor = new ColorSensorV3(colorSensorPort);
  //creates the color sensor object at the color sensor port

  private final ColorMatch colorMatcher = new ColorMatch();
  //registers and detects known colors


  private final Color redTarget = ColorMatch.makeColor(0.47, 0.37, 0.15);//change this after testing
  private final Color yellowTarget = ColorMatch.makeColor(0.31, 0.55, 0.13);//change this after testing
  private final Color greenTarget = ColorMatch.makeColor(0.19, 0.55, 0.25);//change this after testing
  private final Color blueTarget = ColorMatch.makeColor(0.15, 0.44, 0.40);//changet this after testing

  Color detectedColor;
  ColorMatchResult closestColor;
  //the numbers will be in the range of 0 to 1

 
  Color[] colorsWeCareAbout = {redTarget,yellowTarget,greenTarget,blueTarget};  

  public ColorWheelSpinner() 
  {
    for (Color color : colorsWeCareAbout)
    {
      colorMatcher.addColorMatch(color);
    }
  }

  @Override
  public void periodic()
  {
    detectedColor = colorSensor.getColor();
    closestColor = colorMatcher.matchClosestColor(detectedColor);

    String colorString;

    if (closestColor.color == blueTarget) {

      colorString = "Blue";

    } else if (closestColor.color == redTarget) {

      colorString = "Red";

    } else if (closestColor.color == greenTarget) {

      colorString = "Green";

    } else if (closestColor.color == yellowTarget) {

      colorString = "Yellow";

    } 
    else
    {
      colorString = "Unknown";
    }

    SmartDashboard.putString("The Color: ", colorString);

    SmartDashboard.putNumber("Red: ", detectedColor.red);
    SmartDashboard.putNumber("Green: ", detectedColor.green);
    SmartDashboard.putNumber("Blue: ", detectedColor.blue);

    SmartDashboard.putNumber("Confidence: ", closestColor.confidence);
    
  }
}
