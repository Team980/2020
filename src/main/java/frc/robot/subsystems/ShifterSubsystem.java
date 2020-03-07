/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.util.DavisDealWithThis.*;

public class ShifterSubsystem extends SubsystemBase {
    private DoubleSolenoid shifter;

    public ShifterSubsystem() {
        shifter = new DoubleSolenoid(SHIFTER_SOLENOID_HIGH_CHANNEL, SHIFTER_SOLENOID_LOW_CHANNEL);
    }

    public void set(boolean isSet) {
        if (isSet) {
            shifter.set(Value.kForward);
        } else {
            shifter.set(Value.kReverse);
        }
    } 

    @Override
    public void periodic() {
        SmartDashboard.putString("gear state", shifter.get().toString());
    }
}
