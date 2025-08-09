import java.util.Objects;

public class Course {
    private String courseCode;
    private String courseName;
    private int credits;
    private double fee;

    public Course(String courseCode, String courseName, int credits, double fee) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.fee = fee;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public double getFee() {
        return fee;
    }

    @Override
    public String toString() {
        return courseCode + " - " + courseName + " (" + credits + " credits, ₹" + fee + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(courseCode, course.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }
}
