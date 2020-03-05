/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutoShift extends CommandBase {
  DriveTrain driveTrain;
  int gearSelector;//0 auto, 1 low, 2 high
  /**
   * Creates a new AutoShift.
   */
  public AutoShift(DriveTrain driveTrain , int gearSelector) {
    this.driveTrain = driveTrain;
    this.gearSelector = gearSelector;

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
		if (Math.abs(driveTrain.getLeftEncoder().getRate()) > 4.5 || Math.abs(driveTrain.getRightEncoder().getRate()) > 4.5) { // low to high
			driveTrain.shift(true);

		} else if (Math.abs(driveTrain.getLeftEncoder().getRate()) < 4 && Math.abs(driveTrain.getRightEncoder().getRate()) < 4) { // high to low
			driveTrain.shift(false);
		}
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
