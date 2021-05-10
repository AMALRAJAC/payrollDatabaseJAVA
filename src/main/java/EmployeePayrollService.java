import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeePayrollService {

    public static String PAYROLL_FILE_NAME="C:\\Users\\CRACKERJACK\\IdeaProjects\\JDBC_Demo\\src\\main\\java\\payroll-file.txt";

    public enum IOService{
        CONSOLE_IO,FILE_IO,DB_IO,REST_IO
    }
    private List<EmployeePayrollData> employeePayrollList;
    private employeePayrollDBService employeePayrollDBService;
    public EmployeePayrollService(){
        employeePayrollDBService=employeePayrollDBService.getInstance();
    }
    public  EmployeePayrollService(List<EmployeePayrollData>employeePayrollList){
        this();
        this.employeePayrollList=employeePayrollList;

    }
    public static void main(String[] args){
        ArrayList<EmployeePayrollData>employeePayrollList=new ArrayList<>();
        EmployeePayrollService employeePayrollService=new EmployeePayrollService(employeePayrollList);
        Scanner consoleInputReader=new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);

    }
    public void readEmployeePayrollData(Scanner consoleInputReader){
        System.out.println("Enter employee id");
        int id=consoleInputReader.nextInt();
        System.out.println("Employee name");
        String name=consoleInputReader.nextLine();
        System.out.println("Enter employee salary");
        double salary=consoleInputReader.nextDouble();
        LocalDate startDate = null;
        employeePayrollList.add(new EmployeePayrollData(id,name,salary, startDate));

    }
    public List<EmployeePayrollData>readEmployeePayrollData(IOService ioService) {
        if(ioService.equals(IOService.DB_IO)){
            this.employeePayrollList= new employeePayrollDBService().readData();
        }
        return  this.employeePayrollList;

    }

    private EmployeePayrollData getEmployeePayrollData(String name){
        EmployeePayrollData employeePayrollData;
        employeePayrollData =this.employeePayrollList.stream()
                .filter(employeePayrollDataItem ->employeePayrollDataItem.name.equals(name) ).findFirst().orElse(null);
        return employeePayrollData;
    }
    public void writeEmployeePayrollData(IOService ioService){
        if(ioService.equals(IOService.CONSOLE_IO)){
            System.out.println("\nWriting employee payroll Roaster to console\n"+employeePayrollList);
        }
        else if(ioService.equals(IOService.FILE_IO)){
            new EmployeePayrollIOService().writeData(employeePayrollList);

        }
    }
//    public long readEmployeePayrollData(IOService ioService){
//        if(ioService.equals(IOService.FILE_IO)){
//            this.employeePayrollList=new EmployeePayrollIOService().readData();
//
//        }
//        return employeePayrollList.size();
//    }
    public void printData(IOService ioService){
        if(ioService.equals(IOService.FILE_IO)){
            new  EmployeePayrollIOService().printData();
        }
    }
    public static long countEntries(IOService ioService){
        long entries=0;
        try{
            entries= Files.lines(new File(PAYROLL_FILE_NAME).toPath()).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }
}
