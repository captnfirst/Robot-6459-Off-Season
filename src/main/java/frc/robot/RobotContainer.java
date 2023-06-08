// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Drive;
import frc.robot.commands.HumanPlayer;
import frc.robot.commands.Level;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveBaseSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.HeadSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.TurretSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static final DriveBaseSubsystem drive = new DriveBaseSubsystem();
  public static final ArmSubsystem arm = new ArmSubsystem();
  public static final ElevatorSubsystem elevator = new ElevatorSubsystem();
  public static final HeadSubsystem head = new HeadSubsystem();
  public static final IntakeSubsystem intake = new IntakeSubsystem();
  public static final TurretSubsystem turret = new TurretSubsystem();
  public static int i;

  public static Joystick driveJoystick = new Joystick(0);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings

    configureBindings();
    drive.setDefaultCommand(new Drive(drive, () -> -driveJoystick.getRawAxis(1), () -> driveJoystick.getRawAxis(2)));
    // drive.setDefaultCommand(new Drive(drive, () -> -driveJoystick.getRawAxis(1),
    // () -> driveJoystick.getRawAxis(0)));
    // drive.setDefaultCommand(new Drive(drive, () -> -driveJoystick.getRawAxis(1),
    // () -> driveJoystick.getRawAxis(4)));
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    // .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is
    // pressed,
    // cancelling on release.
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
    new JoystickButton(driveJoystick, 0).onTrue(new Level(i=6, arm, elevator, head, intake, turret));
    new JoystickButton(driveJoystick, 1).onTrue(new HumanPlayer());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return Autos.exampleAuto(m_exampleSubsystem);
    return null;
  }
}
