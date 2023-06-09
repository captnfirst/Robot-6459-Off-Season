// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBaseSubsystem;

public class Drive extends CommandBase {
  /** Creates a new Drive. */
  DriveBaseSubsystem drive;
  DoubleSupplier xSpeed;
  DoubleSupplier zRotation;
  public Drive(DriveBaseSubsystem m_drive, DoubleSupplier xSpeed, DoubleSupplier zRotation) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drive = m_drive;
    this.xSpeed = xSpeed;
    this.zRotation = zRotation;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.arcadeDrive(xSpeed.getAsDouble(), zRotation.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
