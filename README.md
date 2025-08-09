# Course Registration System

A modular, console-based Java application for student course registration, featuring user authentication, course enrollment with credit limits, and fee receipt generation. Data is persisted using both file I/O and (optionally) SQL database integration.

## Features

- Student registration and secure login
- View, enroll in, and remove courses
- Credit limit enforcement per student
- Automated fee receipt generation
- Data persistence via text files and/or SQL database
- Robust error handling and input validation
- Modular, object-oriented design

## Technologies Used

- Java (OOP, Collections, File I/O)
- (Optional) SQL Database for persistent storage

## Getting Started

1. **Clone the repository:**
   ```
   git clone https://github.com/<your-username>/CourseRegistrationSystem.git
   ```
2. **Navigate to the project directory:**
   ```
   cd CourseRegistrationSystem
   ```
3. **Compile the Java files:**
   ```
   javac *.java
   ```
4. **Run the application:**
   ```
   java Main
   ```

## Project Structure

- `Main.java` - Entry point and menu logic
- `Student.java` - Student model and methods
- `Course.java` - Course model
- `CourseManager.java` - Handles available courses
- `StudentManager.java` - Handles student data and registrations
- `students.txt` - Stores registered students
- `registrations.txt` - Stores course registrations

## Sample Data

- `students.txt` contains student records in CSV format:
  ```
  239,Yadagiri,123456
  ```

## License

This project is for educational purposes.