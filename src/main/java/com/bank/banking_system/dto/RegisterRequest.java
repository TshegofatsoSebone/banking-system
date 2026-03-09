package com.bank.banking_system.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegisterRequest {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String idNumber;
    private String pin;

}
