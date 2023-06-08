// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBaseSubsystem extends SubsystemBase {
  /** Creates a new DriveBaseSubsystem. */

  private final WPI_TalonFX leftTalon1 = new WPI_TalonFX(Constants.Talon.DRIVE_LEFT_TOP);
  private final WPI_TalonFX leftTalon2 = new WPI_TalonFX(Constants.Talon.DRIVE_LEFT_FRONT);
  private final WPI_TalonFX leftTalon3 = new WPI_TalonFX(Constants.Talon.DRIVE_LEFT_BOTTOM);
  private final WPI_TalonFX rightTalon1 = new WPI_TalonFX(Constants.Talon.DRIVE_RIGHT_TOP);
  private final WPI_TalonFX rightTalon2 = new WPI_TalonFX(Constants.Talon.DRIVE_RIGHT_FRONT);
  private final WPI_TalonFX rightTalon3 = new WPI_TalonFX(Constants.Talon.DRIVE_RIGHT_BOTTOM);
  public DifferentialDrive drive = new DifferentialDrive(leftTalon1, rightTalon1);

  // Drive parameters
  // pi * wheel diameter * (pulley ratios) / (counts per rev * gearbox reduction)
  public static final double INCHES_TO_METER_CONVERSION_FACTOR = 0.0254;
  public static final double DISTANCE_PER_PULSE_IN_INCHES = 3.14 * 5.0 * 1.0 / (2048.0 * 9.36); // corrected
  public static final double DISTANCE_PER_PULSE_IN_METERS = DISTANCE_PER_PULSE_IN_INCHES * INCHES_TO_METER_CONVERSION_FACTOR;
  public static final double DISTANCE_PER_ROTATION_IN_METERS = DISTANCE_PER_PULSE_IN_METERS * 2048;

  public DriveBaseSubsystem() {
    leftTalon1.configFactoryDefault();
    leftTalon2.configFactoryDefault();
    leftTalon3.configFactoryDefault();
    rightTalon1.configFactoryDefault();
    rightTalon2.configFactoryDefault();
    rightTalon3.configFactoryDefault();

    leftTalon2.follow(leftTalon1);
    leftTalon3.follow(leftTalon1);
    rightTalon2.follow(rightTalon1);
    rightTalon3.follow(rightTalon1);

    leftTalon1.setInverted(true);
    leftTalon2.setInverted(true);
    leftTalon3.setInverted(true);
    // rightTalon1.setInverted(false);
    // rightTalon2.setInverted(false);
    // rightTalon3.setInverted(false);

    leftTalon1.configOpenloopRamp(0.375);
    rightTalon1.configOpenloopRamp(0.375);

    leftTalon1.setNeutralMode(NeutralMode.Brake);
    leftTalon2.setNeutralMode(NeutralMode.Coast);
    leftTalon3.setNeutralMode(NeutralMode.Coast);
    rightTalon1.setNeutralMode(NeutralMode.Brake);
    rightTalon2.setNeutralMode(NeutralMode.Coast);
    rightTalon3.setNeutralMode(NeutralMode.Coast);  
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());

    int matchnumber = DriverStation.getMatchNumber();
    DriverStation.MatchType MatchType = DriverStation.getMatchType();
    SmartDashboard.putString("matchInfo", "" + MatchType + '_' + matchnumber);

    DifferentialDriveWheelSpeeds speeds = getWheelSpeeds();
    SmartDashboard.putNumber("Drive Left Speed m per s", speeds.leftMetersPerSecond);
    SmartDashboard.putNumber("Drive Right Speed m per s", speeds.rightMetersPerSecond);
  }

  public void arcadeDrive(double xSpeed, double zRotation){
    drive.arcadeDrive(xSpeed, zRotation);
  }

  double convertTicksToMeters(double ticks) {
    return ticks * DISTANCE_PER_PULSE_IN_METERS;
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(convertTicksToMeters(leftTalon1.getSelectedSensorVelocity() * 10), convertTicksToMeters(rightTalon1.getSelectedSensorVelocity() * 10));
  }
}
