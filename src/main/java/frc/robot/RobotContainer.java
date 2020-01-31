/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.ColorWheelSpinner;
import frc.robot.subsystems.DriveTrain;

import static frc.robot.util.Constants.*;
import static frc.robot.util.Util.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    DriveTrain driveTrain;
    ColorWheelSpinner colorWheelSpinner;

    private XboxController xBox;

    public RobotContainer() {
        // Configure the button bindings
        xBox = new XboxController(0);

        colorWheelSpinner = new ColorWheelSpinner();

        driveTrain = new DriveTrain();
        driveTrain.setDefaultCommand(new RunCommand(() -> 
            driveTrain.arcadeDrive(
                applyDeadband(-xBox.getY(Hand.kLeft), MOVE_DEADBAND) , 
                applyDeadband(xBox.getX(Hand.kRight), TURN_DEADBAND)
            ), 
            driveTrain)
        );
        
        JoystickButton a = new JoystickButton(xBox, 1);
        a.whenPressed(() -> driveTrain.setDrivePidEnabled(true));

        JoystickButton b = new JoystickButton(xBox, 2);
        b.whenPressed(() -> driveTrain.setDrivePidEnabled(false));

        configureButtonBindings();
    }

    private void configureButtonBindings() {
    }
}
