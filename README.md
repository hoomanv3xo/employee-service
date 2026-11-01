# Employee Service

A basic RESTful web service built with Java Spring Boot and Gradle.

`GET /employees` returns a hard-coded list of employees as JSON.

## Run

    ./gradlew bootRun

Then open http://localhost:8080/employees

## Structure

- `Employee` – model with employee_id, first_name, last_name, email, title
- `Employees` – wraps the list of employees
- `EmployeeManager` – initializes the sample data
- `EmployeeController` – handles the GET /employees request
