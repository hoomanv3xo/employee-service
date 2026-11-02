package com.example.restservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeManagerTest {

    private EmployeeManager manager;

    @BeforeEach
    void setUp() {
        manager = new EmployeeManager();
    }

    @Test
    void getEmployees_startsWithFourHardCodedEmployees() {
        Employees result = manager.getEmployees();

        assertNotNull(result);
        assertEquals(4, result.getEmployees().size());
        assertEquals("Ada", result.getEmployees().get(0).getFirst_name());
        assertEquals("Hamilton", result.getEmployees().get(3).getLast_name());
    }

    @Test
    void addEmployee_increasesListSize() {
        manager.addEmployee(new Employee("5", "Jane", "Doe", "jane@example.com", "Analyst"));

        assertEquals(5, manager.getEmployees().getEmployees().size());
    }

    @Test
    void addEmployee_newEmployeeIsRetrievableAtEnd() {
        Employee e = new Employee("5", "Jane", "Doe", "jane@example.com", "Analyst");

        manager.addEmployee(e);

        var list = manager.getEmployees().getEmployees();
        assertTrue(list.contains(e));
        assertSame(e, list.get(list.size() - 1));
    }

    @Test
    void addEmployee_multipleAdds_allRetained() {
        manager.addEmployee(new Employee("5", "A", "One", "a@example.com", "T1"));
        manager.addEmployee(new Employee("6", "B", "Two", "b@example.com", "T2"));

        assertEquals(6, manager.getEmployees().getEmployees().size());
    }
}