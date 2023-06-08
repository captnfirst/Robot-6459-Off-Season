// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ArmSubsystem. */
  // static final double TICKS_PER_INCH = 3442;
  // public static final double[] LEVEL_X_SCORE = { 0.0, 2000.0, 45000.0,
  // 112000.0};
  // public static final double HUMAN_PLAYER_STATION = 20000.0;
  // public static final double ALMOST_STOW = 500.0;
  public WPI_TalonFX talon = new WPI_TalonFX(Constants.Talon.ARM_FALCON);
  private double armMinPos = 0;
  private double armMaxPos = 47000;
  double armKConv = 354.6;

  public ArmSubsystem() {
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
    talon.configMotionCruiseVelocity(15000, 0); // measured velocity of ~100K at 85%; set cruise to that
    talon.configMotionAcceleration(2500, 0); // acceleration of 2x velocity allows cruise to be attained in 1 second

    talon.configAllowableClosedloopError(0, 50, 1);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Arm Motor Pozisyon", talon.getSelectedSensorPosition());
    SmartDashboard.putNumber("Arm Motor Sicaklik ", talon.getTemperature());
    SmartDashboard.putNumber("Arm Motor Akim ", talon.getSupplyCurrent());
    SmartDashboard.putNumber("Arm Pozisyon ", getArmPos());
  }

  public double getArmPos() {
    double armPos = (talon.getSelectedSensorPosition() + 0.001) / armKConv;
    return armPos;
  }

  public void setArm(boolean enabled, double armDegrees) {
    double calculatedAngle = armKConv * armDegrees;
    if (enabled) {
      talon.set(ControlMode.MotionMagic,
          MathUtil.clamp(calculatedAngle, armMinPos, armMaxPos));
    } else {
      talon.stopMotor();
    }
  }
}