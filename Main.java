import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager studentManager = new StudentManager();
        CourseManager courseManager = new CourseManager();

        studentManager.setCourseManager(courseManager);

        final int MAX_CREDITS = 10;
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Course Registration System ===");
            System.out.println("1. Register as Student");
            System.out.println("2. Login as Student");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String regId = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String regName = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String regPass = scanner.nextLine();

                    studentManager.registerStudent(regId, regName, regPass);
                    break;

                case 2:
                    System.out.print("Enter Student ID: ");
                    String loginId = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String loginPass = scanner.nextLine();

                    Student student = studentManager.login(loginId, loginPass);
                    if (student != null) {
                        boolean loggedIn = true;
                        while (loggedIn) {
                            System.out.println("\nWelcome, " + student.getName());
                            System.out.println("1. View Available Courses");
                            System.out.println("2. Register for Course");
                            System.out.println("3. View Registered Courses");
                            System.out.println("4. Remove Registered Course");
                            System.out.println("5. Generate Fee Receipt");
                            System.out.println("6. Logout");
                            System.out.print("Choose an option: ");

                            int subChoice;
                            try {
                                subChoice = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                                continue;
                            }

                            switch (subChoice) {
                                case 1:
                                    courseManager.displayCourses();
                                    break;
                                case 2:
                                    System.out.print("Enter Course Code to Register: ");
                                    String code = scanner.nextLine();
                                    Course selected = courseManager.getCourseByCode(code);
                                    if (selected != null) {
                                        if (student.addCourse(selected, MAX_CREDITS)) {
                                            studentManager.saveRegistrations();
                                        }
                                    } else {
                                        System.out.println("Invalid course code.");
                                    }
                                    break;
                                case 3:
                                    System.out.println("\n--- Registered Courses ---");
                                    for (Course c : student.getRegisteredCourses()) {
                                        System.out.println(c);
                                    }
                                    break;
                                case 4:
                                    System.out.print("Enter Course Code to Remove: ");
                                    String removeCode = scanner.nextLine();
                                    Course toRemove = courseManager.getCourseByCode(removeCode);
                                    if (toRemove != null) {
                                        if (student.removeCourse(toRemove)) {
                                            System.out.println("Course removed successfully.");
                                            studentManager.saveRegistrations();
                                        }
                                    } else {
                                        System.out.println("Invalid course code.");
                                    }
                                    break;                                    
                                case 5:
                                    student.printReceipt();
                                    break;
                                case 6:
                                    loggedIn = false;
                                    studentManager.saveRegistrations();  // save before logout
                                    System.out.println("Logged out.");
                                    break;
                                default:
                                    System.out.println("Invalid option.");
                            }
                        }
                    }
                    break;

                case 3:
                    exit = true;
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }
}
