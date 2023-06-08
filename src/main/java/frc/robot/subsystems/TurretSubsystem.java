// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurretSubsystem extends SubsystemBase {
  /** Creates a new TurretSubsystem. */
  // public static double x = 0;
  // public static double y = 0;
  public double x = 0;
  public double y = 0;
  public static double targetStatus = 0;
  public static double targetArea = 0;
  double minTargetArea = 0;

  double turretKConv = -1055.55;

  private double turretMinPos = -548000; // 240000
  private double turretMaxPos = 548000; // -240000

  double minXLimiter = -20;
  double maxXLimiter = 20;

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry tv = table.getEntry("tv");
  NetworkTableEntry ta = table.getEntry("ta");

  public WPI_TalonFX talon = new WPI_TalonFX(Constants.Talon.ARM_FALCON);
  Gyro gyro = new Gyro();

  public TurretSubsystem() {
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
    talon.configPeakOutputForward(1, 0);
    talon.configPeakOutputReverse(-1, 0);

    talon.configMotionCruiseVelocity(45000, 0); // 30000 // measured velocity of ~100K at 85%; set cruise to that
    talon.configMotionAcceleration(30000, 0); // 15000 // acceleration of 2x velocity allows cruise to be attained in 1
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    targetStatus = tv.getDouble(0.0);
    targetArea = ta.getDouble(0.0);

    SmartDashboard.putNumber("Turret Motor Pozisyon", talon.getSelectedSensorPosition());
    SmartDashboard.putNumber("Turret Motor Sicaklik ", talon.getTemperature());
    SmartDashboard.putNumber("Turret Motor Akim ", talon.getSupplyCurrent());
    SmartDashboard.putNumber("Turret Pozisyon ", getTurretPos());
  }

  public double getXVal() {
    double valueToReturn;
    double kMinimizer = 0.05;
    if (x > minXLimiter && x < maxXLimiter) {
      valueToReturn = x;
    } else {
      valueToReturn = 0;
    }
    valueToReturn = valueToReturn * kMinimizer;
    return valueToReturn;
  }

  public double getTurretPos() {
    double turretPos = (talon.getSelectedSensorPosition() + 0.001)
        / turretKConv;
    return turretPos;
  }

  public void setTurret(boolean enabled, double turretDegrees) {
    double calculatedAngle = turretKConv * turretDegrees;
    if (enabled) {
      talon.set(ControlMode.MotionMagic,
          MathUtil.clamp(calculatedAngle, turretMinPos, turretMaxPos));
      if (calculatedAngle > turretMaxPos) {
        System.out.println("Turret MAX sinirin disina cikmaya calisiyor!");
      } else if (calculatedAngle < turretMinPos) {
        System.out.println("Turret MIN sinirin disina cikmaya calisiyor!");
      }
    } else {
      talon.stopMotor();
    }
  }

  boolean gyroOffsetLock = false;
  double gyroOffset = 0;
  double visionIncrement = 0;

  public void lockOnTarget(boolean enabled, boolean visionOn) {
    if (enabled) {
      visionIncrement += getXVal();
      double calculatedTurretAngle = (getTurretAbs() - getTurningOffset()) - visionIncrement;
      setTurret(true, calculatedTurretAngle);
    } else {
      visionIncrement = 0;
    }
  }

  public double getTurningOffset() {
    double valueToReturn;
    if (Math.abs(gyro.getAngle() % 360) > 180) {
      valueToReturn = -180;
    } else {
      valueToReturn = 180;
    }
    return valueToReturn;
  }

  public double getTurretAbs() {
    double turningOffset = getTurningOffset();
    return (gyroOffset - (-gyro.getAngle())) +
        turningOffset;
  }
}
