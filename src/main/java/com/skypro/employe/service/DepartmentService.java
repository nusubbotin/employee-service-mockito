package com.skypro.employe.service;

import com.skypro.employe.exception.EmployeeNotFoundException;
import com.skypro.employee.service.EmployeeService;
import com.skypro.employee.model.Employee;

import javax.swing.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DepartmentService {
    private final EmployeeService employeeService;


    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private Stream<Employee> getEmployeesByDepartment(int department){
        return employeeService.getAllEmployees()
                .stream()
                .filter(e -> e.getDepartment() == department);
    }

    public Collection<Employee> getDepartmentEmployees(int department){
        return getEmployeesByDepartment(department)
                .collect(Collectors.toList());
    }

    public int getSumOfSalariesByDepartment(int department){
        return getEmployeesByDepartment(department)
                //.collect(Collectors.summingInt(e -> e.getSalary()));
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public int getMaxOfSalariesByDepartment(int department){
        return getEmployeesByDepartment(department)
                .mapToInt(Employee::getSalary)
                .max()
                .orElseThrow(EmployeeNotFoundException :: new);
    }

    public int getMinOfSalariesByDepartment(int department){
        return getEmployeesByDepartment(department)
                .mapToInt(Employee::getSalary)
                .min()
                .orElseThrow(EmployeeNotFoundException :: new);
    }

    public Map<Integer, List<Employee>> getEmployeesGroupedByDepartmnt(){
        return employeeService.getAllEmployees()
                .stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment()));
    }
}
