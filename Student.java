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

    public boolean addCourse(Course course, int maxCredits) {
        int totalCredits = registeredCourses.stream().mapToInt(Course::getCredits).sum();

        if (registeredCourses.contains(course)) {
            System.out.println("Already registered for this course!");
            return false;
        }

        if (totalCredits + course.getCredits() > maxCredits) {
            System.out.println("Credit limit exceeded!");
            return false;
        }

        registeredCourses.add(course);
        return true;
    }

    public boolean removeCourse(Course course){
        if(registeredCourses.contains(course)){
            registeredCourses.remove(course);
            return true;
        } else {
            System.out.println("Course not found in registered courses!");
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

    public String toCSV() {
        return id + "," + name + "," + password;
    }

    public static Student fromCSV(String line) {
        String[] parts = line.split(",");
        if (parts.length == 3) {
            return new Student(parts[0], parts[1], parts[2]);
        }
        return null;
    }


}
