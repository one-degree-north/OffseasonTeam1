// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ClawConstants;

public class Claw extends SubsystemBase {
  //objects
  private CANSparkMax m_rollerFollow;
  private CANSparkMax m_rollerLead;
  private SparkPIDController m_rollerPIDController;
  

    public  Claw(){
      m_rollerLead = new CANSparkMax(Constants.ClawConstants.rollerLeadID, CANSparkMax.MotorType.kBrushless);
      m_rollerFollow = new CANSparkMax(Constants.ClawConstants.rollerFollowID, CANSparkMax.MotorType.kBrushless);
    }


    public void Configs(){
      m_rollerFollow.restoreFactoryDefaults();
      m_rollerFollow.setInverted(false);
      m_rollerFollow.setIdleMode(CANSparkMax.IdleMode.kBrake);
      m_rollerFollow.setOpenLoopRampRate(ClawConstants.rollerRampRate);
      m_rollerFollow.setSmartCurrentLimit(20);

      m_rollerLead.restoreFactoryDefaults();
      m_rollerLead.setInverted(false);
      m_rollerLead.setIdleMode(CANSparkMax.IdleMode.kBrake);
      m_rollerLead.setOpenLoopRampRate(ClawConstants.rollerRampRate);
      m_rollerLead.setSmartCurrentLimit(20);

      //PID Controller Initialization
      m_rollerPIDController = m_rollerLead.getPIDController();

      //PID Configs 
      m_rollerPIDController.setP(ClawConstants.rollerskP);
      m_rollerPIDController.setI(ClawConstants.rollerskI);
      m_rollerPIDController.setD(ClawConstants.rollerskD);
      m_rollerPIDController.setFF(ClawConstants.rollerskFF); 
      m_rollerPIDController.setFeedbackDevice(m_rollerLead.getEncoder());

      m_rollerFollow.follow(m_rollerLead);
    }

    public void stopAll(){
      m_rollerLead.stopMotor();
      m_rollerFollow.stopMotor();
    }

    public void setRollerSpeedRPM(double speed){
      m_rollerPIDController.setReference(speed, ControlType.kVelocity);    
    }

    public void setRollerDS(double DSSpeed){
      m_rollerLead.set(DSSpeed);
    }

    public double getRollerVelocityRPM(){
      return m_rollerLead.getEncoder().getVelocity();
    }

    @Override
    public void periodic(){
      SmartDashboard.putNumber("Roller Speed", getRollerVelocityRPM());
    }
}
