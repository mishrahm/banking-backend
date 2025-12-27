package com.bank.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String type; // Deposit or withdraw
	
	private Double amount;
	
	private LocalDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	
	
	//constructors
	public Transaction() {
		 this.timestamp = LocalDateTime.now();
	}

	public Transaction(String type, Double amount, Account account) {
		this.type = type;
		this.amount = amount;
		this.account = account;
		this.timestamp = LocalDateTime.now();
		
	}
	// ✅ REQUIRED GETTERS
    public String getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
	

}
