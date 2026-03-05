package com.bank.banking_system.repository;

import com.bank.banking_system.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}