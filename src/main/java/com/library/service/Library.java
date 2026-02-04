package com.library.service;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import com.library.model.Transaction.TransactionType;
import com.library.util.DateUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main library service class that manages all library operations.
 */
public class Library {
    private Map<String, Book> books;
    private Map<String, Member> members;
    private List<Transaction> transactions;

    /**
     * Constructor initializes data structures.
     */
    public Library() {
        this.books = new HashMap<>();
        this.members = new HashMap<>();
        this.transactions = new ArrayList<>();
    }

    /**
     * Add a new book to the library.
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
     * Remove a book from the library.
     */
    public boolean removeBook(String isbn) {
        if (isbn == null || !books.containsKey(isbn)) {
            return false;
        }
        Book book = books.get(isbn);
        if (!book.isAvailable()) {
            System.out.println("Cannot remove book that is currently borrowed.");
            return false;
        }
        books.remove(isbn);
        return true;
    }

    /**
     * Register a new member.
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
     * Remove a member from the library.
     */
    public boolean removeMember(String memberId) {
        if (memberId == null || !members.containsKey(memberId)) {
            return false;
        }
        Member member = members.get(memberId);
        if (member.getBorrowedBooksCount() > 0) {
            System.out.println("Cannot remove member with borrowed books.");
            return false;
        }
        members.remove(memberId);
        return true;
    }

    /**
     * Process book borrowing.
     */
    public boolean borrowBook(String memberId, String isbn) {
        if (memberId == null || isbn == null) {
            System.out.println("Invalid member ID or ISBN.");
            return false;
        }

        Member member = members.get(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return false;
        }

        Book book = books.get(isbn);
        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }

        if (!book.isAvailable()) {
            System.out.println("Book is not available.");
            return false;
        }

        // Borrow the book
        if (book.borrow(memberId)) {
            member.borrowBook(isbn);
            Transaction transaction = new Transaction(memberId, isbn, TransactionType.BORROW);
            transactions.add(transaction);
            System.out.println("Book borrowed successfully. Due date: " + transaction.getDueDate());
            return true;
        }

        return false;
    }

    /**
     * Process book return.
     */
    public boolean returnBook(String memberId, String isbn) {
        if (memberId == null || isbn == null) {
            System.out.println("Invalid member ID or ISBN.");
            return false;
        }

        Member member = members.get(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return false;
        }

        Book book = books.get(isbn);
        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }

        if (!member.getBorrowedBooks().contains(isbn)) {
            System.out.println("This book was not borrowed by this member.");
            return false;
        }

        // Return the book
        if (book.returnBook()) {
            member.returnBook(isbn);
            Transaction transaction = new Transaction(memberId, isbn, TransactionType.RETURN);
            transactions.add(transaction);
            System.out.println("Book returned successfully.");
            return true;
        }

        return false;
    }

    /**
     * Search books by title (partial match, case-insensitive).
     */
    public List<Book> searchBooksByTitle(String title) {
        if (title == null || title.isEmpty()) {
            return new ArrayList<>();
        }
        String lowerTitle = title.toLowerCase();
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lowerTitle))
                .collect(Collectors.toList());
    }

    /**
     * Search books by author (partial match, case-insensitive).
     */
    public List<Book> searchBooksByAuthor(String author) {
        if (author == null || author.isEmpty()) {
            return new ArrayList<>();
        }
        String lowerAuthor = author.toLowerCase();
        return books.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(lowerAuthor))
                .collect(Collectors.toList());
    }

    /**
     * Get all available books.
     */
    public List<Book> getAvailableBooks() {
        return books.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    /**
     * Get member's borrowing history.
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
     * Get overdue books (books borrowed more than 14 days ago and not yet returned).
     */
    public List<Book> getOverdueBooks() {
        LocalDate today = DateUtils.getCurrentDate();
        List<Book> overdueBooks = new ArrayList<>();

        // Find all borrow transactions
        Map<String, Transaction> latestBorrows = new HashMap<>();
        for (Transaction t : transactions) {
            if (t.getTransactionType() == TransactionType.BORROW) {
                latestBorrows.put(t.getIsbn(), t);
            } else if (t.getTransactionType() == TransactionType.RETURN) {
                latestBorrows.remove(t.getIsbn());
            }
        }

        // Check which books are overdue
        for (Transaction t : latestBorrows.values()) {
            if (t.getDueDate() != null && today.isAfter(t.getDueDate())) {
                Book book = books.get(t.getIsbn());
                if (book != null && !book.isAvailable()) {
                    overdueBooks.add(book);
                }
            }
        }

        return overdueBooks;
    }

    /**
     * Display all books with their status.
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
     * Display all registered members.
     */
    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("          ALL MEMBERS");
        System.out.println("========================================");
        for (Member member : members.values()) {
            System.out.println(member);
        }
        System.out.println("========================================");
        System.out.println("Total members: " + members.size());
    }

    /**
     * Get a book by ISBN.
     */
    public Book getBook(String isbn) {
        return books.get(isbn);
    }

    /**
     * Get a member by ID.
     */
    public Member getMember(String memberId) {
        return members.get(memberId);
    }

    /**
     * Load sample data for testing.
     */
    public void loadSampleData() {
        // Sample books
        addBook(new Book("9780134685991", "Effective Java", "Joshua Bloch", 2018));
        addBook(new Book("9780132350884", "Clean Code", "Robert C. Martin", 2008));
        addBook(new Book("9780201633610", "Design Patterns", "Gang of Four", 1994));
        addBook(new Book("9780137081073", "The Clean Coder", "Robert C. Martin", 2011));
        addBook(new Book("9781617294945", "Spring in Action", "Craig Walls", 2018));

        // Sample members
        registerMember(new Member("MEM001", "Alice Johnson", "alice@example.com"));
        registerMember(new Member("MEM002", "Bob Smith", "bob@example.com"));
        registerMember(new Member("MEM003", "Charlie Brown", "charlie@example.com"));

        // Sample transactions
        borrowBook("MEM001", "9780134685991");
        borrowBook("MEM002", "9780132350884");

        System.out.println("Sample data loaded successfully!");
    }
}
