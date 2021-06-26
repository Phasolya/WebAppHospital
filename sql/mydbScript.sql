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
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `full_name` VARCHAR(45) NOT NULL,
  `birth_date` VARCHAR(10) NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_roles_idx` (`role_id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
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
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_disease_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_disease_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `mydb`.`treatment_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`treatment_type` ;

CREATE TABLE IF NOT EXISTS `mydb`.`treatment_type` (
  `id` INT NOT NULL,
  `name` VARCHAR(15) NOT NULL,
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
-- Table `mydb`.`treatment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`treatment` ;

CREATE TABLE IF NOT EXISTS `mydb`.`treatment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `disease_id` INT NOT NULL,
  `treatment_type_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_treatment_disease1_idx` (`disease_id` ASC) VISIBLE,
  INDEX `fk_treatment_treatment_type2_idx` (`treatment_type_id` ASC) VISIBLE,
  CONSTRAINT `fk_treatment_disease1`
    FOREIGN KEY (`disease_id`)
    REFERENCES `mydb`.`disease` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_treatment_treatment_type2`
    FOREIGN KEY (`treatment_type_id`)
    REFERENCES `mydb`.`treatment_type` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `mydb`.`doctor_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`doctor_type` ;

CREATE TABLE IF NOT EXISTS `mydb`.`doctor_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
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
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_has_doctor_type_doctor_type1`
    FOREIGN KEY (`doctor_type_id`)
    REFERENCES `mydb`.`doctor_type` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


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
INSERT INTO `mydb`.`user` (`id`, `email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (1, 'mvovnianko@gmail.com', 'Admin1', 'Vovnianko Maxym Nikolaevich', '1991-06-09', 0);
INSERT INTO `mydb`.`user` (`id`, `email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (2, 'tzub@gmail.com', 'Nurse2', 'Zub Tamara Borisovna', '1987-04-12', 2);
INSERT INTO `mydb`.`user` (`id`, `email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (3, 'msaenko@gmail.com', 'Nurse3', 'Sayenko Marina Olegovna', '1985-05-04', 2);
INSERT INTO `mydb`.`user` (`id`, `email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (4, 'dratuch@gmail.com', 'Doctor4', 'Ratuch Dima Petrovich', '1941-05-12', 1);
INSERT INTO `mydb`.`user` (`id`, `email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (5, 'bumin@gmail.com', 'Docotr5', 'Umin Bogdan Vasilovych', '2000-03-21', 1);
INSERT INTO `mydb`.`user` (`id`, `email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (6, 'lkusnir@gmail.com', 'Doctor6', 'Kusnir Ludmila Ivanovna', '1993-07-09', 1);
INSERT INTO `mydb`.`user` (`id`, `email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (7, 'igrona@gmail.ua', 'Patient7', 'Grona Inna Polyakovovna', '1986-06-13', 3);
INSERT INTO `mydb`.`user` (`id`, `email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (8, 'ibedrovich@rambler.ru', 'Patient8', 'Golik Ivan Bedrovich', '1999-09-11', 3);
INSERT INTO `mydb`.`user` (`id`, `email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (9, 'bbandera@gmail.com', 'Patient9', 'Bandera Bogdan Gerasimovich', '1990-04-22', 3);
INSERT INTO `mydb`.`user` (`id`, `email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (10, 'epaniakin@gmail.com', 'Patient10', 'Paniakin Eduard Eduardovich', '1992-06-09', 3);
INSERT INTO `mydb`.`user` (`id`, `email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (11, 'lpopko@rambler.ru', 'Patient11', 'Popko Lena Nikolaevna ', '1990-05-20', 3);
INSERT INTO `mydb`.`user` (`id`, `email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (12, 'mzelenski@gmail.ru', 'Patient12', 'Zelenski Misha Antonovich', '1986-01-01', 3);
INSERT INTO `mydb`.`user` (`id`, `email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (13, 'susovich@ukmail.ua', 'Patient13', 'Usovich Stepan Timurovich', '1996-05-13', 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`disease`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`disease` (`id`, `name`, `user_id`) VALUES (1, 'Trachea cancer', 7);
INSERT INTO `mydb`.`disease` (`id`, `name`, `user_id`) VALUES (2, 'Diabetes mellitus', 8);
INSERT INTO `mydb`.`disease` (`id`, `name`, `user_id`) VALUES (3, 'Alzheimer\'s disease', 8);
INSERT INTO `mydb`.`disease` (`id`, `name`, `user_id`) VALUES (4, 'Diarrhea', 9);
INSERT INTO `mydb`.`disease` (`id`, `name`, `user_id`) VALUES (5, 'Tuberculosis', 9);
INSERT INTO `mydb`.`disease` (`id`, `name`, `user_id`) VALUES (6, 'Cirrhosis', 9);
INSERT INTO `mydb`.`disease` (`id`, `name`, `user_id`) VALUES (7, 'Stroke', 10);
INSERT INTO `mydb`.`disease` (`id`, `name`, `user_id`) VALUES (8, 'Malaria', 10);
INSERT INTO `mydb`.`disease` (`id`, `name`, `user_id`) VALUES (9, 'Lower Respiratory Infection', 10);

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`treatment_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`treatment_type` (`id`, `name`) VALUES (0, 'procedure');
INSERT INTO `mydb`.`treatment_type` (`id`, `name`) VALUES (1, 'operation');
INSERT INTO `mydb`.`treatment_type` (`id`, `name`) VALUES (2, 'medicine');

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`doctor_has_patient`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`doctor_has_patient` (`doctor_id`, `patient_id`) VALUES (3, 6);
INSERT INTO `mydb`.`doctor_has_patient` (`doctor_id`, `patient_id`) VALUES (4, 7);
INSERT INTO `mydb`.`doctor_has_patient` (`doctor_id`, `patient_id`) VALUES (4, 8);
INSERT INTO `mydb`.`doctor_has_patient` (`doctor_id`, `patient_id`) VALUES (5, 9);
INSERT INTO `mydb`.`doctor_has_patient` (`doctor_id`, `patient_id`) VALUES (5, 6);
INSERT INTO `mydb`.`doctor_has_patient` (`doctor_id`, `patient_id`) VALUES (5, 7);

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`treatment`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`treatment` (`id`, `name`, `disease_id`, `treatment_type_id`) VALUES (1, 'Aspirin', 1, 2);
INSERT INTO `mydb`.`treatment` (`id`, `name`, `disease_id`, `treatment_type_id`) VALUES (2, 'spine operation', 1, 1);
INSERT INTO `mydb`.`treatment` (`id`, `name`, `disease_id`, `treatment_type_id`) VALUES (3, 'seconal', 1, 2);
INSERT INTO `mydb`.`treatment` (`id`, `name`, `disease_id`, `treatment_type_id`) VALUES (4, 'nembrutal', 3, 2);
INSERT INTO `mydb`.`treatment` (`id`, `name`, `disease_id`, `treatment_type_id`) VALUES (5, 'valium', 3, 2);
INSERT INTO `mydb`.`treatment` (`id`, `name`, `disease_id`, `treatment_type_id`) VALUES (6, 'xanax', 5, 2);
INSERT INTO `mydb`.`treatment` (`id`, `name`, `disease_id`, `treatment_type_id`) VALUES (7, 'sonata', 5, 2);
INSERT INTO `mydb`.`treatment` (`id`, `name`, `disease_id`, `treatment_type_id`) VALUES (8, 'foot massage', 5, 0);
INSERT INTO `mydb`.`treatment` (`id`, `name`, `disease_id`, `treatment_type_id`) VALUES (9, 'mouthwash', 7, 0);
INSERT INTO `mydb`.`treatment` (`id`, `name`, `disease_id`, `treatment_type_id`) VALUES (10, 'removal of kidney stones', 7, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`doctor_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`doctor_type` (`id`, `name`) VALUES (1, 'pediatrician');
INSERT INTO `mydb`.`doctor_type` (`id`, `name`) VALUES (2, 'traumatologist');
INSERT INTO `mydb`.`doctor_type` (`id`, `name`) VALUES (3, 'surgeon');
INSERT INTO `mydb`.`doctor_type` (`id`, `name`) VALUES (4, 'gynaecologist');
INSERT INTO `mydb`.`doctor_type` (`id`, `name`) VALUES (5, 'dentist');

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`doctor_has_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`doctor_has_type` (`doctor_id`, `doctor_type_id`) VALUES (4, 1);
INSERT INTO `mydb`.`doctor_has_type` (`doctor_id`, `doctor_type_id`) VALUES (5, 2);
INSERT INTO `mydb`.`doctor_has_type` (`doctor_id`, `doctor_type_id`) VALUES (6, 4);

COMMIT;

