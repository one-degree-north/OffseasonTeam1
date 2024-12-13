// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.




 //current limit--> neo: 20amps, falcon 500: 40amps, kracken: 35?
 //2 falcons


 package frc.robot.subsystems;


 import com.ctre.phoenix6.controls.NeutralOut;
 import com.ctre.phoenix6.controls.PositionVoltage;
 import com.ctre.phoenix6.configs.TalonFXConfiguration;
 import com.ctre.phoenix6.hardware.TalonFX;
 import com.ctre.phoenix6.controls.Follower;
 import com.ctre.phoenix6.signals.InvertedValue;
 import com.ctre.phoenix6.signals.NeutralModeValue;
 
 import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.pivotConstants;
 
 public class Pivot extends SubsystemBase {
 
  private TalonFX m_pivotLead;
  private TalonFX m_pivotFollow;
  private TalonFXConfiguration motorConfigs;
  private PositionVoltage positionVoltageRequest;
 
 public Pivot(){
  m_pivotLead = new TalonFX(Constants.pivotConstants.leftMotorID, "rio");
  m_pivotFollow = new TalonFX(Constants.pivotConstants.rightMotorID, "rio");
  positionVoltageRequest = new PositionVoltage(0).withSlot(0);
  m_pivotFollow.setControl(new Follower(m_pivotLead.getDeviceID(), true));
  motorConfigs = new TalonFXConfiguration();
  motorConfig();
 }
 
 private void motorConfig(){
 
  motorConfigs.Slot0.kP = pivotConstants.pivotkP;
  motorConfigs.Slot0.kI = pivotConstants.pivotkI;
  motorConfigs.Slot0.kD = pivotConstants.pivotkD;
  motorConfigs.Slot0.kS = pivotConstants.pivotkS;
  motorConfigs.Slot0.kV = pivotConstants.pivotkV;
  motorConfigs.Slot0.kA = pivotConstants.pivotkA;
 
  motorConfigs.CurrentLimits.SupplyCurrentLimit = 35;
  motorConfigs.CurrentLimits.SupplyCurrentLimitEnable = true;
  motorConfigs.CurrentLimits.SupplyCurrentThreshold = 60;
  motorConfigs.CurrentLimits.SupplyTimeThreshold = 0.1;
  motorConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
  motorConfigs.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
  motorConfigs.MotionMagic.MotionMagicCruiseVelocity = pivotConstants.pivotCruiseVelocity;
  motorConfigs.MotionMagic.MotionMagicAcceleration = pivotConstants.pivotCruiseAcceleration;

  m_pivotLead.getConfigurator().apply(motorConfigs);
  m_pivotFollow.getConfigurator().apply(motorConfigs);
 }
 
 public void setPivotPosRot(double setpoint){
  m_pivotLead.setControl(positionVoltageRequest.withPosition(setpoint));
}

public void stopAll(){
   m_pivotLead.setControl(new NeutralOut());
}

public void setPivotDS(double SpeedDS){
   m_pivotLead.set(SpeedDS);
}
 
 public double getPositionLeft(){
    return m_pivotLead.getPosition().getValue();
 }
 
 
 public double getPositionRight (){
    return m_pivotFollow.getPosition().getValue();
 }
 
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Left Position", getPositionLeft());
    SmartDashboard.putNumber("Right Position", getPositionRight());
  }
 }
 
 
 