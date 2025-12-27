package com.bank.dto;

import java.time.LocalDateTime;

public class TransactionDTO {

    private String type;
    private Double amount;
    private LocalDateTime timestamp;

    public TransactionDTO(String type, Double amount, LocalDateTime timestamp) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

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
