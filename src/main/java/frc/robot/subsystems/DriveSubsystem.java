// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  public static record Hardware(
    WPI_TalonSRX lMasterMotor,
    WPI_TalonSRX rMasterMotor,
    WPI_TalonSRX lSlaveMotor,
    WPI_TalonSRX rSlaveMotor
  ) {}

  private WPI_TalonSRX m_lMasterMotor;
  private WPI_TalonSRX m_rMasterMotor;
  private WPI_TalonSRX m_lSlaveMotor;
  private WPI_TalonSRX m_rSlaveMotor;

  private DifferentialDrive m_drivetrain;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem(Hardware drivetrainHardware) {
    this.m_lMasterMotor = drivetrainHardware.lMasterMotor;
    this.m_rMasterMotor = drivetrainHardware.rMasterMotor;
    this.m_lSlaveMotor = drivetrainHardware.lSlaveMotor;
    this.m_rSlaveMotor = drivetrainHardware.rSlaveMotor;

    m_drivetrain = new DifferentialDrive(m_lMasterMotor, m_rMasterMotor);

    m_rSlaveMotor.setInverted(true);
    m_rMasterMotor.setInverted(true);

    m_lSlaveMotor.follow(m_lMasterMotor);
    m_rSlaveMotor.follow(m_rMasterMotor);
  }

  public static Hardware initializeHardware() {
    Hardware drivetrainHardware = new Hardware(
      new WPI_TalonSRX(Constants.DriveHardware.LEFT_FRONT_MASTER_MOTOR),
      new WPI_TalonSRX(Constants.DriveHardware.RIGHT_FRONT_MASTER_MOTOR),
      new WPI_TalonSRX(Constants.DriveHardware.LEFT_REAR_SLAVE_MOTOR),
      new WPI_TalonSRX(Constants.DriveHardware.RIGHT_REAR_SLAVE_MOTOR)
    );

    return drivetrainHardware;
  }

  public void set(double speed, double turn) {
    m_drivetrain.arcadeDrive(speed, turn);
  }

  public void stop() {
    m_drivetrain.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
