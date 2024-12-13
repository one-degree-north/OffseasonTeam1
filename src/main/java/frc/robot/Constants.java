// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public final class Constants{
        public class GrintakeConstants {
    
            public static final int intakeFollowerID = 5;
            public static final int intakeLeaderID = 6;
            public static final int pivotID = 30;
    
            public static final double intakekP = 0;
            public static final double intakekI = 0;
            public static final double intakekD = 0;
            public static final double intakekFF = 0;
            public static final double intakeRampRate = 2;
    
            public static final double pivotkP = 0;
            public static final double pivotkI = 0;
            public static final double pivotkD = 0;
            public static final double pivotkS = 0;
            public static final double pivotkV = 0;
            public static final double pivotkA = 0;

            public static final double pivotDS = 0.4;
            public static final double intakeDS = 0.6;
            public static final double feedingDS = 0;
            public static final double outputDS = 0;
    
            public static final double intakeRPM = 0;
            public static final double feedingRPM = 0;
            public static final double outtakeRPM = 0;
    
            public static final double pivotGroundPos = 0;
            public static final double pivotDockedPos = 0;
        }
    
        public static final class pivotConstants {
     
            public static final int leftMotorID = 13;
            public static final int rightMotorID = 14;
          
            public static final double pivotkP = 0;
            public static final double pivotkI = 0;
            public static final double pivotkD = 0;
            public static final double pivotkS = 0;
            public static final double pivotkV = 0;
            public static final double pivotkA = 0;
            public static final double pivotCruiseVelocity = 3;
            public static final double pivotCruiseAcceleration = 5;

            public static final double pivotDS = 1;
    
            public static final double pivotDockedPos = 0;
            public static final double pivotFeedPos = 0;
            public static final double pivotMidScorePos = 0;
            public static final double pivotHighScorePos = 0;
          }
    
          public class ClawConstants {
    
            public static final int rollerLeadID = 20;
            public static final int rollerFollowID = 21;
    
            public static final double rollerskP = 0;
            public static final double rollerskI = 0;
            public static final double rollerskD = 0;
            public static final double rollerskFF = 0;
            public static final double rollerRampRate = 3;

            public static final double rollerFeedDS = 0.6;
            public static final double rollerOutputDS = 0;
    
            public static final double intakeRollerRPM = 0;
            public static final double outtakeRollerRPM = 0;
        
        }
}