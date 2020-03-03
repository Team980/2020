/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.util.Constants.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake extends SubsystemBase
 {
    Solenoid boundaryExtender;

    WPI_TalonSRX leftIntakeMotor;
    WPI_TalonSRX rightIntakeMotor;

    WPI_TalonSRX belt;
   
    SpeedControllerGroup intakeRollers;

    boolean powerOn = false;
    
    public Intake()
    {
      boundaryExtender = new Solenoid(INTAKE_DEPLOY_SOLENOID_CHANNEL);

      rightIntakeMotor = new WPI_TalonSRX(INTAKE_TALON_RIGHT_CHANNEL);
      leftIntakeMotor = new WPI_TalonSRX(INTAKE_TALON_LEFT_CHANNEL);
      leftIntakeMotor.setInverted(true);
      intakeRollers = new SpeedControllerGroup(leftIntakeMotor,rightIntakeMotor);

      belt = new WPI_TalonSRX(BELT_CHANNEL);

      powerOn = false;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("belt: ", belt.get());
      SmartDashboard.putBoolean("solenoid position: ", boundaryExtender.get());
      SmartDashboard.putNumber("Left Intake: ", leftIntakeMotor.get());
      SmartDashboard.putNumber("Right Intake: ", rightIntakeMotor.get());
    }
    public void toggle(double beltPower)//happens after release
    {
      beltPower = powerOn ? beltPower : 0;
      boundaryExtender.set(powerOn);
      intakeRollers.set(beltPower);
      belt.set(beltPower);

      powerOn = !powerOn;
    }
}
