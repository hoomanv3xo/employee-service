package com.example.restservice;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeManager {

    private final Employees employees = new Employees();

    public EmployeeManager() {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("1", "Ada", "Lovelace", "ada.lovelace@example.com", "Software Engineer"));
        list.add(new Employee("2", "Grace", "Hopper", "grace.hopper@example.com", "Systems Architect"));
        list.add(new Employee("3", "Alan", "Turing", "alan.turing@example.com", "Data Scientist"));
        list.add(new Employee("4", "Margaret", "Hamilton", "margaret.hamilton@example.com", "Engineering Manager"));
        employees.setEmployees(list);
    }

    public Employees getEmployees() { return employees; }
}
