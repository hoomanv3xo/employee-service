package com.example.restservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class Employees {

    private List<Employee> employees = new ArrayList<>();

    @JsonProperty("Employees")
    public List<Employee> getEmployees() { return employees; }

    public void setEmployees(List<Employee> employees) { this.employees = employees; }
}
