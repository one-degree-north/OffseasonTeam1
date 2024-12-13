// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Grintake extends SubsystemBase {
  private CANSparkMax m_intakeLeader;
  private CANSparkMax m_intakeFollower;
  private TalonFX m_pivot;
  private TalonFXConfiguration pivotConfig;
  private SparkPIDController m_intakePIDController;
  private MotionMagicVoltage pivotMotionMagic;

    public Grintake(){
      pivotConfig = new TalonFXConfiguration();
      pivotMotionMagic = new MotionMagicVoltage(0).withSlot(0);
      m_intakeFollower = new CANSparkMax(Constants.GrintakeConstants.intakeFollowerID, CANSparkMax.MotorType.kBrushless);
      m_intakeLeader = new CANSparkMax(Constants.GrintakeConstants.intakeLeaderID, CANSparkMax.MotorType.kBrushless);
      m_pivot = new TalonFX(Constants.GrintakeConstants.pivotID);
      m_intakePIDController = m_intakeLeader.getPIDController();
      configMotors();   
    }


    public void configMotors(){
      m_intakeLeader.setIdleMode(CANSparkMax.IdleMode.kBrake);
      m_intakeLeader.setInverted(true);
      m_intakeLeader.setSmartCurrentLimit(20);
      m_intakeLeader.setOpenLoopRampRate(Constants.GrintakeConstants.intakeRampRate);

      m_intakeFollower.setInverted(false);
      m_intakeFollower.setIdleMode(CANSparkMax.IdleMode.kBrake);
      m_intakeFollower.setOpenLoopRampRate(Constants.GrintakeConstants.intakeRampRate);
      m_intakeFollower.setSmartCurrentLimit(20);
      
      pivotConfig.CurrentLimits.StatorCurrentLimitEnable = true;
      pivotConfig.CurrentLimits.StatorCurrentLimit = 80;

      //PID Configs 
      m_intakePIDController.setP(Constants.GrintakeConstants.intakekP);
      m_intakePIDController.setI(Constants.GrintakeConstants.intakekI);
      m_intakePIDController.setD(Constants.GrintakeConstants.intakekD);
      m_intakePIDController.setFF(Constants.GrintakeConstants.intakekFF); 

      pivotConfig.Slot0.kP = Constants.GrintakeConstants.pivotkP;
      pivotConfig.Slot0.kI = Constants.GrintakeConstants.pivotkI;
      pivotConfig.Slot0.kD = Constants.GrintakeConstants.pivotkD;
      pivotConfig.Slot0.kS = Constants.GrintakeConstants.pivotkS;
      pivotConfig.Slot0.kV = Constants.GrintakeConstants.pivotkV;
      pivotConfig.Slot0.kA = Constants.GrintakeConstants.pivotkA;

      m_intakeFollower.follow(m_intakeLeader);
      m_pivot.getConfigurator().apply(pivotConfig);
    }

    public void stopAll(){
      m_intakeLeader.stopMotor();
      m_intakeFollower.stopMotor();
      m_pivot.setControl(new NeutralOut());
    }

    public void setIntakeSpeedRPM(double speed){
      m_intakePIDController.setReference(speed, ControlType.kVelocity);
    }

    public void setPivotPosition(double position){
      m_pivot.setControl(pivotMotionMagic.withPosition(position));
    }

    public void setPivotDS(double DSSpeed){
      m_pivot.set(DSSpeed);
    }
    
    public void setIntakeDS(double DSSpeed){
      m_intakeLeader.set(DSSpeed);
    }

    public double getIntakeVelocity(){
      return m_intakeFollower.getEncoder().getVelocity();
    }

    public double getPivotRotations(){
      return m_pivot.getPosition().getValueAsDouble();
    }

    @Override
    public void periodic(){
      SmartDashboard.putNumber("Claw Top Speed", getIntakeVelocity());
      SmartDashboard.putNumber("Grintake Pivot Position", getPivotRotations());
    }
} 
