// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous.Red;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.backlashTimer;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.HeadSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Red2 extends SequentialCommandGroup {
  /** Creates a new Red2. */
  ArmSubsystem m_arm;
  HeadSubsystem m_head;
  ElevatorSubsystem m_elevator;
  public Red2() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new backlashTimer(m_arm, m_head, m_elevator));
  }
}
