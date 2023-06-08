// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gyro extends SubsystemBase {
  /** Creates a new Gyro. */
  public AHRS gyro = new AHRS(SPI.Port.kMXP, (byte) 200);

  public Gyro() {
    gyro.reset();
    gyro.calibrate();
  }

  public void reset() {
    gyro.reset();
  }

  public double getAngle() {
    return gyro.getAngle();
  }

  public double getRoll() {
    return gyro.getRoll();
  }

  public double getPitch() {
    return gyro.getPitch();
  }

  public double getYaw() {
    return gyro.getYaw();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("NavX Pos", getAngle());
    SmartDashboard.putNumber("NavX Pitch(X)", getPitch());
    SmartDashboard.putNumber("NavX Roll(Y)", getRoll());
    SmartDashboard.putNumber("NavX Yaw(Z)", getYaw());
  }
}
