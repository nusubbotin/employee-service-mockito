package com.skypro.employee.controller;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.OptionalInt;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService =  employeeService;
    }

    @GetMapping("/employee")
    public Collection<Employee> getAllEmployees(){
        return this.employeeService.getAllEmployees();
    }

    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest){
        return employeeService.createEmployee(employeeRequest);
    }

    @GetMapping("/employee/salary/sum")
    public int getSummSalary(){
        return employeeService.getSummSalary();
    }

    @GetMapping("/employee/salary/min")
    public int getMinSalary(){
        return employeeService.getMinSalary();
    }

    @GetMapping("/employee/salary/max")
    public int getMaxSalary(){
        return employeeService.getMaxSalary();
    }

    @GetMapping("/employee/high-salary")
    public Collection<Employee> getHighSalaryEmployees(){
        return employeeService.getHighSalaryEmployees();
    }
}
