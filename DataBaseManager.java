import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.SQLException;


public class DataBaseManager {

    private static final String url = "jdbc:mysql://localhost:3306/course_registration_system";

    private static final String user="root";

    private static final String password="Yadagiri@6309";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }
    
}
