/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ConstantRateShooter extends CommandBase {
  /**
   * Creates a new ConstantRateShooter.
   */
  private Shooter shooter;
  private double power;
  public ConstantRateShooter(Shooter shooter, double power) {
    addRequirements(shooter);
    this.shooter = shooter;
    this.power = power;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setGatekeeperOpen(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.fire(power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setGatekeeperOpen(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
