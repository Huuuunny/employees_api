package com.yohann.api.employees;

import com.yohann.api.model.Employee;
import com.yohann.api.repository.EmployeeRepository;
import com.yohann.api.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void whenValidId_thenEmployeeShouldBeFound() {
        Long id = 1L;
        Employee mockEmployee = new Employee();
        mockEmployee.setId(id);
        mockEmployee.setFirstName("Test Employee");
        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee));

        Optional<Employee> found = employeeService.getEmployee(id);

        assertTrue(found.isPresent());
        assertEquals(found.get().getId(), id);
        assertEquals(found.get().getFirstName(), "Test Employee");

    }

    @Test
    public void whenInvalidId_thenEmployeeShouldNotBeFound() {
        Long id = 2L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Employee> found = employeeService.getEmployee(id);

        assertTrue(found.isEmpty());
    }

}
