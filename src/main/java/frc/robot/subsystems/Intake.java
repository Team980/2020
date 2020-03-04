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
    private SpeedControllerGroup intakeRollers;
    
    public Intake()
    {
      WPI_TalonSRX leftIntakeMotor = new WPI_TalonSRX(INTAKE_TALON_LEFT_CHANNEL);
      leftIntakeMotor.setInverted(true);
      WPI_TalonSRX rightIntakeMotor = new WPI_TalonSRX(INTAKE_TALON_RIGHT_CHANNEL);
      intakeRollers = new SpeedControllerGroup(leftIntakeMotor, rightIntakeMotor);
    }

    public void run(double speed) {
      intakeRollers.set(speed);
    }

    @Override
    public void periodic() {
    }
}
