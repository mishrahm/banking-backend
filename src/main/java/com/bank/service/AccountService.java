package com.bank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.dto.TransactionDTO;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	
	//Get balance
	public Double getBalDouble(Long accountId) {
		Account account = accountRepository
				.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found")); 
		return account.getBalance();		
	}
	
	//deposit money
	public void deposit(Long accountId, Double amount) {
		if(amount<=0) {
			throw new RuntimeException("Deposit amount must be positive");
		}
		Account account = accountRepository.findById(accountId)
				.orElseThrow( () -> new RuntimeException("Account not found"));
		account.setBalance(account.getBalance() + amount);
		accountRepository.save(account);
		
		 Transaction txn = new Transaction("DEPOSIT", amount, account);
		 //transactionRepository.save(txn);
		 account.getTransactions().add(txn);
		 accountRepository.save(account);
	}
	
	//withdraw money
	public void withdraw(Long accountId, Double amount) {
		if(amount <= 0) {
			throw new RuntimeException("Withdraw amount must be positive");
		}
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Account not found"));
		if(account.getBalance() < amount) {
			throw new RuntimeException("Insufficient balance");
		}
		account.setBalance(account.getBalance() - amount);
		accountRepository.save(account);
		
		Transaction txn = new Transaction("WITHDRAW", amount, account);
		//transactionRepository.save(txn);
		account.getTransactions().add(txn);
		accountRepository.save(account);
	}
	
	
	public List<TransactionDTO> getTransactionHistory(Long accountId) {

	    Account account = accountRepository.findById(accountId)
	            .orElseThrow(() -> new RuntimeException("Account not found"));

	    return transactionRepository.findByAccountId(accountId)
	            .stream()
	            .map(txn -> new TransactionDTO(
	                    txn.getType(),
	                    txn.getAmount(),
	                    txn.getTimestamp()
	            ))
	            .collect(Collectors.toList());
	}


}
