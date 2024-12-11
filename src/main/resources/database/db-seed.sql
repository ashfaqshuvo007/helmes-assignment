-- Create database
CREATE DATABASE IF NOT EXISTS helmes;

-- Create table 'users'
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(8) NOT NULL DEFAULT 'USER'
);

-- Create table 'users'
CREATE TABLE sectors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sector_name VARCHAR(64) NOT NULL,
    parent_id INT NULL
);

-- Create pivot table 'user_sectors'
CREATE TABLE user_sectors (
    user_id BIGINT NOT NULL,
    sector_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (sector_id) REFERENCES sectors(id)
);

-- Create pivot table 'user_sectors'
CREATE TABLE user_terms (
    user_id BIGINT NOT NULL,
    is_terms_checked VARCHAR(8) NOT NULL DEFAULT 'DISAGREE',
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- POPULATE THE DATABASE

-- Users table
INSERT INTO users(id, username, password, role) VALUES
(1, 'John', '$2a$12$t5zUFCfw/duPJVaaCxbV8.m1nPRX/kA.2QEvJZm.2uu5n8kKFeHeW', 'ADMIN'),
(2, 'Jane', '$2a$12$t5zUFCfw/duPJVaaCxbV8.m1nPRX/kA.2QEvJZm.2uu5n8kKFeHeW', 'USER'),
(3, 'Mika', '$2a$12$t5zUFCfw/duPJVaaCxbV8.m1nPRX/kA.2QEvJZm.2uu5n8kKFeHeW', 'USER');
-- Sectors Table (Starting with base sectors)
INSERT INTO sectors(id, sector_name, parent_id) VALUES
(1, 'Manufacturing', NULL),
(2, 'Other', NULL),
(3, 'Service', NULL);

INSERT INTO user_sectors(user_id, sector_id) VALUES
(2,2),
(3, 1);

INSERT INTO user_terms(user_id, is_terms_checked) VALUES
(2,'DISAGREE'),
(3, 'DISAGREE');
