package com.library.model;

import java.util.Date;

/**
 * Transaction class to track borrow/return activities
 */
public class Transaction {
    /**
     * Transaction type enum
     */
    public enum TransactionType {
        BORROW, RETURN
    }

    private String transactionId;
    private String memberId;
    private String isbn;
    private TransactionType transactionType;
    private Date transactionDate;
    private Date dueDate;

    /**
     * Constructor
     */
    public Transaction(String transactionId, String memberId, String isbn, 
                      TransactionType transactionType, Date transactionDate, Date dueDate) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.isbn = isbn;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.dueDate = dueDate;
    }

    // Getters and setters
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

    public Date getTransactionDate() {
        return transactionDate != null ? new Date(transactionDate.getTime()) : null;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getDueDate() {
        return dueDate != null ? new Date(dueDate.getTime()) : null;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return String.format("Transaction ID: %s | Type: %s | Member: %s | ISBN: %s | Date: %s%s",
                transactionId, transactionType, memberId, isbn, transactionDate,
                dueDate != null ? " | Due Date: " + dueDate : "");
    }
}
