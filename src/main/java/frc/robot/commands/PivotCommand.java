// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.pivotConstants;
import frc.robot.subsystems.Pivot;

public class PivotCommand extends Command {
  private PivotPosition pivotPositions;
  private Pivot s_Pivot;
  private Command command;
  private boolean isFinished;

  public PivotCommand(Pivot s_Pivot, PivotPosition pivotPositions, boolean isFinished) {
    this.s_Pivot = s_Pivot;
    this.pivotPositions = pivotPositions;
    this.isFinished = isFinished;
    addRequirements(s_Pivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch (pivotPositions) {
      case DOCKED:
        command = new InstantCommand(() -> s_Pivot.setPivotPosRot(pivotConstants.pivotDockedPos));
        break;
      case FEEDING:
        command = new InstantCommand(() -> s_Pivot.setPivotPosRot(pivotConstants.pivotFeedPos));
        break;
      case MIDSCORE:
        command = new InstantCommand(() -> s_Pivot.setPivotPosRot(pivotConstants.pivotMidScorePos));
        break;
      case HIGHSCORE:
        command = new InstantCommand(() -> s_Pivot.setPivotPosRot(pivotConstants.pivotHighScorePos));
        break;
      case UPDS:
        command = new InstantCommand(() -> s_Pivot.setPivotDS(pivotConstants.pivotDS));
        break;
      case DOWNDS:
        command = new InstantCommand(() -> s_Pivot.setPivotDS(-pivotConstants.pivotDS));
        break;
    }
    command.initialize();
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
      s_Pivot.stopAll();
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return command.isFinished();
  }

  public enum PivotPosition {
    DOCKED,
    FEEDING,
    MIDSCORE,
    HIGHSCORE,
    UPDS,
    DOWNDS
  }
}
