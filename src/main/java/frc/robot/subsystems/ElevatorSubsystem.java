// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorConstants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ElevatorSubsystem extends SubsystemBase {
  /** Creates a new ElevatorSubsystem. */
  private final CANSparkMax elevator = new CANSparkMax(ElevatorConstants.ELEVATOR_RIGHT, MotorType.kBrushless);
  private final RelativeEncoder encoderElevator = elevator.getEncoder();
  private final PIDController elevatorController = new PIDController(ElevatorConstants.TURN_KP, ElevatorConstants.TURN_KI,
  ElevatorConstants.TURN_KD);
  
  public double getEncoderElevatorPosition() {  // returns the current encoder distance
    return (encoderElevator.getPosition());
  }

  public boolean atTop() { // check if elevator is at max height
    return encoderElevator.getPosition() >= ElevatorConstants.ELEVATOR_ENCODER_MAX;
  }

  public double getElevatorControllerSpeed(double setpoint) { // takes the desired setpoint as a parameter
    return (elevatorController.calculate(getEncoderElevatorPosition(), setpoint)); // returns the elevator speed calculated by the PID
  }

  public PIDController getElevatorController() {  // return PID info
    return (elevatorController);
  }

  public ElevatorSubsystem() { // invert motor
    elevator.setInverted(true);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Elevator Right Encoder", getEncoderElevatorPosition()); // displays current encoder distance on SmartDashboard
    SmartDashboard.putNumber("Elevator PID Speed", elevatorController.calculate(getEncoderElevatorPosition(), -11.73)); // displays currently calculated elevator speed
    // This method will be called once per scheduler run
  }

  public void setMotor(double speed) {
    elevator.set(speed);
  }

}
