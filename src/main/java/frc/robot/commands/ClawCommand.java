
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.ClawConstants;
import frc.robot.subsystems.Claw;

public class ClawCommand extends Command {
  /** Creates a new ClawCommand. */
  private Claw s_Claw;
  private ClawMode clawMode;
  private Command command;

  public ClawCommand(Claw claw, ClawMode clawMode) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.s_Claw = claw;
    this.clawMode = clawMode;
    addRequirements(s_Claw);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch(clawMode){
      case INTAKE:
        command = new InstantCommand(() -> s_Claw.setRollerSpeedRPM(ClawConstants.intakeRollerRPM));
        break;
      case OUTTAKE:
        command = new InstantCommand(() -> s_Claw.setRollerSpeedRPM(ClawConstants.outtakeRollerRPM));
        break;
      case INTAKEDS:
        command = new InstantCommand(() -> s_Claw.setRollerDS(ClawConstants.rollerFeedDS));
        break;
      case OUTTAKEDS:
        command = new InstantCommand(() -> s_Claw.setRollerDS(ClawConstants.rollerOutputDS));
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
    s_Claw.stopAll();
    }
  

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return command.isFinished();
  }

  public enum ClawMode {
    INTAKE,
    OUTTAKE,
    INTAKEDS,
    OUTTAKEDS
  }
}
