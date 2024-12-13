// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.GrintakeConstants;
import frc.robot.subsystems.Grintake;

public class GrintakeCommand extends Command {
  /** Creates a new GrintakeCommand. */
  private Grintake s_Grintake;
  private GrintakeMode grintakeMode;
  private Command command;
  private boolean isFinished;
  public GrintakeCommand(Grintake s_Grintake, GrintakeMode grintakeMode, boolean isFinished) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.s_Grintake = s_Grintake;
    this.grintakeMode = grintakeMode;
    this.isFinished = isFinished;
    addRequirements(s_Grintake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch(grintakeMode){
      case INTAKE:
        command = new InstantCommand(() -> s_Grintake.setIntakeSpeedRPM(GrintakeConstants.intakeRPM))
        .alongWith(new InstantCommand(() -> s_Grintake.setPivotPosition(GrintakeConstants.pivotGroundPos)));
        break;
      case FEEDING:
        command = new InstantCommand(() -> s_Grintake.setIntakeSpeedRPM(GrintakeConstants.feedingRPM))
        .alongWith(new InstantCommand(() -> s_Grintake.setPivotPosition(GrintakeConstants.pivotDockedPos)));
        break;
      case LOWSCORE:
        command = new InstantCommand(() -> s_Grintake.setIntakeSpeedRPM(GrintakeConstants.outtakeRPM))
        .alongWith(new InstantCommand(() -> s_Grintake.setPivotPosition(GrintakeConstants.pivotDockedPos)));
        break;
      case INTAKEDS:
        command = new InstantCommand(()-> s_Grintake.setIntakeDS(GrintakeConstants.intakeDS));
        break;
      case FEEDINGDS:
        command = new InstantCommand(()-> s_Grintake.setIntakeDS(GrintakeConstants.feedingDS));
        break;
      case PIVOTUPDS:
        command = new InstantCommand(()-> s_Grintake.setPivotDS(GrintakeConstants.pivotDS));
        break;
      case PIVOTDOWNDS:
        command = new InstantCommand(()-> s_Grintake.setPivotDS(-GrintakeConstants.pivotDS));
        break;
      case OUTTAKEDS:
        command = new InstantCommand(()-> s_Grintake.setIntakeDS(GrintakeConstants.outputDS));
        break;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    command.execute();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (isFinished) {
      s_Grintake.stopAll();
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public enum GrintakeMode {
    INTAKE,
    FEEDING,
    LOWSCORE,
    INTAKEDS,
    FEEDINGDS,
    PIVOTUPDS,
    PIVOTDOWNDS,
    OUTTAKEDS
  }
}
