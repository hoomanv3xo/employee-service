package com.example.restservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeManager employeeManager;

    private Employees sampleEmployees() {
        Employees employees = new Employees();
        employees.setEmployees(List.of(
                new Employee("1", "Ada", "Lovelace", "ada@example.com", "Software Engineer"),
                new Employee("2", "Grace", "Hopper", "grace@example.com", "Systems Architect")));
        return employees;
    }

    @Test
    void getEmployees_returnsOkAndEmployeeList() throws Exception {
        when(employeeManager.getEmployees()).thenReturn(sampleEmployees());

        mockMvc.perform(get("/employees").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Employees.length()").value(2))
                .andExpect(jsonPath("$.Employees[0].employee_id").value("1"))
                .andExpect(jsonPath("$.Employees[0].first_name").value("Ada"))
                .andExpect(jsonPath("$.Employees[1].last_name").value("Hopper"))
                .andExpect(jsonPath("$.Employees[1].title").value("Systems Architect"));
    }

    @Test
    void getEmployees_emptyList_returnsEmptyArray() throws Exception {
        when(employeeManager.getEmployees()).thenReturn(new Employees());

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Employees.length()").value(0));
    }

    @Test
    void addEmployee_validJson_returnsCreatedAndEchoesEmployee() throws Exception {
        String body = """
                {"employee_id": "5", "first_name": "Jane", "last_name": "Doe",
                 "email": "jane@example.com", "title": "Analyst"}
                """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employee_id").value("5"))
                .andExpect(jsonPath("$.first_name").value("Jane"))
                .andExpect(jsonPath("$.title").value("Analyst"));
    }

    @Test
    void addEmployee_passesParsedEmployeeToManager() throws Exception {
        String body = """
                {"employee_id": "5", "first_name": "Jane", "last_name": "Doe",
                 "email": "jane@example.com", "title": "Analyst"}
                """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeManager, times(1)).addEmployee(captor.capture());
        assertEquals("5", captor.getValue().getEmployee_id());
        assertEquals("Jane", captor.getValue().getFirst_name());
        assertEquals("Doe", captor.getValue().getLast_name());
        assertEquals("jane@example.com", captor.getValue().getEmail());
        assertEquals("Analyst", captor.getValue().getTitle());
    }

    @Test
    void addEmployee_malformedJson_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ not valid json"))
                .andExpect(status().isBadRequest());

        verify(employeeManager, never()).addEmployee(any());
    }

    @Test
    void addEmployee_wrongContentType_returnsUnsupportedMediaType() throws Exception {
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("hello"))
                .andExpect(status().isUnsupportedMediaType());

        verify(employeeManager, never()).addEmployee(any());
    }
}