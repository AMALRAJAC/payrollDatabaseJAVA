import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class employeePayrollDBService{
    private PreparedStatement employeePayrollDataStatement;
    private static  employeePayrollDBService employeePayrollDBService;
    employeePayrollDBService(){

    }
    public  static employeePayrollDBService getInstance(){
        if (employeePayrollDBService==null){
            employeePayrollDBService=new employeePayrollDBService();
        }
        return employeePayrollDBService;
    }
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/employeePayrollDB?characterEncoding=latin1";
        String userName = "root";
        String password = "password";
        Connection con;
        System.out.println("Connecting to database:"+jdbcURL);
        con= DriverManager.getConnection(jdbcURL,userName,password);
        System.out.println("Connection is successfull!!!!!"+con);
        return con;
    }
    public List<EmployeePayrollData>readData() {
        String sql="SELECT * FROM payrollDB;";
        return this.getEmployeePayrollDataUsingDB(sql);
    }

    private List<EmployeePayrollData> getEmployeePayrollDataUsingDB(String sql) {
        List<EmployeePayrollData>employeePayrollList=new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);
            employeePayrollList=this.getEmployeePayrollData(resultSet);
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println(employeePayrollList);
        return employeePayrollList;
    }

    public List<EmployeePayrollData> getEmployeePayrollData(String name) {
        List<EmployeePayrollData>employeePayrollList=null;
        if (this.employeePayrollDataStatement == null) {
            this.prepareStatementForEmployeeData();
            try{
                employeePayrollDataStatement.setString(1,name);
                ResultSet resultSet=employeePayrollDataStatement.executeQuery();
                employeePayrollList=this.getEmployeePayrollData(resultSet);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
        return employeePayrollList;
    }

    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
        List<EmployeePayrollData>employeePayrollList=new ArrayList<>();
        try{
            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                double salary=resultSet.getDouble("salary");
                LocalDate startDate=resultSet.getDate("Start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id,name,salary,startDate));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    private void prepareStatementForEmployeeData() {
        try{
            Connection connection=this.getConnection();
            String sql ="SELECT * FROM payrollDB where name=?";
            employeePayrollDataStatement=connection.prepareStatement(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public int updateEmployeeData(String name, double salary) {
        return this.updateEmployeeDataUsingStatement(name,salary);
    }
    private int updateEmployeeDataUsingStatement(String name, double salary) {
        String sql=String.format("update payrollDB set salary=%.2f where name='%s';",salary,name);
        try(Connection connection=this.getConnection()){
            Statement statement=connection.createStatement();
            return statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

}
