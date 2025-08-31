# Course Registration System

A modular, console-based Java application for student registration, authentication, course enrollment with credit limits, and automated fee receipt generation. Data persistence is implemented using MySQL (JDBC). Passwords are hashed with bcrypt.

## Features
- Student registration and secure login (bcrypt)
- View, enroll in, and remove courses with credit-limit enforcement
- Automated fee receipt generation
- Persistent storage using MySQL tables for students, courses, and registrations
- Modular OOP design: Student, Course, StudentManager, CourseManager, DB utility

## Technologies & Dependencies
- Java 8+
- MySQL (server)
- MySQL Connector/J (JDBC) — add jar to `lib\mysql-connector-java-<version>.jar`
- jBCrypt (org.mindrot.jbcrypt) — add jar to `lib\jBCrypt-<version>.jar` or use Maven

## Database Schema (run in MySQL Workbench)
```sql
CREATE DATABASE IF NOT EXISTS course_registration_system;
USE course_registration_system;

CREATE TABLE IF NOT EXISTS students (
  student_id VARCHAR(50) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS courses (
  course_code VARCHAR(50) PRIMARY KEY,
  course_name VARCHAR(200) NOT NULL,
  credits INT NOT NULL,
  fee DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS student_courses (
  stu_id VARCHAR(50),
  course_id VARCHAR(50),
  PRIMARY KEY (stu_id, course_id),
  FOREIGN KEY (stu_id) REFERENCES students(student_id) ON DELETE CASCADE,
  FOREIGN KEY (course_id) REFERENCES courses(course_code) ON DELETE CASCADE
);
```

Sample course rows:
```sql
INSERT INTO courses (course_code, course_name, credits, fee) VALUES
('CS101','Data Structures',3,1500),
('CS102','Algorithms',3,1600),
('CS103','Databases',2,1400);
```

## Setup (Windows)
1. Ensure MySQL server is running and you can connect via Workbench.
2. Create DB and tables (use schema above).
3. Download dependencies and place in project `lib`:
   - MySQL Connector/J: https://dev.mysql.com/downloads/connector/j/
   - jBCrypt: https://www.mindrot.org/projects/jBCrypt/ or via Maven central
4. Update DB credentials in `DataBaseManager.java` if needed:
   - url, user, password
   - Avoid committing real passwords to public repos.

## Compile & Run (from project root)
Assuming jars are `lib\mysql-connector-java.jar` and `lib\jbcrypt.jar`:
```sh
cd /path/to/your/CourseRegistrationSystem
javac -cp ".;lib\mysql-connector-java.jar;lib\jbcrypt.jar" *.java
java  -cp ".;lib\mysql-connector-java.jar;lib\jbcrypt.jar" Main
```

## Usage
- 1 → Register student (stored in `students`)
- 2 → Login → view courses, register/remove courses (updates `student_courses`), print receipt
- 3 → Exit

## Notes & Troubleshooting
- If ClassNotFoundException: ensure connector and jbcrypt jars are on classpath and names match.
- If SQLException: verify DB name, credentials, and that MySQL server is running on localhost:3306.
- For production or public repo: use a config/properties file or environment variables for DB credentials and add `.gitignore` to exclude them.

## Project Structure
- Main.java
- Student.java
- StudentManager.java
- Course.java
- CourseManager.java
- DataBaseManager.java
- PasswordUtils.java
- lib/ (third-party jars)

This project is for educational