/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Constants;

public class PickupRoller extends SubsystemBase {
  private Solenoid solenoid;
  public PickupRoller() {
    solenoid = new Solenoid(Constants.PICKUP_ROLLER__SOLENOID_CHANNEL);
  }

  public void toggleDeployed() {
    solenoid.set(!solenoid.get());
  }

  public void setDeployed(boolean isDeployed) {
    solenoid.set(isDeployed == Constants.PICKUP_ROLLER_DEPLOY_SOLENOID_SET_DEPLOYED);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("pickup roller deployed", solenoid.get());
  }
}
