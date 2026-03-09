package com.bank.banking_system.service;

import com.bank.banking_system.exception.ResourceNotFoundException;
import com.bank.banking_system.model.Account;
import com.bank.banking_system.model.Transaction;
import com.bank.banking_system.model.User;
import com.bank.banking_system.repository.AccountRepository;
import com.bank.banking_system.repository.TransactionRepository;
import com.bank.banking_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository,
                          UserRepository userRepository) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }


    public Account createAccount(String fullName, String email){

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPin("1234"); // temporary test pin

        userRepository.save(user);

        Account account = new Account();
        account.setAccountNumber("AC" + System.currentTimeMillis());
        account.setBalance(0.0);
        account.setUser(user);

        return accountRepository.save(account);
    }
    // ✅ Deposit Money
    @Transactional
    public Account deposit(Long accountId, Double amount) {

        validateAmount(amount);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found"));

        account.setBalance(account.getBalance() + amount);

        saveTransaction(account, "DEPOSIT", amount);

        return accountRepository.save(account);
    }

    // ✅ Withdraw Money
    @Transactional
    public Account withdraw(Long accountId, Double amount) {

        validateAmount(amount);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);

        saveTransaction(account, "WITHDRAW", amount);

        return accountRepository.save(account);
    }

    // ✅ Transfer Money
    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, Double amount) {

        validateAmount(amount);

        Account sender = accountRepository.findById(fromAccountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sender account not found"));

        Account receiver = accountRepository.findById(toAccountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Receiver account not found"));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);
    }

    // ✅ Private Helper Methods (Clean Architecture)

    private void validateAmount(Double amount) {
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Transaction amount must be greater than zero");
        }
    }

    private void saveTransaction(Account account, String type, Double amount) {

        Transaction tx = new Transaction();
        tx.setType(type);
        tx.setAmount(amount);
        tx.setTimestamp(LocalDateTime.now());
        tx.setAccount(account);

        transactionRepository.save(tx);
    }

    public Account getAccount(Long id){

        return accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found"));
    }

    public List<Transaction> getTransactionHistory(Long accountId){

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found"));

        return transactionRepository.findByAccountId(accountId);
    }
}