/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ColorWheelPositionControl;
import frc.robot.commands.ColorWheelRotationControl;
import frc.robot.commands.ConstantRateShooter;
import frc.robot.commands.RunIntake;
import frc.robot.commands.ToggleDeployRoller;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.ColorWheelSpinner;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PickupRoller;
import frc.robot.subsystems.RunBelt;
import frc.robot.subsystems.Shooter;

import static frc.robot.util.DavisDealWithThis.*;
import static frc.robot.util.Util.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
	private static final Joystick throttle = new Joystick(0);
    private static final Joystick wheel = new Joystick(1);
    private static final XboxController xBox = new XboxController(2);
    private static final Joystick prajBox = new Joystick(3);

    private static final DriveTrain driveTrain = new DriveTrain();
    private static final Intake intake = new Intake();
    private static final Belt belt = new Belt();
    private static final PickupRoller roller = new PickupRoller();
    private static final ColorWheelSpinner colorWheelSpinner = new ColorWheelSpinner();
    private static final Shooter shooter = new Shooter();


    private static final Command colorWheelPositionControl = new ColorWheelPositionControl(colorWheelSpinner);
    private static final Command colorWheelRotationControl = new ColorWheelRotationControl(colorWheelSpinner);
    private static final Command runIntakeSuck = new RunIntake(intake, 0.8);
    private static final Command runIntakeSpit = new RunIntake(intake, -0.8);
    private static final Command toggleRollerDeployed = new ToggleDeployRoller(roller);
    private static final Command beltFeed = new RunBelt(belt, 0.8);
    private static final Command beltUnfeed = new RunBelt(belt, -0.8);
    private static final Command shooterCommand = new ConstantRateShooter(shooter, 50);



    public RobotContainer() {        
        driveTrain.setDefaultCommand(new RunCommand(
            () -> driveTrain.arcadeDrive(-throttle.getY(), wheel.getX()), 
            driveTrain
        ));
 
        configureButtonBindings();
    }
    
    private void configureButtonBindings() {
        // throttle
        new JoystickButton(throttle, 0)
            .whenPressed(() -> driveTrain.setDrivePidEnabled(true));

        new JoystickButton(throttle, 1)
            .whenPressed(() -> driveTrain.setDrivePidEnabled(false));

        // xbox
        new JoystickButton(xBox, XboxController.Button.kA.value)
            .whenPressed(toggleRollerDeployed);

        new JoystickButton(xBox, XboxController.Button.kB.value)
            .whenPressed(shooterCommand);

        new JoystickButton(xBox, XboxController.Button.kX.value)
            .whileHeld(beltFeed);

        new JoystickButton(xBox, XboxController.Button.kY.value)
            .whileHeld(beltUnfeed);

        new JoystickButton(xBox, XboxController.Button.kStart.value)
            .whileHeld(runIntakeSuck);

        new JoystickButton(xBox, XboxController.Button.kBack.value)
            .whileHeld(runIntakeSpit);

        new JoystickButton(xBox, XboxController.Button.kY.value)
            .whileHeld(beltUnfeed);

        new JoystickButton(xBox, XboxController.Button.kBumperLeft.value)
            .whenPressed(colorWheelRotationControl);

        new JoystickButton(xBox, XboxController.Button.kBumperRight.value)
            .whenPressed(colorWheelPositionControl);



    }
}
