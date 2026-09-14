-- ============================================
-- Student Leave Management System - MySQL Schema
-- Note: Spring Data JPA (hibernate.ddl-auto=update)
-- will create this table automatically when the
-- backend starts. This file is just for reference
-- or if you want to create it manually.
-- ============================================

CREATE DATABASE IF NOT EXISTS student_leave_db;

USE student_leave_db;

CREATE TABLE IF NOT EXISTS leave_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(255) NOT NULL,
    roll_number VARCHAR(255) NOT NULL,
    department VARCHAR(255),
    from_date VARCHAR(50) NOT NULL,
    to_date VARCHAR(50) NOT NULL,
    reason VARCHAR(1000),
    status VARCHAR(50) DEFAULT 'PENDING',
    applied_date VARCHAR(50)
);
