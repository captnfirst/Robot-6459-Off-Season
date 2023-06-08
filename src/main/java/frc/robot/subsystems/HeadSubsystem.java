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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.InterpolatingDouble;
import frc.robot.util.InterpolatingTreeMap;

public class HeadSubsystem extends SubsystemBase {
  /** Creates a new HeadSubsystem. */
  public WPI_TalonFX talon = new WPI_TalonFX(Constants.Talon.HEAD_FALCON);

  public HeadSubsystem() {
    configTalon(talon);
  }

  private void configTalon(TalonFX talon) {
    talon.setNeutralMode(NeutralMode.Brake);

    talon.configFactoryDefault();
    talon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    talon.configNeutralDeadband(0.001, 0);
    talon.setSelectedSensorPosition(0);
    talon.configAllowableClosedloopError(0, 50, 1);

    talon.config_kP(0, 0.1);
    talon.config_kI(0, 0.0);
    talon.config_kD(0, 0.0);
    talon.config_kF(0, 0.0);

    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);

    talon.configNominalOutputForward(0, 0);
    talon.configNominalOutputReverse(0, 0);
    talon.configPeakOutputForward(0.45, 0);
    talon.configPeakOutputReverse(-0.45, 0);

    talon.configMotionCruiseVelocity(8000, 0); // measured velocity of ~100K at 85%; set cruise to that
    talon.configMotionAcceleration(24000, 0); // acceleration of 2x velocity allows cruise to be attained in 1 second

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Head Motor Pozisyon", talon.getSelectedSensorPosition());
    SmartDashboard.putNumber("Head Motor Sicaklik ", talon.getTemperature());
    SmartDashboard.putNumber("Head Motor Akim ", talon.getSupplyCurrent());
    SmartDashboard.putNumber("Head Pozisyon", getHeadPos());
  }

  public double getHeadPos() {
    double headPos = -talon.getSelectedSensorPosition();
    return headPos;
  }

  public void setHead(boolean enabled, double rawHeadPos) {
    if (enabled) {
      talon.set(ControlMode.MotionMagic, -rawHeadPos);
    } else {
      talon.stopMotor();
    }
  }

  public static InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> HeadAngleMap = new InterpolatingTreeMap<>();

  public  double setHeadAngle(double range) {
    return HeadAngleMap.getInterpolated(new InterpolatingDouble(range)).value;
  }

  public static double[][] HeadAngleValues = {
      { 1.30, -4800 },
      { 3.30, -11500 },
      { 6.08, -16300 }
  };

  static {
    for (double[] pair : HeadAngleValues) {
      HeadAngleMap.put(new InterpolatingDouble(pair[0]), new InterpolatingDouble(pair[1]));
    }
  }

}
