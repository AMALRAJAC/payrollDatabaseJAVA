import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EmployeePayrollServiceTest {
    @Test
    public  void given3EmployeesWhenWrittenToFilesShouldMatchEmployeeEntries() {
        LocalDate startDate = null;
        EmployeePayrollData[] arrayOfEmps = {
                new EmployeePayrollData(1, "jeff bezos", 10000.0 ),
                new EmployeePayrollData(2, "Bill gates", 20000.0),
                new EmployeePayrollData(3, "Mark Zuckerberg", 30000.0)
        };
        EmployeePayrollService employeePayrollService;
        employeePayrollService=new EmployeePayrollService(Arrays.asList(arrayOfEmps));
        employeePayrollService.writeEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);
        employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
        long entries=EmployeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
        Assertions.assertEquals(3,entries);
    }
//    @Test
//    public void givenFileOnReadingFromFileShouldMatchEmployeeCount(){
//        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
//        long entries=employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);
//        Assertions.assertEquals(3,entries);
//    }
    @Test
    public  void givenEmployeePayrollIoDB_whenRetrieved_shouldMatchEmployeeCount(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        List<EmployeePayrollData>employeePayrollData=employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        Assertions.assertEquals(3,employeePayrollData.size());
    }
    @Test
    public  void givenNewSalaryForEmployee_WhenUpdated_ShouldMatch(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        List<EmployeePayrollData>employeePayrollData=employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalary("Mark",500000.00);
        boolean result=employeePayrollService.checkEmployeePayrollInSyncWithDb("Mark");
        Assertions.assertTrue(result);
    }
    @Test
    public void givenDateRange_whenretrievedShouldMatchEmployeeCount(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        LocalDate startDate=LocalDate.of(2018,01,01);
        LocalDate endDate =LocalDate.now();
        List<EmployeePayrollData>employeePayrollData=employeePayrollService.readEmployeePayrollForDateRange(EmployeePayrollService.IOService.DB_IO,startDate,endDate);
        Assertions.assertEquals(3,employeePayrollData.size());
    }
    @Test
    public  void givenPayrollData_whenAverageSalaryRetrievedByGender_ShouldReturnProperValue(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        Map<String,Double> averageSalaryByGender=employeePayrollService.readAverageSalaryByGender(EmployeePayrollService.IOService.DB_IO);
        Assertions.assertTrue(averageSalaryByGender.get("M").equals(200000.00)&& averageSalaryByGender.get("F").equals(500000.00));
    }
}
