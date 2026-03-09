package com.bank.banking_system.controller;

import com.bank.banking_system.dto.LoginRequest;
import com.bank.banking_system.dto.RegisterRequest;
import com.bank.banking_system.model.Account;
import com.bank.banking_system.model.User;
import com.bank.banking_system.repository.AccountRepository;
import com.bank.banking_system.repository.UserRepository;
import com.bank.banking_system.utility.IdValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AuthController(UserRepository userRepository,
                          AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (!IdValidator.isAdult(request.getIdNumber())) {
            return ResponseEntity.badRequest().body("User must be 18 or older");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setIdNumber(request.getIdNumber());
        user.setPin(request.getPin());

        userRepository.save(user);

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(0.0);
        account.setUser(user);

        accountRepository.save(account);

        return ResponseEntity.ok("Account created successfully");
    }

    private String generateAccountNumber() {
        return "AC" + System.currentTimeMillis();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail());

        if (user == null || !user.getPin().equals(request.getPin())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or PIN");
        }

        return ResponseEntity.ok(user);
    }
}