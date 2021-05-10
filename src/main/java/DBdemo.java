import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBdemo {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/employeePayrollDB?characterEncoding=latin1";
        String userName = "root";
        String password = "password";
        Connection con;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("cannot find driver in the class path", e);
        }
    }
}
