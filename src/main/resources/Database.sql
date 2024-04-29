-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.35 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for collegemanagementapp
DROP DATABASE IF EXISTS `collegemanagementapp`;
CREATE DATABASE IF NOT EXISTS `collegemanagementapp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `collegemanagementapp`;

-- Dumping structure for table collegemanagementapp.admin
DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `AdminPK` bigint NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `LoginFK` bigint NOT NULL,
  `RoleFK` bigint NOT NULL,
  PRIMARY KEY (`AdminPK`),
  KEY `Loginfk_Admin` (`LoginFK`),
  KEY `RoleFK_Admin` (`RoleFK`),
  CONSTRAINT `Loginfk_Admin` FOREIGN KEY (`LoginFK`) REFERENCES `login` (`LoginPK`),
  CONSTRAINT `RoleFK_Admin` FOREIGN KEY (`RoleFK`) REFERENCES `role` (`RolePK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.anouncement
DROP TABLE IF EXISTS `anouncement`;
CREATE TABLE IF NOT EXISTS `anouncement` (
  `AccouncementPK` bigint NOT NULL AUTO_INCREMENT,
  `TextMessage` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`AccouncementPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.assignment
DROP TABLE IF EXISTS `assignment`;
CREATE TABLE IF NOT EXISTS `assignment` (
  `AssignmentPK` bigint NOT NULL AUTO_INCREMENT,
  `SubjectFK` bigint NOT NULL,
  `USN` varchar(50) NOT NULL DEFAULT '',
  `isSubmitted` tinyint NOT NULL DEFAULT (0),
  PRIMARY KEY (`AssignmentPK`),
  KEY `FK1_assignment_subject` (`SubjectFK`),
  KEY `FK2_assignment_student` (`USN`) USING BTREE,
  CONSTRAINT `FK1_assignment_subject` FOREIGN KEY (`SubjectFK`) REFERENCES `subject` (`SubjectPK`),
  CONSTRAINT `FK2_assignment_student` FOREIGN KEY (`USN`) REFERENCES `student` (`USN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.attendance
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE IF NOT EXISTS `attendance` (
  `AttendancePK` bigint NOT NULL AUTO_INCREMENT,
  `USN` varchar(50) NOT NULL,
  `SubjectFK` bigint NOT NULL,
  `Sem` int NOT NULL,
  `TotalNumberofClasses` int NOT NULL,
  `AttendedClasses` int NOT NULL,
  `AttendancePercentage` double NOT NULL,
  PRIMARY KEY (`AttendancePK`),
  KEY `FK1_att_student` (`USN`),
  KEY `FK2_Subject_att` (`SubjectFK`),
  CONSTRAINT `FK1_att_student` FOREIGN KEY (`USN`) REFERENCES `student` (`USN`),
  CONSTRAINT `FK2_Subject_att` FOREIGN KEY (`SubjectFK`) REFERENCES `subject` (`SubjectPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.department
DROP TABLE IF EXISTS `department`;
CREATE TABLE IF NOT EXISTS `department` (
  `DeptPK` bigint NOT NULL AUTO_INCREMENT,
  `DepartmentName` varchar(50) NOT NULL,
  PRIMARY KEY (`DeptPK`),
  UNIQUE KEY `DepartmentName` (`DepartmentName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.externalmarks
DROP TABLE IF EXISTS `externalmarks`;
CREATE TABLE IF NOT EXISTS `externalmarks` (
  `ExternalMarksPK` bigint NOT NULL AUTO_INCREMENT,
  `USN` varchar(50) NOT NULL,
  `SubjectFK` bigint NOT NULL DEFAULT (0),
  `InternalMarksFK` bigint NOT NULL,
  `ExternalMarksMaximum` bigint NOT NULL DEFAULT (0),
  `ExternalMarksScored` double NOT NULL,
  `Sem` int NOT NULL DEFAULT (0),
  `DeptFK` bigint NOT NULL,
  PRIMARY KEY (`ExternalMarksPK`),
  KEY `FK1_ext_Student` (`USN`),
  KEY `FK2_ext_Subject` (`SubjectFK`),
  KEY `FK3_Dept_Ext` (`DeptFK`),
  KEY `FK4_internalMarks_FK` (`InternalMarksFK`),
  CONSTRAINT `FK1_ext_Student` FOREIGN KEY (`USN`) REFERENCES `student` (`USN`),
  CONSTRAINT `FK2_ext_Subject` FOREIGN KEY (`SubjectFK`) REFERENCES `subject` (`SubjectPK`),
  CONSTRAINT `FK3_Dept_Ext` FOREIGN KEY (`DeptFK`) REFERENCES `department` (`DeptPK`),
  CONSTRAINT `FK4_internalMarks_FK` FOREIGN KEY (`InternalMarksFK`) REFERENCES `internalmarks` (`InternalMarksPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.feereceipt
DROP TABLE IF EXISTS `feereceipt`;
CREATE TABLE IF NOT EXISTS `feereceipt` (
  `FeeReceiptPK` bigint NOT NULL AUTO_INCREMENT,
  `Receipt` longblob NOT NULL,
  `fileName` varchar(50) NOT NULL,
  PRIMARY KEY (`FeeReceiptPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.feestructure
DROP TABLE IF EXISTS `feestructure`;
CREATE TABLE IF NOT EXISTS `feestructure` (
  `FeeStructurePK` bigint NOT NULL AUTO_INCREMENT,
  `DeptFK` bigint NOT NULL,
  `Sem` tinyint NOT NULL DEFAULT (0),
  `SemFee` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`FeeStructurePK`),
  KEY `FK1_dept` (`DeptFK`),
  CONSTRAINT `FK1_dept` FOREIGN KEY (`DeptFK`) REFERENCES `department` (`DeptPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.internalmarks
DROP TABLE IF EXISTS `internalmarks`;
CREATE TABLE IF NOT EXISTS `internalmarks` (
  `InternalMarksPK` bigint NOT NULL AUTO_INCREMENT,
  `USN` varchar(50) NOT NULL DEFAULT '0',
  `SubjectFK` bigint NOT NULL DEFAULT '0',
  `MaximumMarks` bigint NOT NULL DEFAULT '0',
  `MarksScored` double NOT NULL DEFAULT (0),
  PRIMARY KEY (`InternalMarksPK`),
  KEY `FK1_internal_student` (`USN`),
  KEY `FK2_internal_Subject` (`SubjectFK`),
  CONSTRAINT `FK1_internal_student` FOREIGN KEY (`USN`) REFERENCES `student` (`USN`),
  CONSTRAINT `FK2_internal_Subject` FOREIGN KEY (`SubjectFK`) REFERENCES `subject` (`SubjectPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.login
DROP TABLE IF EXISTS `login`;
CREATE TABLE IF NOT EXISTS `login` (
  `LoginPK` bigint NOT NULL AUTO_INCREMENT,
  `LoginName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Createddate` datetime NOT NULL DEFAULT (now()),
  `DelFlag` tinyint NOT NULL DEFAULT '0',
  `SecretKey` varchar(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`LoginPK`) USING BTREE,
  UNIQUE KEY `LoginName` (`LoginName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.role
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `RolePK` bigint NOT NULL AUTO_INCREMENT,
  `RoleName` varchar(50) NOT NULL,
  PRIMARY KEY (`RolePK`),
  UNIQUE KEY `RoleName` (`RoleName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.staff
DROP TABLE IF EXISTS `staff`;
CREATE TABLE IF NOT EXISTS `staff` (
  `StaffID` bigint NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `CreatedDate` datetime NOT NULL DEFAULT (now()),
  `DelFlag` tinyint NOT NULL DEFAULT (0),
  `DeptFK` bigint NOT NULL DEFAULT (0),
  `LoginFK` bigint NOT NULL DEFAULT (0),
  `RoleFK` bigint NOT NULL DEFAULT (0),
  PRIMARY KEY (`StaffID`),
  KEY `FK__login` (`LoginFK`),
  KEY `FK__role` (`RoleFK`),
  KEY `FK_Dept` (`DeptFK`),
  CONSTRAINT `FK__login` FOREIGN KEY (`LoginFK`) REFERENCES `login` (`LoginPK`),
  CONSTRAINT `FK__role` FOREIGN KEY (`RoleFK`) REFERENCES `role` (`RolePK`),
  CONSTRAINT `FK_Dept` FOREIGN KEY (`DeptFK`) REFERENCES `department` (`DeptPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.student
DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `StudentPK` bigint NOT NULL AUTO_INCREMENT,
  `USN` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Sem` int NOT NULL,
  `DeptFK` bigint NOT NULL,
  `CreatedDate` datetime NOT NULL DEFAULT (now()),
  `LoginFK` bigint NOT NULL,
  `RoleFK` bigint NOT NULL,
  `Status` tinyint NOT NULL DEFAULT (0) COMMENT '0 --> InActive, 1--> Active',
  `StudentName` varchar(50) NOT NULL,
  `DateOfBirth` date NOT NULL,
  `Gender` tinyint NOT NULL DEFAULT (0) COMMENT '0 --> Female, 1 --> Male',
  `StudentEmailID` varchar(50) NOT NULL,
  PRIMARY KEY (`StudentPK`) USING BTREE,
  UNIQUE KEY `USN` (`USN`),
  KEY `Dept_FK` (`DeptFK`),
  KEY `LoginFK_Student` (`LoginFK`),
  KEY `RoleFK_Student` (`RoleFK`),
  CONSTRAINT `Dept_FK` FOREIGN KEY (`DeptFK`) REFERENCES `department` (`DeptPK`),
  CONSTRAINT `LoginFK_Student` FOREIGN KEY (`LoginFK`) REFERENCES `login` (`LoginPK`),
  CONSTRAINT `RoleFK_Student` FOREIGN KEY (`RoleFK`) REFERENCES `role` (`RolePK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.studentfee
DROP TABLE IF EXISTS `studentfee`;
CREATE TABLE IF NOT EXISTS `studentfee` (
  `StudentFeePK` bigint NOT NULL AUTO_INCREMENT,
  `FeeFK` bigint NOT NULL,
  `StudentFK` bigint NOT NULL,
  `isFeePending` tinyint NOT NULL,
  `TotalFeePaid` double NOT NULL,
  `ReceiptFK` bigint DEFAULT NULL,
  `CreatedDate` datetime NOT NULL DEFAULT (now()),
  PRIMARY KEY (`StudentFeePK`),
  KEY `FK2_StudentFK` (`StudentFK`),
  KEY `FK3_FeeReceipt` (`ReceiptFK`),
  KEY `FK1_feeStructure` (`FeeFK`),
  CONSTRAINT `FK1_feeStructure` FOREIGN KEY (`FeeFK`) REFERENCES `feestructure` (`FeeStructurePK`),
  CONSTRAINT `FK2_StudentFK` FOREIGN KEY (`StudentFK`) REFERENCES `student` (`StudentPK`),
  CONSTRAINT `FK3_FeeReceipt` FOREIGN KEY (`ReceiptFK`) REFERENCES `feereceipt` (`FeeReceiptPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.subject
DROP TABLE IF EXISTS `subject`;
CREATE TABLE IF NOT EXISTS `subject` (
  `SubjectPK` bigint NOT NULL AUTO_INCREMENT,
  `SubjectName` varchar(50) NOT NULL,
  `SubjectCode` varchar(50) NOT NULL,
  `DeptFK` bigint NOT NULL DEFAULT (0),
  `Sem` int NOT NULL,
  PRIMARY KEY (`SubjectPK`),
  UNIQUE KEY `SubjectCode` (`SubjectCode`),
  KEY `FK1_dept_Subject` (`DeptFK`),
  CONSTRAINT `FK1_dept_Subject` FOREIGN KEY (`DeptFK`) REFERENCES `department` (`DeptPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table collegemanagementapp.timetable
DROP TABLE IF EXISTS `timetable`;
CREATE TABLE IF NOT EXISTS `timetable` (
  `TimeTablePK` bigint NOT NULL AUTO_INCREMENT,
  `FromTime` time NOT NULL,
  `ToTime` time NOT NULL,
  `Day` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `StaffFK` bigint NOT NULL,
  `SubjectFK` bigint NOT NULL,
  `DeptFK` bigint NOT NULL,
  `Sem` int NOT NULL,
  PRIMARY KEY (`TimeTablePK`),
  KEY `FK2_Sub_Dept` (`DeptFK`),
  KEY `FK3_SubjectFK` (`SubjectFK`),
  KEY `createdBy_StaffPK` (`StaffFK`) USING BTREE,
  CONSTRAINT `createdBy_StaffPK` FOREIGN KEY (`StaffFK`) REFERENCES `staff` (`StaffID`),
  CONSTRAINT `FK2_Sub_Dept` FOREIGN KEY (`DeptFK`) REFERENCES `department` (`DeptPK`),
  CONSTRAINT `FK3_SubjectFK` FOREIGN KEY (`SubjectFK`) REFERENCES `subject` (`SubjectPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for procedure collegemanagementapp.uspgetAssignment
DROP PROCEDURE IF EXISTS `uspgetAssignment`;
DELIMITER //
CREATE PROCEDURE `uspgetAssignment`(
	IN `dept_fk` VARCHAR(50),
	IN `_sem` INT
)
BEGIN
  SELECT assmt.* FROM assignment assmt
INNER JOIN student s ON assmt.USN = s.USN
INNER JOIN subject sub ON sub.SubjectPK = assmt.SubjectFK
INNER JOIN department dp ON dp.DeptPK = sub.DeptFK
WHERE s.sem = _sem AND dp.DepartmentName = dept_fk;
END//
DELIMITER ;

-- Dumping structure for procedure collegemanagementapp.uspgetAssignmentByUSNandSem
DROP PROCEDURE IF EXISTS `uspgetAssignmentByUSNandSem`;
DELIMITER //
CREATE PROCEDURE `uspgetAssignmentByUSNandSem`(
	IN `USN` VARCHAR(50),
	IN `_sem` INT
)
BEGIN
  SELECT assmt.* FROM assignment assmt
INNER JOIN student s ON assmt.USN = s.USN
INNER JOIN subject sub ON sub.SubjectPK = assmt.SubjectFK
INNER JOIN department dp ON dp.DeptPK = sub.DeptFK
WHERE sub.sem = _sem AND assmt.usn = USN;
END//
DELIMITER ;

-- Dumping structure for procedure collegemanagementapp.uspgetinternalmarksbySemAndDept
DROP PROCEDURE IF EXISTS `uspgetinternalmarksbySemAndDept`;
DELIMITER //
CREATE PROCEDURE `uspgetinternalmarksbySemAndDept`(
	IN `sem` INT,
	IN `deptName` VARCHAR(50)
)
BEGIN
SELECT m.* 
    FROM internalmarks m 
    INNER JOIN subject s ON s.SubjectPK = m.SubjectFK
    INNER JOIN department d ON d.DeptPK = s.DeptFK
    WHERE s.Sem = sem AND d.DepartmentName = deptName;
END//
DELIMITER ;

-- Dumping structure for procedure collegemanagementapp.uspgetinternalmarksbyusnAndSem
DROP PROCEDURE IF EXISTS `uspgetinternalmarksbyusnAndSem`;
DELIMITER //
CREATE PROCEDURE `uspgetinternalmarksbyusnAndSem`(
	IN `sem` INT,
	IN `usn` VARCHAR(50)
)
BEGIN
SELECT m.* FROM internalmarks m 
INNER JOIN subject s ON s.SubjectPK = m.SubjectFK
WHERE s.Sem = sem AND m.USN = usn;
END//
DELIMITER ;

-- Dumping structure for procedure collegemanagementapp.uspIdentifyDuplicateTimeTable
DROP PROCEDURE IF EXISTS `uspIdentifyDuplicateTimeTable`;
DELIMITER //
CREATE PROCEDURE `uspIdentifyDuplicateTimeTable`(
	IN `fromTime` TIME,
	IN `toTime` TIME,
	IN `_day` VARCHAR(50),
	IN `deptFK` INT,
	IN `sem` INT
)
BEGIN
SELECT t.* FROM timetable t WHERE t.fromtime = fromTime AND t.totime = toTime AND t.Day = _day AND t.DeptFK = deptFK
 AND t.Sem=sem; 
END//
DELIMITER ;

-- Dumping structure for procedure collegemanagementapp.uspIdentifyDuplicateTimeTableSubject
DROP PROCEDURE IF EXISTS `uspIdentifyDuplicateTimeTableSubject`;
DELIMITER //
CREATE PROCEDURE `uspIdentifyDuplicateTimeTableSubject`(
    IN `from_time` TIME,
    IN `to_time` TIME,
    IN `_day` VARCHAR(50),
    IN `dept_fk` INT,
    IN `sem` INT,
    IN `subject_fk` INT
)
BEGIN
    SELECT t.* 
    FROM `timetable` t 
    WHERE t.`fromtime` = from_time 
    AND t.`totime` = to_time 
    AND t.`Day` = _day 
    AND t.`DeptFK` = dept_fk
    AND t.`Sem` = sem 
    AND t.`SubjectFK` != subject_fk; 
END//
DELIMITER ;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
