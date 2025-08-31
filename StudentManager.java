import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class StudentManager {
    private HashMap<String, Student> students = new HashMap<>();
    // private final String STUDENT_FILE = "students.txt";
    // private final String REGISTRATION_FILE = "registration.txt";

    private CourseManager courseManager;

    public StudentManager() {
        // loadStudents();
    }

    public void setCourseManager(CourseManager courseManager) {
        this.courseManager = courseManager;
        loadStudentFromDB();
    }

    public void registerStudent(String id, String name, String password) {
        if (students.containsKey(id)) {
            System.out.println("Student with this ID already exists!");
            return;
        }

        String passwordHash = PasswordUtils.hashPassword(password);
        String sql = "INSERT INTO students(student_id,name,password) VALUES(?,?,?)";
        try (Connection conn = DataBaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, passwordHash);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                Student newStudent = new Student(id, name, passwordHash);
                students.put(id, newStudent);
                System.out.println("Student registered successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error registering student: " + e.getMessage());
        }
    }

    public Student login(String id, String password) {
        Student student = students.get(id);
        if (student == null) {
            System.out.println("No such student found.");
            return null;
        }
        if (PasswordUtils.checkPassword(password, student.getPassword())) {
            return student;
        } else {
            System.out.println("Incorrect password.");
            return null;
        }
    }

    private void loadStudentFromDB() {
        String sql = "SELECT student_id,name,password FROM students";
        try (Connection conn = DataBaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                String id = rs.getString("student_id");
                String name = rs.getString("name");
                String passHash = rs.getString("password");
                Student s = new Student(id, name, passHash);
                students.put(id, s);
            }
            System.out.println("Students loaded from database successfully.");
            loadAllRegistrations();
        } catch (SQLException e) {
            System.out.println("Error loading students from database: " + e.getMessage());
        }
    }


    private void loadAllRegistrations(){
        String sql="SELECT * from student_courses";
        try(Connection conn=DataBaseManager.getConnection();
        PreparedStatement pstmt=conn.prepareStatement(sql);
        ResultSet rs=pstmt.executeQuery())
        {
            while (rs.next()) {
                String studentId=rs.getString("stu_id");
                String courseCode=rs.getString("course_id");

                Student student=students.get(studentId);
                Course course=courseManager.getCourseByCode(courseCode);

                if(student!=null && course!=null){
                    student.addCourse(course); // ignore limit during load
                }
            }
            System.out.println("Registrations loaded from database successfully.");



        }catch(SQLException e){
            System.out.println("Error loading registrations from database: " + e.getMessage());

    }
}
   
}
