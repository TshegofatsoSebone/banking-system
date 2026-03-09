package com.bank.banking_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Email
    @Column(unique = true)
    private String email;

    private String phoneNumber;

    @Column(unique = true)
    private String idNumber;

    private String pin;
}