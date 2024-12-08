-- Create database
CREATE DATABASE IF NOT EXISTS helmesassignment;

-- Create table 'users'
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Create table 'users'
CREATE TABLE sectors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sector_name VARCHAR(64) NOT NULL,
    parent_id INT NULL
);

-- Create pivot table 'user_sectors'
CREATE TABLE user_sectors (
    user_id INT NOT NULL,
    sector_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (sector_id) REFERENCES sectors(id)
);


-- POPULATE THE DATABASE

-- Users table
INSERT INTO users(id, name, password) VALUES
(1, 'John', '$2a$12$t5zUFCfw/duPJVaaCxbV8.m1nPRX/kA.2QEvJZm.2uu5n8kKFeHeW'),
(2, 'Jane', '$2a$12$t5zUFCfw/duPJVaaCxbV8.m1nPRX/kA.2QEvJZm.2uu5n8kKFeHeW');

-- Sectors Table (Starting with base sectors)
INSERT INTO sectors(id, sector_name, parent_id) VALUES
(1, 'Manufacturing', NULL),
(2, 'Other', NULL),
(3, 'Service', NULL);

INSERT INTO user_sectors(user_id, sector_id) VALUES
(1,3),
(2, 1);
