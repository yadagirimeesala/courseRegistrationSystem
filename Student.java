import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student {
    private String id;
    private String name;
    private String password;
    private ArrayList<Course> registeredCourses;

    public Student(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.registeredCourses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Course> getRegisteredCourses() {
        return Collections.unmodifiableList(registeredCourses);
    }

    public void addCourse(Course course){
        if(!registeredCourses.contains(course)){
            registeredCourses.add(course);
        }
    }


                             
    public boolean registerCourse(Course course, int maxCredits) {
        int totalCredits = registeredCourses.stream().mapToInt(Course::getCredits).sum();

        if (registeredCourses.contains(course)) {
            System.out.println("Already registered for this course!");
            return false;
        }

        if (totalCredits + course.getCredits() > maxCredits) {
            System.out.println("Credit limit exceeded!");
            return false;
        }

        String sql = "INSERT INTO student_courses(stu_id,course_id) VALUES(?,?)";

        try (Connection conn = DataBaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.id);
            pstmt.setString(2, course.getCourseCode());
            pstmt.executeUpdate();

            this.registeredCourses.add(course);
            System.out.println(course.getCourseName() + " Course registered successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error registering course: " + e.getMessage());
            return false;

        }
    }



    public boolean removeCourse(Course course) {
        if (!registeredCourses.contains(course)) {
            System.out.println("Not registered for this course!");
            return false;
        }

        String sql = "DELETE FROM student_courses WHERE stu_id=? and course_id=?";
        try (Connection conn = DataBaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.id);
            pstmt.setString(2, course.getCourseCode());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                registeredCourses.remove(course);
                System.out.println(course.getCourseName() + " Course removed successfully.");
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error removing course: " + e.getMessage());
            return false;
        }
    }




    public void printReceipt() {
        System.out.println("\n----- FEE RECEIPT -----");
        System.out.println("Student Name: " + name);
        System.out.println("ID: " + id);

        double totalFee = 0;
        int totalCredits = 0;

        for (Course course : registeredCourses) {
            System.out.println(course);
            totalFee += course.getFee();
            totalCredits += course.getCredits();
        }

        System.out.println("Total Credits: " + totalCredits);
        System.out.println("Total Fee: ₹" + totalFee);
        System.out.println("------------------------\n");
    }

}
