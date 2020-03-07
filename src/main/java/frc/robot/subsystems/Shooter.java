/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import static frc.robot.util.DavisDealWithThis.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Shooter extends PIDSubsystem {
  private SpeedController motor;
  private Encoder shootEncoder;
  private SimpleMotorFeedforward shootFF;
  private final DoubleSolenoid gateKeeperSolenoid = new DoubleSolenoid(SHOOTER_GATEKEEPER_SOLENOID_CLOSED_CHANNEL , SHOOTER_GATEKEEPER_SOLENOID_OPENED_CHANNEL); // Forward is closed
   
  public Shooter() {
    super(new PIDController(SHOOTER_P, SHOOTER_I, SHOOTER_D));
    getController().setTolerance(1);//rotations per second

    motor = new WPI_TalonSRX(SHOOTER_TALON_CHANNEL);

    shootEncoder = new Encoder(SHOOTER_ENCODER_CHANNEL_A, SHOOTER_ENCODER_CHANNEL_B, false, EncodingType.k4X);
    //shootEncoder.setDistancePerPulse(SHOOTER_ENCODER_DISTANCE_PER_PULSE);

    shootFF = new SimpleMotorFeedforward(SHOOTER_FEEDFORWARD_KS, SHOOTER_FEEDFORWARD_KV);
  }

  public void setGatekeeperOpen(boolean open) {
    if (open){
      gateKeeperSolenoid.set(Value.kReverse);
    }
    else{
      gateKeeperSolenoid.set(Value.kForward);
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("st;lskdfj", shootEncoder.getDistance());
  }
  public void fire(double targetRPS){
    setSetpoint(targetRPS);
  }

  @Override
  public void useOutput(double output, double setpoint) {
    motor.setVoltage(output + shootFF.calculate(setpoint));
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return shootEncoder.getRate();
  }

  public void manual(double throttle){
    motor.set(throttle);
  }

  public Encoder getEncoder(){
    return shootEncoder;
  }
}