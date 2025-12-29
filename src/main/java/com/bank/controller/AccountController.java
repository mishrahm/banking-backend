package com.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dto.TransactionDTO;
import com.bank.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

    /*
     * Controller should talk ONLY to Service layer
     * Never directly to Repository (bank-grade rule)
     */
    @Autowired
    private AccountService accountService;

    /* =========================================================
       GET BALANCE
       - No accountId from client
       - Account is derived from JWT
       ========================================================= */
    @GetMapping("/balance")
    public Double getBalance() {
        return accountService.getBalance();
    }

    /* =========================================================
       DEPOSIT MONEY
       - Client sends ONLY amount
       - User identity comes from JWT
       ========================================================= */
    @PostMapping("/deposit")
    public String deposit(@RequestParam Double amount) {

        accountService.deposit(amount);
        return "Deposit successful";
    }

    /* =========================================================
       WITHDRAW MONEY
       - Fully JWT-based
       - Prevents accessing other users' accounts
       ========================================================= */
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Double amount) {

        accountService.withdraw(amount);
        return "Withdraw successful";
    }

    /* =========================================================
       TRANSACTION HISTORY
       - Returns DTOs (not entities)
       - Account resolved via JWT
       ========================================================= */
    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactionHistory() {

        return accountService.getTransactionHistory();
    }
}
