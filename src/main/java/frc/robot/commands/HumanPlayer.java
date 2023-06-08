// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.HeadSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.TurretSubsystem;

public class HumanPlayer extends CommandBase {
  /** Creates a new HumanPlayer. */
  ArmSubsystem m_arm= new ArmSubsystem();
  ElevatorSubsystem m_elevator = new ElevatorSubsystem();
  HeadSubsystem m_head = new HeadSubsystem();
  IntakeSubsystem m_intake = new IntakeSubsystem();
  TurretSubsystem m_turret = new TurretSubsystem();
  Level lvl= new Level(6, m_arm, m_elevator, m_head, m_intake, m_turret);
  public HumanPlayer() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // buraya mı oraya mı denemem lazımmm
    // if(RobotContainer.i==6){
    //   RobotContainer.level.armAngleOffset_humanPlayer += 0.1;
    // }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(RobotContainer.i==6){
      lvl.armAngleOffset_humanPlayer+=1;
    }
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
