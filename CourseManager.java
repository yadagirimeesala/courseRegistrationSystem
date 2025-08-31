import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private List<Course> courses = new ArrayList<>();

    public CourseManager() {
        loadCoursesFromDB();
    }

    private void loadCoursesFromDB(){
        String sql="SELECT * FROM courses";

        try(Connection conn=DataBaseManager.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql);
            ResultSet rs=pstmt.executeQuery();){
                while(rs.next()){
                    String code=rs.getString("course_code");
                    String name=rs.getString("course_name");
                    int credits=rs.getInt("credits");  
                    double fee=rs.getDouble("fee");
                    courses.add(new Course(code, name, credits, fee));
                }
                System.out.println("Courses loaded from database successfully.");
        }catch(SQLException e){
            System.out.println("Error loading courses from database: "+e.getMessage());
        }


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
        return null;
    }

    public List<Course> getAllCourses() {
        return courses;
    }
}
