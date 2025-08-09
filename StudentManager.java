import java.io.*;
import java.util.*;

public class StudentManager {
    private HashMap<String, Student> students = new HashMap<>();
    private final String STUDENT_FILE = "students.txt";
    private final String REGISTRATION_FILE = "registration.txt";

    private CourseManager courseManager;

    public StudentManager() {
        loadStudents();
    }

    public void setCourseManager(CourseManager courseManager) {
        this.courseManager = courseManager;
        loadRegistrations(); 
    }

    public void registerStudent(String id, String name, String password) {
        if (students.containsKey(id)) {
            System.out.println("Student with this ID already exists!");
            return;
        }
        Student student = new Student(id, name, password);
        students.put(id, student);
        saveStudents();
        System.out.println("Registration successful!");
    }

    public Student login(String id, String password) {
        Student student = students.get(id);
        if (student == null) {
            System.out.println("No such student found.");
            return null;
        }
        if (!student.getPassword().equals(password)) {
            System.out.println("Incorrect password.");
            return null;
        }
        return student;
    }

    public void saveStudents() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENT_FILE))) {
            for (Student s : students.values()) {
                writer.println(s.toCSV()); // Save each student in CSV format
            }
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    public void loadStudents() {
        File file = new File(STUDENT_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Student s = Student.fromCSV(line);
                if (s != null) {
                    students.put(s.getId(), s);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }

    public void saveRegistrations() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(REGISTRATION_FILE))) {
            for (Student s : students.values()) {
                for (Course c : s.getRegisteredCourses()) {
                    writer.println(s.getId() + "," + c.getCourseCode());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving registrations: " + e.getMessage());
        }
    }

    public void loadRegistrations() {
        if (courseManager == null) return; // can't load without course info

        File file = new File(REGISTRATION_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String studentId = parts[0];
                    String courseCode = parts[1];
                    Student student = students.get(studentId);
                    Course course = courseManager.getCourseByCode(courseCode);
                    if (student != null && course != null) {
                        // Use method to properly handle duplicates, limits
                        student.addCourse(course, Integer.MAX_VALUE); // ignore limit during load
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading registrations: " + e.getMessage());
        }
    }
}
