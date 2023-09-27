package com.yohann.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.yohann.api.model.Employee;
import com.yohann.api.service.EmployeeService;

import java.util.Optional;

@RestController
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Read - Get all employees
     *
     * @return - An Iterable object of Employee fullfilled
     */
    @GetMapping("/employees")
    public Iterable<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    /**
     * Read - Get one employee
     *
     * @param id The id of the employee
     * @return An Employee object fullfilled
     */
    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") final Long id) {
        Optional<Employee> employee = employeeService.getEmployee(id);
        return employee.isPresent() ? employee.get() : null;
    }

    /**
     * Create - Add a new employee
     *
     * @param employee An object employee
     * @return The employee object saved
     */
    @PostMapping("/employee")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.append(error.getDefaultMessage()).append(",");
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
        }
        employeeService.saveEmployee(employee);

        if (employee != null) {
            return ResponseEntity.ok("Employee created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the employee");
        }
    }

//    @PostMapping("/employee")
//    public Employee createEmployee(@RequestBody Employee employee) {
//        return employeeService.saveEmployee(employee);
//    }


    /**
     * Update - Update an existing employee
     *
     * @param id       - The id of the employee to update
     * @param employee - The employee object updated
     * @return
     */
    @PutMapping("/employee/{id}/edit")
    public Employee updateEmployee(@PathVariable("id") final Long id, @RequestBody Employee employee) {
        Optional<Employee> e = employeeService.getEmployee(id);

        if (e.isPresent()) {
            Employee currentEmployee = e.get();

            String firstName = employee.getFirstName();
            if (firstName != null) {
                currentEmployee.setFirstName(firstName);
            }
            String lastName = employee.getLastName();
            if (lastName != null) {
                currentEmployee.setLastName(lastName);
            }
            String mail = employee.getMail();
            if (mail != null) {
                currentEmployee.setMail(mail);
            }
            String password = employee.getPassword();
            if (password != null) {
                currentEmployee.setPassword(password);
            }
            employeeService.saveEmployee(currentEmployee);
            return currentEmployee;
        } else {
            return null;
        }
    }


    /**
     * Delete - Delete an employee
     *
     * @param id - The id of the employee to delete
     */
    @DeleteMapping("/deleteEmployee/{id}")
    public void deleteEmployee(@PathVariable("id") final Long id) {
        employeeService.deleteEmployee(id);
    }
}