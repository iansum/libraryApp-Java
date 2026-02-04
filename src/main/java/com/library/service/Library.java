package com.library.service;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import com.library.model.Transaction.TransactionType;
import com.library.util.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Library service class that manages all library operations
 */
public class Library {
    
    private Map<String, Book> books;
    private Map<String, Member> members;
    private List<Transaction> transactions;
    
    public Library() {
        this.books = new HashMap<>();
        this.members = new HashMap<>();
        this.transactions = new ArrayList<>();
    }
    
    /**
     * Add a new book to the library
     */
    public boolean addBook(Book book) {
        if (book == null || book.getIsbn() == null) {
            return false;
        }
        if (books.containsKey(book.getIsbn())) {
            System.out.println("Book with ISBN " + book.getIsbn() + " already exists.");
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
            System.out.println("Cannot remove book - it is currently borrowed.");
            return false;
        }
        books.remove(isbn);
        return true;
    }
    
    /**
     * Register a new member
     */
    public boolean registerMember(Member member) {
        if (member == null || member.getMemberId() == null) {
            return false;
        }
        if (members.containsKey(member.getMemberId())) {
            System.out.println("Member with ID " + member.getMemberId() + " already exists.");
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
            System.out.println("Cannot remove member - they have borrowed books.");
            return false;
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
        
        if (member == null) {
            System.out.println("Member not found.");
            return false;
        }
        
        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }
        
        if (!book.isAvailable()) {
            System.out.println("Book is not available.");
            return false;
        }
        
        // Borrow the book
        book.borrow(memberId);
        member.borrowBook(isbn);
        
        // Create transaction
        Date borrowDate = DateUtils.getCurrentDate();
        Date dueDate = DateUtils.addDays(borrowDate, 14);
        Transaction transaction = new Transaction(memberId, isbn, TransactionType.BORROW, borrowDate, dueDate);
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
        
        if (member == null) {
            System.out.println("Member not found.");
            return false;
        }
        
        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }
        
        if (book.isAvailable() || !memberId.equals(book.getBorrowedBy())) {
            System.out.println("This book is not borrowed by this member.");
            return false;
        }
        
        // Return the book
        book.returnBook();
        member.returnBook(isbn);
        
        // Create transaction
        Date returnDate = DateUtils.getCurrentDate();
        Transaction transaction = new Transaction(memberId, isbn, TransactionType.RETURN, returnDate, null);
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
     * Get overdue books (>14 days)
     */
    public List<Book> getOverdueBooks() {
        Date currentDate = DateUtils.getCurrentDate();
        List<Book> overdueBooks = new ArrayList<>();
        
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionType() == TransactionType.BORROW && 
                transaction.getDueDate() != null) {
                
                // Check if the book is still borrowed
                Book book = books.get(transaction.getIsbn());
                if (book != null && !book.isAvailable()) {
                    // Check if overdue
                    if (currentDate.after(transaction.getDueDate())) {
                        overdueBooks.add(book);
                    }
                }
            }
        }
        
        return overdueBooks;
    }
    
    /**
     * Display all books with status
     */
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("           ALL BOOKS");
        System.out.println("========================================");
        for (Book book : books.values()) {
            System.out.println(book);
        }
        System.out.println("========================================");
        System.out.println("Total books: " + books.size());
    }
    
    /**
     * Display all registered members
     */
    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("         ALL MEMBERS");
        System.out.println("========================================");
        for (Member member : members.values()) {
            System.out.println(member);
        }
        System.out.println("========================================");
        System.out.println("Total members: " + members.size());
    }
    
    /**
     * Get a book by ISBN
     */
    public Book getBook(String isbn) {
        return books.get(isbn);
    }
    
    /**
     * Get a member by ID
     */
    public Member getMember(String memberId) {
        return members.get(memberId);
    }
    
    /**
     * Get all books
     */
    public Map<String, Book> getAllBooks() {
        return new HashMap<>(books);
    }
    
    /**
     * Get all members
     */
    public Map<String, Member> getAllMembers() {
        return new HashMap<>(members);
    }
    
    /**
     * Load sample data for testing
     */
    public void loadSampleData() {
        // Sample books
        addBook(new Book("9780134685991", "Effective Java", "Joshua Bloch", 2018));
        addBook(new Book("9780132350884", "Clean Code", "Robert C. Martin", 2008));
        addBook(new Book("9780596009205", "Head First Design Patterns", "Eric Freeman", 2004));
        addBook(new Book("9780135957059", "The Pragmatic Programmer", "David Thomas", 2019));
        addBook(new Book("9780201633610", "Design Patterns", "Gang of Four", 1994));
        
        // Sample members
        registerMember(new Member("M0001", "John Doe", "john.doe@email.com"));
        registerMember(new Member("M0002", "Jane Smith", "jane.smith@email.com"));
        registerMember(new Member("M0003", "Bob Johnson", "bob.johnson@email.com"));
        
        // Sample transactions
        borrowBook("M0001", "9780134685991");
        borrowBook("M0002", "9780132350884");
        
        System.out.println("Sample data loaded successfully!");
    }
}
