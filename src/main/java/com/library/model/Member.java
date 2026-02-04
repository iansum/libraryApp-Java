package com.library.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Member class representing a library member
 */
public class Member {
    private String memberId;
    private String name;
    private String email;
    private List<String> borrowedBooks;
    private Date registrationDate;

    /**
     * Constructor
     */
    public Member(String memberId, String name, String email, Date registrationDate) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
        this.registrationDate = registrationDate;
    }

    /**
     * Borrow a book
     */
    public void borrowBook(String isbn) {
        if (!borrowedBooks.contains(isbn)) {
            borrowedBooks.add(isbn);
        }
    }

    /**
     * Return a book
     */
    public boolean returnBook(String isbn) {
        return borrowedBooks.remove(isbn);
    }

    /**
     * Get count of borrowed books
     */
    public int getBorrowedBooksCount() {
        return borrowedBooks.size();
    }

    // Getters and setters
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<String> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return String.format("Member ID: %s | Name: %s | Email: %s | Books Borrowed: %d | Registration Date: %s",
                memberId, name, email, borrowedBooks.size(), registrationDate);
    }
}
