package com.skypro.employe;

import com.skypro.employe.exception.EmployeeNotFoundException;
import com.skypro.employe.service.DepartmentService;
import com.skypro.employe.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.skypro.employe.service.EmployeeService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

     final List<Employee> employees = List.of(
            new Employee("EmpA", "EmpA", 1, 11_000),
                        new Employee("EmpB", "EmpB", 1, 12_000),
                        new Employee("EmpC", "EmpC", 1, 13_000),
                        new Employee("EmpD", "EmpD", 2, 15_000),
                        new Employee("EmpE", "EmpE", 2, 25_000)
        );

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setUp(){
        when(employeeService.getAllEmployees())
                .thenReturn(employees);
    }

    @Test
    void getDepartmentEmployees(){
        Collection<Employee> employeeList = this.departmentService.getDepartmentEmployees(1);
        Assertions.assertThat(employeeList).hasSize(3)
                .contains(employees.get(0), employees.get(1), employees.get(2));
    }

    @Test
    void getSumOfSalariesByDepartment(){
        int sum = departmentService.getSumOfSalariesByDepartment(1);
        Assertions.assertThat(sum).isEqualTo(36_000);
    }

    @Test
    void getMaxOfSalariesByDepartment(){
        int max = departmentService.getMaxOfSalariesByDepartment(2);
        Assertions.assertThat(max).isEqualTo(25_000);
    }

    @Test
    void getMinOfSalariesByDepartment(){
        int min = departmentService.getMinOfSalariesByDepartment(1);
        Assertions.assertThat(min).isEqualTo(11_000);
    }

    @Test
    void getEmployeesGroupedByDepartmnt(){
        Map<Integer, List<Employee>> groupedEmployees = departmentService.getEmployeesGroupedByDepartmnt();
        Assertions.assertThat(groupedEmployees)
                .hasSize(2)
                .containsKey(1)
                .containsKey(2)
                .containsEntry(1, List.of(employees.get(0), employees.get(1), employees.get(2)))
                .containsEntry(2, List.of(employees.get(3), employees.get(4)));

    }

    @Test
    void whenNoEmployeesThenGroupReturnEmptyMap(){
        when(employeeService.getAllEmployees()).thenReturn(List.of());

        Map<Integer, List<Employee>> groupedEmpls = departmentService.getEmployeesGroupedByDepartmnt();

        Assertions.assertThat(groupedEmpls).isEmpty();
    }

    @Test
    void whenEmptyEmplThenMaxOfSalariesByDepartmentException(){
        when(employeeService.getAllEmployees()).thenReturn(List.of());

        Assertions.assertThatThrownBy(() -> departmentService.getMaxOfSalariesByDepartment(0))
                .isInstanceOf(EmployeeNotFoundException.class);
    }
}
