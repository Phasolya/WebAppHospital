-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`role` ;

CREATE TABLE IF NOT EXISTS `mydb`.`role` (
  `id` INT NOT NULL,
  `name` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`user` ;

CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `full_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `birth_date` DATE NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_roles_idx` (`role_id` ASC) VISIBLE,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  CONSTRAINT `fk_user_roles`
    FOREIGN KEY (`role_id`)
    REFERENCES `mydb`.`role` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `mydb`.`disease`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`disease` ;

CREATE TABLE IF NOT EXISTS `mydb`.`disease` (
  `id` INT NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `description` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `mydb`.`treatment_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`treatment_type` ;

CREATE TABLE IF NOT EXISTS `mydb`.`treatment_type` (
  `id` INT NOT NULL,
  `name` VARCHAR(15) NOT NULL,
  `description` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `mydb`.`doctor_has_patient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`doctor_has_patient` ;

CREATE TABLE IF NOT EXISTS `mydb`.`doctor_has_patient` (
  `doctor_id` INT NOT NULL,
  `patient_id` INT NOT NULL,
  PRIMARY KEY (`doctor_id`, `patient_id`),
  INDEX `fk_user_has_user_user2_idx` (`patient_id` ASC) VISIBLE,
  INDEX `fk_user_has_user_user1_idx` (`doctor_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_user_user1`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `mydb`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_has_user_user2`
    FOREIGN KEY (`patient_id`)
    REFERENCES `mydb`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `mydb`.`timestamps`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`timestamps` ;

CREATE TABLE IF NOT EXISTS `mydb`.`timestamps` (
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL);


-- -----------------------------------------------------
-- Table `mydb`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`category` ;

CREATE TABLE IF NOT EXISTS `mydb`.`category` (
  `category_id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`category_id`));


-- -----------------------------------------------------
-- Table `mydb`.`user_has_disease`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`user_has_disease` ;

CREATE TABLE IF NOT EXISTS `mydb`.`user_has_disease` (
  `user_id` INT NOT NULL,
  `disease_id` INT NOT NULL,
  `doctors_id` INT NOT NULL,
  `is_healed` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`, `disease_id`),
  INDEX `fk_user_has_disease_disease1_idx` (`disease_id` ASC) VISIBLE,
  INDEX `fk_user_has_disease_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_disease_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_has_disease_disease1`
    FOREIGN KEY (`disease_id`)
    REFERENCES `mydb`.`disease` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `mydb`.`treatment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`treatment` ;

CREATE TABLE IF NOT EXISTS `mydb`.`treatment` (
  `id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `is_done` TINYINT NOT NULL DEFAULT 0,
  `treatment_type_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `disease_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_treatment_treatment_type1_idx` (`treatment_type_id` ASC) VISIBLE,
  INDEX `fk_treatment_user_has_disease1_idx` (`user_id` ASC, `disease_id` ASC) VISIBLE,
  CONSTRAINT `fk_treatment_treatment_type1`
    FOREIGN KEY (`treatment_type_id`)
    REFERENCES `mydb`.`treatment_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_treatment_user_has_disease1`
    FOREIGN KEY (`user_id` , `disease_id`)
    REFERENCES `mydb`.`user_has_disease` (`user_id` , `disease_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `mydb`.`doctor_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`doctor_type` ;

CREATE TABLE IF NOT EXISTS `mydb`.`doctor_type` (
  `id` INT NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `mydb`.`doctor_has_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`doctor_has_type` ;

CREATE TABLE IF NOT EXISTS `mydb`.`doctor_has_type` (
  `doctor_id` INT NOT NULL,
  `doctor_type_id` INT NOT NULL,
  PRIMARY KEY (`doctor_id`, `doctor_type_id`),
  INDEX `fk_user_has_doctor_type_doctor_type1_idx` (`doctor_type_id` ASC) VISIBLE,
  INDEX `fk_user_has_doctor_type_user1_idx` (`doctor_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_doctor_type_user1`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `mydb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_doctor_type_doctor_type1`
    FOREIGN KEY (`doctor_type_id`)
    REFERENCES `mydb`.`doctor_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `mydb`.`role`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`role` (`id`, `name`) VALUES (0, 'admin');
INSERT INTO `mydb`.`role` (`id`, `name`) VALUES (1, 'doctor');
INSERT INTO `mydb`.`role` (`id`, `name`) VALUES (2, 'nurse');
INSERT INTO `mydb`.`role` (`id`, `name`) VALUES (3, 'patient');

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`user` (`id`, `login`, `password`, `full_name`, `email`, `birth_date`, `role_id`) VALUES (1, 'admin', 'admin', 'Vovnianko Maxym Nikolaevich', 'mvovnianko@gmail.com', '1991-06-09', 0);
INSERT INTO `mydb`.`user` (`id`, `login`, `password`, `full_name`, `email`, `birth_date`, `role_id`) VALUES (2, 'nurseTamara', 'nurseTamara', 'Zub Tamara Borisovna', 'tzub@gmail.com', '1987-04-12', 2);
INSERT INTO `mydb`.`user` (`id`, `login`, `password`, `full_name`, `email`, `birth_date`, `role_id`) VALUES (3, 'nurseMarina', 'nurseMarina', 'Sayenko Marina Olegovna', 'msaenko@gmail.com', '1985-05-04', 2);
INSERT INTO `mydb`.`user` (`id`, `login`, `password`, `full_name`, `email`, `birth_date`, `role_id`) VALUES (4, 'doctorCardiologist', 'DoctorCardiologist', 'Ratuch Dima Petrovich', 'dratuch@gmail.com', '1941-05-12', 1);
INSERT INTO `mydb`.`user` (`id`, `login`, `password`, `full_name`, `email`, `birth_date`, `role_id`) VALUES (5, 'doctorDentist', 'DoctorDentist', 'Umin Bogdan Vasilovych', 'bumin@gmail.com', '2000-03-21', 1);
INSERT INTO `mydb`.`user` (`id`, `login`, `password`, `full_name`, `email`, `birth_date`, `role_id`) VALUES (6, 'doctorENT', 'DoctorENT', 'Kusnir Ludmila Ivanovna', 'lkusnir@gmail.com', '1993-07-09', 1);
INSERT INTO `mydb`.`user` (`id`, `login`, `password`, `full_name`, `email`, `birth_date`, `role_id`) VALUES (7, 'PatientInna', 'PatientInna', 'Grona Inna Polyakovovna', 'igrona@gmail.ua', '1986-06-13', 3);
INSERT INTO `mydb`.`user` (`id`, `login`, `password`, `full_name`, `email`, `birth_date`, `role_id`) VALUES (8, 'PatientIvan', 'PatientIvan', 'Golik Ivan Bedrovich', 'ibedrovich@rambler.ru', '1999-09-11', 3);
INSERT INTO `mydb`.`user` (`id`, `login`, `password`, `full_name`, `email`, `birth_date`, `role_id`) VALUES (9, 'PatientBogdan', 'PatientBogdan', 'Bandera Bogdan Gerasimovich', 'bbandera@gmail.com', '1990-04-22', 3);
INSERT INTO `mydb`.`user` (`id`, `login`, `password`, `full_name`, `email`, `birth_date`, `role_id`) VALUES (10, 'PatientEduard', 'PatientEduard', 'Paniakin Eduard Eduardovich', 'epaniakin@gmail.com', '1992-06-09', 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`disease`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`disease` (`id`, `name`, `description`) VALUES (1, 'Trachea cancer', 'Trachea cancer');
INSERT INTO `mydb`.`disease` (`id`, `name`, `description`) VALUES (2, 'Diabetes mellitus', 'Diabetes mellitus');
INSERT INTO `mydb`.`disease` (`id`, `name`, `description`) VALUES (3, 'Alzheimer\'s disease', 'Alzheimer\'s disease');
INSERT INTO `mydb`.`disease` (`id`, `name`, `description`) VALUES (4, 'Diarrhea', 'Diarrhea');
INSERT INTO `mydb`.`disease` (`id`, `name`, `description`) VALUES (5, 'Tuberculosis', 'Tuberculosis');
INSERT INTO `mydb`.`disease` (`id`, `name`, `description`) VALUES (6, 'Cirrhosis', 'Cirrhosis');
INSERT INTO `mydb`.`disease` (`id`, `name`, `description`) VALUES (7, 'Stroke', 'Stroke');
INSERT INTO `mydb`.`disease` (`id`, `name`, `description`) VALUES (8, 'Malaria', 'Malaria');
INSERT INTO `mydb`.`disease` (`id`, `name`, `description`) VALUES (9, 'Lower Respiratory Infection', 'Lower Respiratory Infection');

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`treatment_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`treatment_type` (`id`, `name`, `description`) VALUES (0, 'procedure', 'Procedure');
INSERT INTO `mydb`.`treatment_type` (`id`, `name`, `description`) VALUES (1, 'operation', 'Operation');
INSERT INTO `mydb`.`treatment_type` (`id`, `name`, `description`) VALUES (2, 'medicine', 'Medicine');

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`doctor_has_patient`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`doctor_has_patient` (`doctor_id`, `patient_id`) VALUES (4, 7);
INSERT INTO `mydb`.`doctor_has_patient` (`doctor_id`, `patient_id`) VALUES (5, 8);
INSERT INTO `mydb`.`doctor_has_patient` (`doctor_id`, `patient_id`) VALUES (5, 9);
INSERT INTO `mydb`.`doctor_has_patient` (`doctor_id`, `patient_id`) VALUES (6, 10);
INSERT INTO `mydb`.`doctor_has_patient` (`doctor_id`, `patient_id`) VALUES (6, 7);
INSERT INTO `mydb`.`doctor_has_patient` (`doctor_id`, `patient_id`) VALUES (6, 8);

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`user_has_disease`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`user_has_disease` (`user_id`, `disease_id`, `doctors_id`, `is_healed`) VALUES (7, 1, 4, 0);
INSERT INTO `mydb`.`user_has_disease` (`user_id`, `disease_id`, `doctors_id`, `is_healed`) VALUES (8, 2, 5, 0);
INSERT INTO `mydb`.`user_has_disease` (`user_id`, `disease_id`, `doctors_id`, `is_healed`) VALUES (9, 3, 5, 0);
INSERT INTO `mydb`.`user_has_disease` (`user_id`, `disease_id`, `doctors_id`, `is_healed`) VALUES (10, 4, 6, 0);
INSERT INTO `mydb`.`user_has_disease` (`user_id`, `disease_id`, `doctors_id`, `is_healed`) VALUES (7, 5, 6, 0);
INSERT INTO `mydb`.`user_has_disease` (`user_id`, `disease_id`, `doctors_id`, `is_healed`) VALUES (8, 6, 6, 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`treatment`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`treatment` (`id`, `name`, `is_done`, `treatment_type_id`, `user_id`, `disease_id`) VALUES (1, 'Aspirin', 0, 2, 7, 1);
INSERT INTO `mydb`.`treatment` (`id`, `name`, `is_done`, `treatment_type_id`, `user_id`, `disease_id`) VALUES (2, 'Spine operation', 0, 1, 9, 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`doctor_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`doctor_type` (`id`, `name`) VALUES (0, 'PEDIATRICIAN');
INSERT INTO `mydb`.`doctor_type` (`id`, `name`) VALUES (1, 'TRAUMATOLOGIST');
INSERT INTO `mydb`.`doctor_type` (`id`, `name`) VALUES (2, 'SURGEON');
INSERT INTO `mydb`.`doctor_type` (`id`, `name`) VALUES (3, 'GYNAECOLOGIST');
INSERT INTO `mydb`.`doctor_type` (`id`, `name`) VALUES (4, 'DENTIST');

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`doctor_has_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`doctor_has_type` (`doctor_id`, `doctor_type_id`) VALUES (4, 1);
INSERT INTO `mydb`.`doctor_has_type` (`doctor_id`, `doctor_type_id`) VALUES (5, 2);
INSERT INTO `mydb`.`doctor_has_type` (`doctor_id`, `doctor_type_id`) VALUES (6, 3);

COMMIT;

