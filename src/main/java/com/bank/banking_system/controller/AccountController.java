package com.bank.banking_system.controller;

import com.bank.banking_system.model.Account;
import com.bank.banking_system.model.Transaction;
import com.bank.banking_system.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Account createAccount(@RequestParam String fullName,
                                 @RequestParam String email){

        return service.createAccount(fullName, email);
    }

    // ✅ Create Deposit
    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id,
                           @RequestParam Double amount) {
        return service.deposit(id, amount);
    }

    // ✅ Withdraw Money
    @PostMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable Long id,
                            @RequestParam Double amount) {
        return service.withdraw(id, amount);
    }

    // ✅ Transfer Money
    @PostMapping("/transfer")
    public String transfer(@RequestParam Long from,
                           @RequestParam Long to,
                           @RequestParam Double amount) {

        service.transfer(from, to, amount);
        return "Transfer successful";
    }

    // ✅ Get Account By ID (THIS FIXES YOUR ERROR)
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id){
        return service.getAccount(id);
    }

    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long id){
        return service.getTransactionHistory(id);
    }


}

