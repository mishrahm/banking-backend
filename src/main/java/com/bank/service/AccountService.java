package com.bank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.dto.TransactionDTO;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserRepository;
import com.bank.security.SecurityUtil;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    /* =========================================================
        CORE METHOD — SINGLE SOURCE OF ACCOUNT
       ========================================================= */
    private Account getAccountForCurrentUser() {

        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return accountRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    /* =========================================================
        GET BALANCE (JWT BASED)
       ========================================================= */
    public Double getBalance() {
        return getAccountForCurrentUser().getBalance();
    }

    /* =========================================================
        DEPOSIT MONEY (JWT BASED)
       ========================================================= */
    public void deposit(Double amount) {

        if (amount <= 0) {
            throw new RuntimeException("Deposit amount must be positive");
        }

        Account account = getAccountForCurrentUser();
        account.setBalance(account.getBalance() + amount);

        Transaction txn = new Transaction("DEPOSIT", amount, account);
        account.getTransactions().add(txn);

        accountRepository.save(account); // cascade saves transaction
    }

    /* =========================================================
        WITHDRAW MONEY (JWT BASED)
       ========================================================= */
    public void withdraw(Double amount) {

        if (amount <= 0) {
            throw new RuntimeException("Withdraw amount must be positive");
        }

        Account account = getAccountForCurrentUser();

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);

        Transaction txn = new Transaction("WITHDRAW", amount, account);
        account.getTransactions().add(txn);

        accountRepository.save(account);
    }

    /* =========================================================
        TRANSACTION HISTORY (JWT BASED)
       ========================================================= */
    public List<TransactionDTO> getTransactionHistory() {

        Account account = getAccountForCurrentUser();

        return transactionRepository.findByAccountId(account.getId())
                .stream()
                .map(txn -> new TransactionDTO(
                        txn.getType(),
                        txn.getAmount(),
                        txn.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}
