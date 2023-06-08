// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.HeadSubsystem;

public class backlashTimer extends CommandBase {
  /** Creates a new backlashTimer. */
  ArmSubsystem arm = new ArmSubsystem();
  HeadSubsystem head = new HeadSubsystem();
  ElevatorSubsystem elevator = new ElevatorSubsystem();

  Timer backlashTimer = new Timer();
  public boolean enableTeleop = false;

  public backlashTimer(ArmSubsystem m_arm,HeadSubsystem m_head, ElevatorSubsystem m_elevator) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.arm = m_arm;
    this.head = m_head;
    this.elevator = m_elevator;
    addRequirements(arm,head,elevator);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    backlashTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (backlashTimer.get() > 0.25) {
      enableTeleop = true;
    } else if (backlashTimer.get() < 0.24) {
      arm.talon.set(ControlMode.PercentOutput, -0.05);
      head.talon.set(ControlMode.PercentOutput, 0.05);
      elevator.talon.set(ControlMode.PercentOutput, 0.05);
    } else {
      arm.talon.setSelectedSensorPosition(0);
      head.talon.setSelectedSensorPosition(0);
      elevator.talon.setSelectedSensorPosition(0);
      arm.talon.stopMotor();
      head.talon.stopMotor();
      elevator.talon.stopMotor();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    enableTeleop = true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
