package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.apache.el.lang.FunctionMapperImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();

    public Collection<Employee> getAllEmployees(){
        return this.employees.values();
    }

    public Employee createEmployee(EmployeeRequest employeeRequest){
        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException("Не заполнены обязательные поля");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(), employeeRequest.getLastName(), employeeRequest.getDepartment(), employeeRequest.getSalary());

        employees.put(employee.getId(), employee);
        return employee;
    }

    public int getSummSalary(){
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public OptionalInt getMinSalary(){
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .min();
    }

    public OptionalInt getMaxSalary(){
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .max();
    }

    public Collection<Employee> getHighSalaryEmployees(){
        OptionalDouble averageSalary = employees.values().stream()
                .mapToInt(Employee::getSalary)
                .average();

            return employees.values().stream()
                    .filter(e-> e.getSalary() >= averageSalary.getAsDouble())
                    .collect(Collectors.toList());

    }
}
