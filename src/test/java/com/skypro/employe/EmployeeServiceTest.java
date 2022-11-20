package com.skypro.employe;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    public void setUp(){
        this.employeeService = new EmployeeService();
        Stream.of(
                new EmployeeRequest("EmpA", "EmpA", 1, 11_000),
                new EmployeeRequest("EmpB", "EmpB", 1, 12_000),
                new EmployeeRequest("EmpC", "EmpC", 1, 13_000),
                new EmployeeRequest("EmpD", "EmpD", 2, 15_000),
                new EmployeeRequest("EmpE", "EmpE", 2, 25_000)
        ).forEach(employeeService::createEmployee);
    }

    @Test
    public void createEmployee(){
        EmployeeRequest request = new EmployeeRequest("User", "User", 3, 100_000);
        Employee result = employeeService.createEmployee(request);

        assertEquals(request.getFirstName(), result.getFirstName());
        assertEquals(request.getLastName(), result.getLastName());
        assertEquals(request.getSalary(), result.getSalary());
        assertEquals(request.getDepartment(), result.getDepartment());

        Assertions.assertThat(employeeService.getAllEmployees())
                .contains(result);
    }

    @Test
    public void listEmployees(){
        Collection<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).hasSize(5);
        Assertions.assertThat(employees)
                .first()
                .extracting(Employee::getFirstName)
                .isEqualTo("EmpA");
    }

    @Test
    public void getSummSalary(){
        int sum = employeeService.getSummSalary();
        Assertions.assertThat(sum)
                .isEqualTo(76_000);
    }

    @Test
    public void getMinSalary(){
        int minSalary = employeeService.getMinSalary();
        Assertions.assertThat(minSalary)
                .isEqualTo(11_000);
    }

    @Test
    public void getMaxSalary(){
        int maxSalary = employeeService.getMaxSalary();
        Assertions.assertThat(maxSalary)
                .isEqualTo(12_000);
    }

    @Test
    public void getHighSalary(){
        double averageSalary = employeeService.getAverageSalary();
        Assertions.assertThat(averageSalary)
                .isEqualTo(15_200);
    }

    @Test
    public void getHighSalaryEmployees(){
        Collection<Employee> HighSalaryEmployees = employeeService.getHighSalaryEmployees();
        Assertions.assertThat(HighSalaryEmployees).hasSize(1);
        Assertions.assertThat(HighSalaryEmployees)
                .first()
                .extracting(Employee::getFirstName)
                .isEqualTo("EmpE");
    }

}
