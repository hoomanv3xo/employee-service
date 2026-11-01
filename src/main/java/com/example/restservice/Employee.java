package com.example.restservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

    private String employee_id;
    private String first_name;
    private String last_name;
    private String email;
    private String title;

    public Employee(String employee_id, String first_name, String last_name, String email, String title) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.title = title;
    }

    @JsonProperty("employee_id")
    public String getEmployee_id() { return employee_id; }

    @JsonProperty("first_name")
    public String getFirst_name() { return first_name; }

    @JsonProperty("last_name")
    public String getLast_name() { return last_name; }

    @JsonProperty("email")
    public String getEmail() { return email; }

    @JsonProperty("title")
    public String getTitle() { return title; }
}
