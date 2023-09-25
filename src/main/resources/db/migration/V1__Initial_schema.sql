-- src/main/resources/db/migration/V1__Initial_schema.sql

CREATE TABLE `users` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `first_name` VARCHAR(255),
  `last_name` VARCHAR(255),
  `email` VARCHAR(255) UNIQUE NOT NULL,
  `password_hash` VARCHAR(255),
  `role` ENUM('ADMIN', 'HR_STAFF', 'EMPLOYEE') DEFAULT 'EMPLOYEE',
  `dob` DATE,
  `start_date` DATE,
  `phone_number` VARCHAR(15),
  `address` TEXT,
  `department_id` INT,
  `position` VARCHAR(255),
  `status` ENUM('ACTIVE', 'INACTIVE', 'TERMINATED', 'ON_LEAVE') DEFAULT 'ACTIVE',
  `created_at` DATETIME DEFAULT current_timestamp,
  `updated_at` DATETIME DEFAULT current_timestamp ON UPDATE current_timestamp
);

CREATE TABLE `departments` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) UNIQUE NOT NULL,
  `description` TEXT,
  `head_id` INT,
  `created_at` DATETIME DEFAULT current_timestamp,
  `updated_at` DATETIME DEFAULT current_timestamp ON UPDATE current_timestamp
);

ALTER TABLE `departments` ADD FOREIGN KEY (`head_id`) REFERENCES `users` (`id`);
