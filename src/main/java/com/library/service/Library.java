package com.library.service;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import com.library.util.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Library class that manages all library operations
 */
public class Library {
    private Map<String, Book> books;
    private Map<String, Member> members;
    private List<Transaction> transactions;
    private int transactionCounter;

    /**
     * Constructor
     */
    public Library() {
        this.books = new HashMap<>();
        this.members = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.transactionCounter = 1;
    }

    /**
     * Add a new book to the library
     */
    public boolean addBook(Book book) {
        if (book == null || books.containsKey(book.getIsbn())) {
            return false;
        }
        books.put(book.getIsbn(), book);
        return true;
    }

    /**
     * Remove a book from the library
     */
    public boolean removeBook(String isbn) {
        if (isbn == null || !books.containsKey(isbn)) {
            return false;
        }
        Book book = books.get(isbn);
        if (!book.isAvailable()) {
            return false; // Cannot remove borrowed book
        }
        books.remove(isbn);
        return true;
    }

    /**
     * Register a new member
     */
    public boolean registerMember(Member member) {
        if (member == null || members.containsKey(member.getMemberId())) {
            return false;
        }
        members.put(member.getMemberId(), member);
        return true;
    }

    /**
     * Remove a member
     */
    public boolean removeMember(String memberId) {
        if (memberId == null || !members.containsKey(memberId)) {
            return false;
        }
        Member member = members.get(memberId);
        if (member.getBorrowedBooksCount() > 0) {
            return false; // Cannot remove member with borrowed books
        }
        members.remove(memberId);
        return true;
    }

    /**
     * Process book borrowing
     */
    public boolean borrowBook(String memberId, String isbn) {
        if (memberId == null || isbn == null) {
            return false;
        }
        
        Member member = members.get(memberId);
        Book book = books.get(isbn);
        
        if (member == null || book == null) {
            return false;
        }
        
        if (!book.isAvailable()) {
            return false;
        }
        
        // Borrow the book
        book.borrow(memberId);
        member.borrowBook(isbn);
        
        // Create transaction
        Date transactionDate = DateUtils.getCurrentDate();
        Date dueDate = DateUtils.addDays(transactionDate, 14);
        Transaction transaction = new Transaction(
            "T" + String.format("%04d", transactionCounter++),
            memberId,
            isbn,
            Transaction.TransactionType.BORROW,
            transactionDate,
            dueDate
        );
        transactions.add(transaction);
        
        return true;
    }

    /**
     * Process book return
     */
    public boolean returnBook(String memberId, String isbn) {
        if (memberId == null || isbn == null) {
            return false;
        }
        
        Member member = members.get(memberId);
        Book book = books.get(isbn);
        
        if (member == null || book == null) {
            return false;
        }
        
        if (book.isAvailable() || !memberId.equals(book.getBorrowedBy())) {
            return false;
        }
        
        // Return the book
        book.returnBook();
        member.returnBook(isbn);
        
        // Create transaction
        Transaction transaction = new Transaction(
            "T" + String.format("%04d", transactionCounter++),
            memberId,
            isbn,
            Transaction.TransactionType.RETURN,
            DateUtils.getCurrentDate(),
            null
        );
        transactions.add(transaction);
        
        return true;
    }

    /**
     * Search books by title (partial match)
     */
    public List<Book> searchBooksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchTerm = title.toLowerCase();
        return books.values().stream()
            .filter(book -> book.getTitle().toLowerCase().contains(searchTerm))
            .collect(Collectors.toList());
    }

    /**
     * Search books by author (partial match)
     */
    public List<Book> searchBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchTerm = author.toLowerCase();
        return books.values().stream()
            .filter(book -> book.getAuthor().toLowerCase().contains(searchTerm))
            .collect(Collectors.toList());
    }

    /**
     * Get all available books
     */
    public List<Book> getAvailableBooks() {
        return books.values().stream()
            .filter(Book::isAvailable)
            .collect(Collectors.toList());
    }

    /**
     * Get member's borrowing history
     */
    public List<Transaction> getMemberBorrowHistory(String memberId) {
        if (memberId == null) {
            return new ArrayList<>();
        }
        
        return transactions.stream()
            .filter(t -> t.getMemberId().equals(memberId))
            .collect(Collectors.toList());
    }

    /**
     * Get overdue books (borrowed for more than 14 days)
     */
    public List<Book> getOverdueBooks() {
        Date currentDate = DateUtils.getCurrentDate();
        List<Book> overdueBooks = new ArrayList<>();
        
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionType() == Transaction.TransactionType.BORROW) {
                // Check if this book is still borrowed
                Book book = books.get(transaction.getIsbn());
                if (book != null && !book.isAvailable()) {
                    long daysBorrowed = DateUtils.calculateDaysBetween(transaction.getTransactionDate(), currentDate);
                    if (daysBorrowed > 14) {
                        overdueBooks.add(book);
                    }
                }
            }
        }
        
        return overdueBooks;
    }

    /**
     * Display all books
     */
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        
        System.out.println("\n========== ALL BOOKS ==========");
        for (Book book : books.values()) {
            System.out.println(book);
        }
        System.out.println("================================\n");
    }

    /**
     * Display all members
     */
    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }
        
        System.out.println("\n========== ALL MEMBERS ==========");
        for (Member member : members.values()) {
            System.out.println(member);
        }
        System.out.println("=================================\n");
    }

    /**
     * Populate sample data for testing
     */
    public void populateSampleData() {
        // Add sample books
        addBook(new Book("9780132350884", "Clean Code", "Robert C. Martin", 2008, true, null));
        addBook(new Book("9780201633610", "Design Patterns", "Gang of Four", 1994, true, null));
        addBook(new Book("9780134685991", "Effective Java", "Joshua Bloch", 2017, true, null));
        addBook(new Book("9781617294945", "Java 8 in Action", "Raoul-Gabriel Urma", 2015, true, null));
        addBook(new Book("9780596009205", "Head First Java", "Kathy Sierra", 2005, true, null));
        
        // Add sample members
        Date currentDate = DateUtils.getCurrentDate();
        registerMember(new Member("M001", "John Doe", "john.doe@email.com", currentDate));
        registerMember(new Member("M002", "Jane Smith", "jane.smith@email.com", currentDate));
        registerMember(new Member("M003", "Bob Johnson", "bob.johnson@email.com", currentDate));
        
        // Add some sample transactions
        borrowBook("M001", "9780132350884");
        borrowBook("M002", "9780201633610");
    }

    // Getters for testing/debugging
    public Map<String, Book> getBooks() {
        return books;
    }

    public Map<String, Member> getMembers() {
        return members;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
