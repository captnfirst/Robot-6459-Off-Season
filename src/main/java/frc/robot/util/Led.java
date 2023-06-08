// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Led extends SubsystemBase {
  /** Creates a new Led. */
  AddressableLED led;
  AddressableLEDBuffer ledBuffer;
  int id;
  int hue;
  Timer ledBlinkTimer = new Timer();
  double blinkTime = 0.1;

  public Led() {
    led = new AddressableLED(0);
    ledBuffer = new AddressableLEDBuffer(78);
  }

  void huePlus() {
    hue++;
    if (hue == 180) {
      hue = 0;
    } else {
      ledBuffer.setHSV(id, hue, 255, 255);
    }
  }

  void idPlus() {
    id++;
    if (id == 78) {
      id = 0;
    } else {
      ledBuffer.setHSV(id, hue, 255, 255);
    }
  }

  public void setLeds(int ledStatus) {
    Alliance ally = DriverStation.getAlliance();
    ledBlinkTimer.start();
    if (ledStatus == 0) { // rainbow
      idPlus();
      huePlus();
    } else if (ledStatus == -1) { // purple cube
      if (ledBlinkTimer.get() < blinkTime) {
        for (int i = 0; i < 78; i++) {
          if (i > 42 && i < 79) {
            ledBuffer.setRGB(i, 255, 0, 255);
          } else if (i < 42 && ally.equals(Alliance.Red)) {
            ledBuffer.setRGB(i, 255, 0, 0);
          } else if (id < 42 && ally.equals(Alliance.Blue)) {
            ledBuffer.setRGB(id, 0, 0, 255);
          }
        }
      } else if (ledBlinkTimer.get() > blinkTime && ledBlinkTimer.get() < blinkTime * 2) {
        for (int i = 0; i < 78; i++) {
          ledBuffer.setRGB(i, 0, 0, 0);
        }
      } else {
        ledBlinkTimer.reset();
      }
    } else if (ledStatus == 1) { // yellow cone
      if (ledBlinkTimer.get() < blinkTime) {
        for (int i = 0; i < 78; i++) {
          if (i > 42 && i < 79) {
            ledBuffer.setRGB(i, 255, 255, 0);
          } else if (i < 42 && ally.equals(Alliance.Red)) {
            ledBuffer.setRGB(i, 255, 0, 0);
          } else if (id < 42 && ally.equals(Alliance.Blue)) {
            ledBuffer.setRGB(id, 0, 0, 255);
          }
        }
      } else if (ledBlinkTimer.get() > blinkTime && ledBlinkTimer.get() < blinkTime * 2) {
        for (int i = 0; i < 78; i++) {
          ledBuffer.setRGB(i, 0, 0, 0);
        }
      } else {
        ledBlinkTimer.reset();
      }
    } else if (ledStatus == 2) { // green ready
      if (ledBlinkTimer.get() < blinkTime) {
        for (int i = 0; i < 78; i++) {
          if (i > 42 && i < 79) {
            ledBuffer.setRGB(i, 0, 255, 0);
          } else if (i < 42 && ally.equals(Alliance.Red)) {
            ledBuffer.setRGB(i, 255, 0, 0);
          } else if (id < 42 && ally.equals(Alliance.Blue)) {
            ledBuffer.setRGB(id, 0, 0, 255);
          }
        }
      } else if (ledBlinkTimer.get() > blinkTime && ledBlinkTimer.get() < blinkTime * 2) {
        for (int i = 0; i <= 24; i++) {
          ledBuffer.setRGB(i, 0, 0, 0);
        }
      } else {
        ledBlinkTimer.reset();
      }
    } else if (ledStatus == 3) { // red error
      if (ledBlinkTimer.get() < blinkTime) {
        for (int i = 0; i < 78; i++) {
          if (i > 42 && i < 79) {
            ledBuffer.setRGB(i, 255, 0, 0);
          } else if (i < 42 && ally.equals(Alliance.Red)) {
            ledBuffer.setRGB(i, 255, 0, 0);
          } else if (id < 42 && ally.equals(Alliance.Blue)) {
            ledBuffer.setRGB(id, 0, 0, 255);
          }
        }
      } else if (ledBlinkTimer.get() > blinkTime && ledBlinkTimer.get() < blinkTime * 2) {
        for (int i = 0; i <= 24; i++) {
          ledBuffer.setRGB(i, 0, 0, 0);
        }
      } else {
        ledBlinkTimer.reset();
      }
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    led.setLength(78);
    led.setData(ledBuffer);
    led.start();
  }
}
