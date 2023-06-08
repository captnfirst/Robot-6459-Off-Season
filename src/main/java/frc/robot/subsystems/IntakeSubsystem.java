// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  public WPI_TalonFX talon = new WPI_TalonFX(Constants.Talon.INTAKE_FALCON);

  public IntakeSubsystem() {
    configTalon(talon);
  }

  private void configTalon(TalonFX talon) {
    talon.setNeutralMode(NeutralMode.Brake);
    talon.configFactoryDefault();
    talon.setSelectedSensorPosition(0);

    talon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

    talon.config_kP(0, 0.5);
    talon.config_kI(0, 0.0);
    talon.config_kD(0, 0.0);
    talon.config_kF(0, 0.0);

    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);

    talon.configNominalOutputForward(0, 0);
    talon.configNominalOutputReverse(0, 0);
    talon.configPeakOutputForward(0.45, 0);
    talon.configPeakOutputReverse(-0.45, 0);

    talon.configNeutralDeadband(0.001, 0);
    talon.configMotionCruiseVelocity(15000, 0);
    talon.configMotionAcceleration(2500, 0);
    
    talon.configAllowableClosedloopError(0, 50, 1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
