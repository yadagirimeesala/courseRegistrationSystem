import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private List<Course> courses = new ArrayList<>();

    public CourseManager() {
        // Hardcoded courses (you can load from file later if you want)
        courses.add(new Course("CS101", "Data Structures", 3, 3000));
        courses.add(new Course("MA102", "Calculus", 4, 4000));
        courses.add(new Course("PH103", "Physics", 3, 3500));
        courses.add(new Course("EE104", "Basic Electrical", 2, 2500));
        courses.add(new Course("HS105", "Communication Skills", 2, 2000));
    }

    public void displayCourses() {
        System.out.println("\n--- Available Courses ---");
        for (Course course : courses) {
            System.out.println(course);
        }
        System.out.println("--------------------------");
    }

    public Course getCourseByCode(String code) {
        for (Course course : courses) {
            if (course.getCourseCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        System.out.println("Course with code " + code + " not found.");
        return null;
    }

    public List<Course> getAllCourses() {
        return courses;
    }
}
