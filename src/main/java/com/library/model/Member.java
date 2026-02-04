package com.library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a library member.
 */
public class Member {
    private String memberId;
    private String name;
    private String email;
    private List<String> borrowedBooks; // List of ISBNs
    private LocalDate registrationDate;

    /**
     * Constructor to create a new member.
     */
    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
        this.registrationDate = LocalDate.now();
    }

    // Getters and Setters
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

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * Borrow a book by adding its ISBN to the borrowed books list.
     */
    public void borrowBook(String isbn) {
        if (!borrowedBooks.contains(isbn)) {
            borrowedBooks.add(isbn);
        }
    }

    /**
     * Return a book by removing its ISBN from the borrowed books list.
     */
    public boolean returnBook(String isbn) {
        return borrowedBooks.remove(isbn);
    }

    /**
     * Get the count of borrowed books.
     */
    public int getBorrowedBooksCount() {
        return borrowedBooks.size();
    }

    @Override
    public String toString() {
        return String.format("Member ID: %s | Name: %s | Email: %s | Borrowed Books: %d | Registered: %s",
                memberId, name, email, borrowedBooks.size(), registrationDate);
    }
}
