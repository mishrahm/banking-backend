package com.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.model.Transaction;
import com.bank.repository.TransactionRepository;
import com.bank.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final TransactionRepository transactionRepository;
	
	@Autowired
	private AccountService accountService;

    AccountController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
	
	//get balance
	@GetMapping("/balance/{id}")
	public Double getBalance(@PathVariable Long id) {
		return accountService.getBalDouble(id);	
	}
	
	//deposit
	 // @GetMapping("/deposit")
	 @PostMapping("/deposit")
	  public String deposit(@RequestParam Long accountId,
	                          @RequestParam Double amount) {

	        accountService.deposit(accountId, amount);
	        return "Deposit successful";
	    }
	
	//withdraw
	//@GetMapping("/withdraw")
	@PostMapping("/withdraw")
	public String withdraw(@RequestParam Long accountId,
			              @RequestParam Double amount) {
		accountService.withdraw(accountId, amount);
		return "Withdraw successfull";
	}
	
	@GetMapping("/transactions/{accountId}")
	public List<Transaction> getTransaction(@PathVariable Long accountId){
		return transactionRepository.findByAccountId(accountId);
	}
	

}
