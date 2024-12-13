// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.commands.ClawCommand;
import frc.robot.commands.ClawCommand.ClawMode;
import frc.robot.commands.GrintakeCommand;
import frc.robot.commands.GrintakeCommand.GrintakeMode;
import frc.robot.commands.PivotCommand;
import frc.robot.commands.PivotCommand.PivotPosition;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Grintake;
import frc.robot.subsystems.Pivot;

public class RobotContainer {
  
  private final Grintake s_Grintake = new Grintake();
  private final Pivot s_Pivot = new Pivot();
  private final Claw s_Claw = new Claw();
  
  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandPS5Controller mainController = new CommandPS5Controller(0); // My joystick
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop

  private final Telemetry logger = new Telemetry(MaxSpeed);

  private void configureBindings() {

    mainController.R1().whileTrue(
      new PivotCommand(s_Pivot, PivotPosition.UPDS, true)
    );

    mainController.R2().whileTrue(
      new PivotCommand(s_Pivot, PivotPosition.DOWNDS, true)
    );

    mainController.L1().whileTrue(
      new GrintakeCommand(s_Grintake, GrintakeMode.PIVOTUPDS, true)
    );
    
    mainController.L2().whileTrue(
      new GrintakeCommand(s_Grintake, GrintakeMode.PIVOTDOWNDS, true)
    );

    mainController.cross().whileTrue(
      new GrintakeCommand(s_Grintake, GrintakeMode.INTAKEDS, true)
    );

    mainController.circle().whileTrue(
      new GrintakeCommand(s_Grintake, GrintakeMode.OUTTAKEDS, true)
    );

    mainController.square().whileTrue(
      Commands.parallel(
        new GrintakeCommand(s_Grintake, GrintakeMode.FEEDING, true),
        new ClawCommand(s_Claw, ClawMode.INTAKEDS, true)
      )
    );
    
    mainController.triangle().whileTrue(
      new ClawCommand(s_Claw, ClawMode.OUTTAKEDS, true)
    );

    // mainController.R2().whileTrue(
    //         new GrintakeCommand(s_Grintake, GrintakeMode.INTAKE, true)
    //     );

    //     mainController.circle().whileTrue(
    //         Commands.sequence(
    //             new PivotCommand(s_Pivot, PivotPosition.FEEDING, true),
    //             Commands.parallel(
    //                 new GrintakeCommand(s_Grintake, GrintakeMode.FEEDING, false),
    //                 new ClawCommand(s_Claw, ClawMode.INTAKE, false)
    //             )
    //         )
    //     );

    //     mainController.L1().whileTrue(
    //         Commands.sequence(
    //             new PivotCommand(s_Pivot, PivotPosition.MIDSCORE, true),
    //             new ClawCommand(s_Claw, ClawMode.OUTTAKE, false)
    //         )
    //     );

    //     mainController.L2().whileTrue(
    //         Commands.sequence(
    //             new PivotCommand(s_Pivot, PivotPosition.HIGHSCORE, true),
    //             new ClawCommand(s_Claw, ClawMode.OUTTAKE, false)
    //         )
    //     );

    //     mainController.cross().whileTrue(
    //         new GrintakeCommand(s_Grintake, GrintakeMode.LOWSCORE, true)
    //     );

    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-mainController.getLeftY() * MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-mainController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-mainController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    // reset the field-centric heading on left bumper press
    mainController.L1().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);
  }

  public RobotContainer() {
    configureBindings();
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
