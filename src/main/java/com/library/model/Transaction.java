package com.library.model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a transaction in the library system.
 */
public class Transaction {
    private String transactionId;
    private String memberId;
    private String isbn;
    private TransactionType transactionType;
    private LocalDate transactionDate;
    private LocalDate dueDate;

    /**
     * Enum for transaction types.
     */
    public enum TransactionType {
        BORROW, RETURN
    }

    /**
     * Constructor for creating a transaction.
     */
    public Transaction(String memberId, String isbn, TransactionType transactionType) {
        this.transactionId = UUID.randomUUID().toString();
        this.memberId = memberId;
        this.isbn = isbn;
        this.transactionType = transactionType;
        this.transactionDate = LocalDate.now();
        
        // Set due date for borrowed books (14 days from now)
        if (transactionType == TransactionType.BORROW) {
            this.dueDate = transactionDate.plusDays(14);
        }
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        String dueDateStr = dueDate != null ? " | Due: " + dueDate : "";
        return String.format("Transaction ID: %s | Member: %s | ISBN: %s | Type: %s | Date: %s%s",
                transactionId, memberId, isbn, transactionType, transactionDate, dueDateStr);
    }
}
