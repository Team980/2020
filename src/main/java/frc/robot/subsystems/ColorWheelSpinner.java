/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.util.Constants.*;

public class ColorWheelSpinner extends SubsystemBase {    

    private Color lookingAt;
    private Color targetColor;

    private boolean hasTargetColor;

    private final ColorSensorV3 colorSensor;
    private ColorMatch colorMatcher = new ColorMatch();

    enum WheelColor {
        RED, GREEN, BLUE, YELLOW;

        WheelColor() {}

        public static WheelColor fromColor(Color color) {

        }

        public static WheelColor fromString(String color) {

        }
    }

    public ColorWheelSpinner() {
        colorSensor = new ColorSensorV3(COLOR_SENSOR_PORT);
        colorMatcher = new ColorMatch();
        colorMatcher.addColorMatch(RED);
        colorMatcher.addColorMatch(GREEN);
        colorMatcher.addColorMatch(BLUE);
        colorMatcher.addColorMatch(YELLOW);

    }

    public void runPositionControl() {
        if (!hasTargetColor) {
            String gameSpecificMessage = DriverStation.getInstance().getGameSpecificMessage();

            if (gameSpecificMessage.length() > 0) {
                Color color;
                switch (gameSpecificMessage.charAt(0)) {
                    case 'R': color = RED; break;
                    case 'G': color = GREEN; break;
                    case 'B': color = BLUE; break;
                    case 'Y': color = YELLOW; break;
                }
                hasTargetColor = true;    
            }
        }

        if (!hasTargetColor) return;

        ColorMatchResult colorMatchResult = colorMatcher.matchClosestColor(colorSensor.getColor());
        if (colorMatchResult.confidence > COLOR_MATCHER_CONFIDENCE_THRESHOLD) {
            lookingAt = colorMatchResult.color;


        }





    }

    public void runRotationControl() {
    }

    


    @Override
    public void periodic() {
        // report info and stuff

    }

    private static int getColorIndex(Color color) {
        if (color.equals(RED)) {
            return 0;
        } else if (color.equals(GREEN)) {
            return 1;
        } else if (color.equals(BLUE)) {
            return 2;
        } else if (color.equals(YELLOW)) {
            return 3;
        } else {
            return -1;
        }
    }
    
    private static double getTargetDirection(Color start, Color end) {
        int clockwise = (getColorIndex(end)-getColorIndex(start)) % 4;
        int counterClockwise = (-clockwise) % 4;

        if (start.equals(end)) {
            return 0;
        } else if (clockwise < counterClockwise) {
            return -1;
        } else {
            return 1;
        }
    }

    // private static double lookup(Color currentColor, Color targetColor) {
    //     for (Response response : responses) {
    //         if (response.matches(currentColor, targetColor)) {
    //             return response.responseSpeed;
    //         }
    //     }
    // }
    // private static final Response[] responses = {
    //     new Response(RED,    RED,    0, 0), 
    //     new Response(RED,    GREEN,  1, 3),
    //     new Response(RED,    BLUE,   2, 2),
    //     new Response(RED,    YELLOW, 3, 1),
    //     new Response(GREEN,  RED,    3, 1),
    //     new Response(GREEN,  GREEN,  0, 0),
    //     new Response(GREEN,  BLUE,   1, 3),
    //     new Response(GREEN,  YELLOW, 2, 2),
    //     new Response(BLUE,   RED,    2, 2),
    //     new Response(BLUE,   GREEN,  3, 1),
    //     new Response(BLUE,   BLUE,   0, 0),
    //     new Response(BLUE,   YELLOW, 1, 3),
    //     new Response(YELLOW, RED,    1, 3),
    //     new Response(YELLOW, GREEN,  2, 2),
    //     new Response(YELLOW, BLUE,   3, 1),
    //     new Response(YELLOW, YELLOW, 0, 0)
    // };  
        // private class Response {
    //     private Color currentColor;
    //     private Color targetColor;
    //     // these refer to the actual field element's rotation, our motor spins opposite
    //     private int clockwiseDistance; 
    //     private int counterclockwiseDistance;

    //     Response(Color currentColor, Color targetColor, int clockwiseDistance, int counterclockwiseDistance) {
    //         this.currentColor = currentColor;
    //         this.targetColor = targetColor;
    //         this.clockwiseDistance = clockwiseDistance;
    //         this.counterclockwiseDistance = counterclockwiseDistance;
    //     }

    //     private double getTurnSpeed() {
    //         if (clockwiseDistance < counterclockwiseDistance) {

    //         }
    //     }

    //     private boolean matches(Color currentColor, Color targetColor) {
    //         return this.currentColor.equals(currentColor) 
    //             && this.targetColor.equals(targetColor);
    //     }
    // }

}
