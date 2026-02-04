package com.library.model;

/**
 * Book class representing a book in the library system
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private boolean isAvailable;
    private String borrowedBy;

    /**
     * Constructor with all fields
     */
    public Book(String isbn, String title, String author, int publicationYear, boolean isAvailable, String borrowedBy) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isAvailable = isAvailable;
        this.borrowedBy = borrowedBy;
    }

    /**
     * Borrow this book for a member
     */
    public boolean borrow(String memberId) {
        if (isAvailable) {
            this.isAvailable = false;
            this.borrowedBy = memberId;
            return true;
        }
        return false;
    }

    /**
     * Return this book
     */
    public boolean returnBook() {
        if (!isAvailable) {
            this.isAvailable = true;
            this.borrowedBy = null;
            return true;
        }
        return false;
    }

    // Getters and setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(String borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    @Override
    public String toString() {
        return String.format("ISBN: %s | Title: %s | Author: %s | Year: %d | Available: %s%s",
                isbn, title, author, publicationYear, 
                isAvailable ? "Yes" : "No",
                !isAvailable ? " | Borrowed by: " + borrowedBy : "");
    }
}
