package com.yohann.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "First name may not be empty")
    @Size(min = 2, max = 32, message = "First name must be between 2 and 32 characters long")
    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @NotEmpty(message = "Mail may not be empty")
    private String mail;

    @NotEmpty(message = "Password may not be empty")
    @Size(min = 3, message = "Password must be minimum 3 characters long")
    private String password;

}
