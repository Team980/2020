/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.SpeedControllerPIDWrapper;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DriverStation;

import static frc.robot.util.Constants.*;

public class DriveTrain extends SubsystemBase {
	private DifferentialDrive differentialDrive;
	private SpeedControllerPIDWrapper leftDrive;
	private SpeedControllerPIDWrapper rightDrive;

	private Encoder leftDriveEncoder;
	private Encoder rightDriveEncoder;
	
	//private Solenoid shifter;

  	public DriveTrain() {
		var leftFront = new WPI_TalonSRX(1);
		var leftBack = new WPI_TalonSRX(2);
		var leftTop = new WPI_TalonSRX(3);
		leftTop.setInverted(true);
		leftDriveEncoder = new Encoder(0, 1, false, CounterBase.EncodingType.k4X);
		leftDriveEncoder.setDistancePerPulse(Math.PI * 2 * WHEEL_RADIUS_FEET / 2048.0);
		leftDrive = new SpeedControllerPIDWrapper(new SpeedControllerGroup(leftFront, leftBack, leftTop), leftDriveEncoder);

		var rightFront = new WPI_TalonSRX(4);
		var rightBack = new WPI_TalonSRX(5);
		var rightTop = new WPI_TalonSRX(6);
		rightTop.setInverted(true);
		rightDriveEncoder = new Encoder(2, 3, false, CounterBase.EncodingType.k4X);
		rightDriveEncoder.setDistancePerPulse(Math.PI * 2 * WHEEL_RADIUS_FEET / 2048.0);
		rightDrive = new SpeedControllerPIDWrapper(new SpeedControllerGroup(rightFront, rightBack, rightTop), rightDriveEncoder);
		
		differentialDrive = new DifferentialDrive(leftDrive, rightDrive);
	}
	  
	public void setDrivePidEnabled(boolean pidEnabled) {
		leftDrive.setPidEnabled(pidEnabled);
		rightDrive.setPidEnabled(pidEnabled);
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Left Encoder Velocity", leftDriveEncoder.getRate());
		SmartDashboard.putNumber("Right Encoder Velocity", rightDriveEncoder.getRate());
		SmartDashboard.putNumber("Left Encoder Distance", leftDriveEncoder.getDistance());
		SmartDashboard.putNumber("Right Encoder Distance", rightDriveEncoder.getDistance());
		// This method will be called once per scheduler run
	}

  	public void arcadeDrive(double move, double turn) {
		differentialDrive.arcadeDrive(move, turn);
	}
}
