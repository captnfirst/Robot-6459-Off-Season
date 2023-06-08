// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.HeadSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.TurretSubsystem;

public class Level extends CommandBase {
  /** Creates a new Level. */
  public int lvl;
  public double headAngleOffset_humanPlayer = 0;
  public double armAngleOffset_humanPlayer = 0;

  ArmSubsystem arm = new ArmSubsystem();
  ElevatorSubsystem elevator = new ElevatorSubsystem();
  HeadSubsystem head = new HeadSubsystem();
  IntakeSubsystem intake = new IntakeSubsystem();
  TurretSubsystem turret = new TurretSubsystem();

  public Level(int level, ArmSubsystem m_arm, ElevatorSubsystem m_elevator, HeadSubsystem m_head,
      IntakeSubsystem m_intake, TurretSubsystem m_turret) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.lvl = level;
    this.arm = m_arm;
    this.elevator = m_elevator;
    this.head = m_head;
    this.intake = m_intake;
    this.turret = m_turret;
    addRequirements(arm, elevator, head, intake, turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (lvl == 0) { // Normal Pozisyon
      if (elevator.getElevatorPos() > -5) {
        arm.setArm(true, -5);
        head.setHead(true, 12000); // 10000 dene
        if (arm.getArmPos() < 20) {
          turret.setTurret(true, 0);
        }
      }
      turret.lockOnTarget(false, false);
    } else if (lvl == -1) { // asansör iptal olursa sadece yerden küp almak için
      arm.setArm(true, 27.5);
      head.setHead(true, 0);
    } else if (lvl == 1) { // KONİ VE KÜP ALMA YERDEN
      if (arm.getArmPos() > 40) {
        arm.setArm(true, 22.5);
        head.setHead(true, 8500);
        if (arm.getArmPos() < 25) {
          turret.setTurret(true, 180);
          if (turret.getTurretPos() > 170 && turret.getTurretPos() < 190) {
            if (arm.getArmPos() > 20) {
              elevator.setElevator(true, -32.5);
            }
          }
        }
      } else {
        turret.setTurret(true, 180);
        if (turret.getTurretPos() > 170 && turret.getTurretPos() < 190) {
          arm.setArm(true, 22.5);
          head.setHead(true, 8500);
          if (arm.getArmPos() > 20) {
            elevator.setElevator(true, -32.5);
          }
        }
      }
      turret.lockOnTarget(false, false);
    } else if (lvl == 2) { // ORTA KÜP KONİ BIRAKMA
      elevator.setElevator(true, -0.5);
      if (elevator.getElevatorPos() > -5) {
        turret.lockOnTarget(true, true);
        arm.setArm(true, 120);
        if (arm.getArmPos() > 30) {
          // setHead(true, -18000);
          head.setHead(true, head.setHeadAngle(turret.y)); // angle değeri bulmalıyız çalışmazsa
        }
      }
    } else if (lvl == 3) { // ÜST KONİ KÜP BIRAKMA
      elevator.setElevator(true, -0.5);
      if (elevator.getElevatorPos() > -5) {
        turret.lockOnTarget(true, true);
        arm.setArm(true, 120);
        if (arm.getArmPos() > 30) {
          head.setHead(true, -1000);
        }
      }
    } else if (lvl == 4) { // ALT KONİ BIRAKMA
      elevator.setElevator(true, -0.5);
      if (elevator.getElevatorPos() > -5) {
        arm.setArm(true, -5);
        turret.setTurret(true, (turret.getTurretAbs() - turret.getTurningOffset()));
        if (arm.getArmPos() < 20) {
          head.setHead(true, 10500);
        }
      }
      turret.lockOnTarget(false, false);
    } else if (lvl == 5) { // ALT KÜP BIRAKMA
      elevator.setElevator(true, -0.5);
      if (elevator.getElevatorPos() > -5) {
        arm.setArm(true, -5);
        turret.setTurret(true, (turret.getTurretAbs() - turret.getTurningOffset()));
        if (arm.getArmPos() < 20) {
          // setTurret(true, 0);
          head.setHead(true, 10500);
        }
      }
      turret.lockOnTarget(false, false);
    } else if (lvl == 6) { // Human Player
      arm.setArm(true, 123 + armAngleOffset_humanPlayer); // 113
      if (arm.getArmPos() > 50) {
        head.setHead(true, 0 + headAngleOffset_humanPlayer);
        elevator.setElevator(true, -27); // 23
      } else {
        elevator.setElevator(true, -1);
      }
      turret.lockOnTarget(false, false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
