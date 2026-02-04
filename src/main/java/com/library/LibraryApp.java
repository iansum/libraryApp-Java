package com.library;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import com.library.service.Library;
import com.library.util.DateUtils;
import com.library.util.ValidationUtils;

import java.util.List;
import java.util.Scanner;

/**
 * Main application class with console-based menu system
 */
public class LibraryApp {
    private static Library library;
    private static Scanner scanner;

    public static void main(String[] args) {
        library = new Library();
        scanner = new Scanner(System.in);
        
        // Populate sample data
        library.populateSampleData();
        
        System.out.println("\n========================================");
        System.out.println("  WELCOME TO LIBRARY MANAGEMENT SYSTEM");
        System.out.println("========================================\n");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getMenuChoice();
            
            try {
                switch (choice) {
                    case 1:
                        addNewBook();
                        break;
                    case 2:
                        removeBook();
                        break;
                    case 3:
                        registerNewMember();
                        break;
                    case 4:
                        removeMember();
                        break;
                    case 5:
                        borrowBook();
                        break;
                    case 6:
                        returnBook();
                        break;
                    case 7:
                        searchBooksByTitle();
                        break;
                    case 8:
                        searchBooksByAuthor();
                        break;
                    case 9:
                        viewAvailableBooks();
                        break;
                    case 10:
                        viewAllBooks();
                        break;
                    case 11:
                        viewAllMembers();
                        break;
                    case 12:
                        viewMemberBorrowHistory();
                        break;
                    case 13:
                        viewOverdueBooks();
                        break;
                    case 14:
                        running = false;
                        System.out.println("\nThank you for using Library Management System!");
                        System.out.println("Goodbye!\n");
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.\n");
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
        
        scanner.close();
    }

    /**
     * Display main menu
     */
    private static void displayMenu() {
        System.out.println("========================================");
        System.out.println("    LIBRARY MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1.  Add a new book");
        System.out.println("2.  Remove a book");
        System.out.println("3.  Register a new member");
        System.out.println("4.  Remove a member");
        System.out.println("5.  Borrow a book");
        System.out.println("6.  Return a book");
        System.out.println("7.  Search books by title");
        System.out.println("8.  Search books by author");
        System.out.println("9.  View all available books");
        System.out.println("10. View all books");
        System.out.println("11. View all members");
        System.out.println("12. View member borrowing history");
        System.out.println("13. View overdue books");
        System.out.println("14. Exit");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
    }

    /**
     * Get menu choice from user
     */
    private static int getMenuChoice() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Add a new book
     */
    private static void addNewBook() {
        System.out.println("\n--- Add New Book ---");
        
        System.out.print("Enter ISBN (10 or 13 digits): ");
        String isbn = scanner.nextLine().trim();
        
        if (!ValidationUtils.isValidISBN(isbn)) {
            System.out.println("Error: Invalid ISBN format. Must be 10 or 13 digits.");
            return;
        }
        
        if (library.getBooks().containsKey(isbn)) {
            System.out.println("Error: A book with this ISBN already exists.");
            return;
        }
        
        System.out.print("Enter title: ");
        String title = scanner.nextLine().trim();
        
        if (title.isEmpty()) {
            System.out.println("Error: Title cannot be empty.");
            return;
        }
        
        System.out.print("Enter author: ");
        String author = scanner.nextLine().trim();
        
        if (author.isEmpty()) {
            System.out.println("Error: Author cannot be empty.");
            return;
        }
        
        System.out.print("Enter publication year: ");
        int year;
        try {
            year = Integer.parseInt(scanner.nextLine().trim());
            if (year < 1000 || year > 2100) {
                System.out.println("Error: Invalid publication year.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid year format.");
            return;
        }
        
        Book book = new Book(isbn, title, author, year, true, null);
        if (library.addBook(book)) {
            System.out.println("Success: Book added successfully!");
        } else {
            System.out.println("Error: Failed to add book.");
        }
    }

    /**
     * Remove a book
     */
    private static void removeBook() {
        System.out.println("\n--- Remove Book ---");
        
        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine().trim();
        
        if (!library.getBooks().containsKey(isbn)) {
            System.out.println("Error: Book not found.");
            return;
        }
        
        if (library.removeBook(isbn)) {
            System.out.println("Success: Book removed successfully!");
        } else {
            System.out.println("Error: Cannot remove book. It may be currently borrowed.");
        }
    }

    /**
     * Register a new member
     */
    private static void registerNewMember() {
        System.out.println("\n--- Register New Member ---");
        
        System.out.print("Enter Member ID (format: M001, M002, etc.): ");
        String memberId = scanner.nextLine().trim();
        
        if (!ValidationUtils.isValidMemberId(memberId)) {
            System.out.println("Error: Invalid Member ID format. Must start with 'M' followed by at least 3 digits.");
            return;
        }
        
        if (library.getMembers().containsKey(memberId)) {
            System.out.println("Error: A member with this ID already exists.");
            return;
        }
        
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
            return;
        }
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        
        if (!ValidationUtils.isValidEmail(email)) {
            System.out.println("Error: Invalid email format.");
            return;
        }
        
        Member member = new Member(memberId, name, email, DateUtils.getCurrentDate());
        if (library.registerMember(member)) {
            System.out.println("Success: Member registered successfully!");
        } else {
            System.out.println("Error: Failed to register member.");
        }
    }

    /**
     * Remove a member
     */
    private static void removeMember() {
        System.out.println("\n--- Remove Member ---");
        
        System.out.print("Enter Member ID to remove: ");
        String memberId = scanner.nextLine().trim();
        
        if (!library.getMembers().containsKey(memberId)) {
            System.out.println("Error: Member not found.");
            return;
        }
        
        if (library.removeMember(memberId)) {
            System.out.println("Success: Member removed successfully!");
        } else {
            System.out.println("Error: Cannot remove member. They may have borrowed books.");
        }
    }

    /**
     * Borrow a book
     */
    private static void borrowBook() {
        System.out.println("\n--- Borrow Book ---");
        
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        
        if (!library.getMembers().containsKey(memberId)) {
            System.out.println("Error: Member not found.");
            return;
        }
        
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();
        
        if (!library.getBooks().containsKey(isbn)) {
            System.out.println("Error: Book not found.");
            return;
        }
        
        Book book = library.getBooks().get(isbn);
        if (!book.isAvailable()) {
            System.out.println("Error: Book is not available. Currently borrowed by: " + book.getBorrowedBy());
            return;
        }
        
        if (library.borrowBook(memberId, isbn)) {
            System.out.println("Success: Book borrowed successfully!");
            System.out.println("Due date: " + DateUtils.formatDate(DateUtils.addDays(DateUtils.getCurrentDate(), 14)));
        } else {
            System.out.println("Error: Failed to borrow book.");
        }
    }

    /**
     * Return a book
     */
    private static void returnBook() {
        System.out.println("\n--- Return Book ---");
        
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        
        if (!library.getMembers().containsKey(memberId)) {
            System.out.println("Error: Member not found.");
            return;
        }
        
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();
        
        if (!library.getBooks().containsKey(isbn)) {
            System.out.println("Error: Book not found.");
            return;
        }
        
        if (library.returnBook(memberId, isbn)) {
            System.out.println("Success: Book returned successfully!");
        } else {
            System.out.println("Error: Failed to return book. Please verify the member and book details.");
        }
    }

    /**
     * Search books by title
     */
    private static void searchBooksByTitle() {
        System.out.println("\n--- Search Books by Title ---");
        
        System.out.print("Enter title (or partial title): ");
        String title = scanner.nextLine().trim();
        
        List<Book> results = library.searchBooksByTitle(title);
        
        if (results.isEmpty()) {
            System.out.println("No books found matching the title.");
        } else {
            System.out.println("\nSearch Results (" + results.size() + " book(s) found):");
            System.out.println("-------------------------------------------");
            for (Book book : results) {
                System.out.println(book);
            }
            System.out.println("-------------------------------------------");
        }
    }

    /**
     * Search books by author
     */
    private static void searchBooksByAuthor() {
        System.out.println("\n--- Search Books by Author ---");
        
        System.out.print("Enter author (or partial name): ");
        String author = scanner.nextLine().trim();
        
        List<Book> results = library.searchBooksByAuthor(author);
        
        if (results.isEmpty()) {
            System.out.println("No books found by this author.");
        } else {
            System.out.println("\nSearch Results (" + results.size() + " book(s) found):");
            System.out.println("-------------------------------------------");
            for (Book book : results) {
                System.out.println(book);
            }
            System.out.println("-------------------------------------------");
        }
    }

    /**
     * View all available books
     */
    private static void viewAvailableBooks() {
        System.out.println("\n--- Available Books ---");
        
        List<Book> availableBooks = library.getAvailableBooks();
        
        if (availableBooks.isEmpty()) {
            System.out.println("No available books at the moment.");
        } else {
            System.out.println("\nAvailable Books (" + availableBooks.size() + " book(s)):");
            System.out.println("-------------------------------------------");
            for (Book book : availableBooks) {
                System.out.println(book);
            }
            System.out.println("-------------------------------------------");
        }
    }

    /**
     * View all books
     */
    private static void viewAllBooks() {
        library.displayAllBooks();
    }

    /**
     * View all members
     */
    private static void viewAllMembers() {
        library.displayAllMembers();
    }

    /**
     * View member borrowing history
     */
    private static void viewMemberBorrowHistory() {
        System.out.println("\n--- Member Borrowing History ---");
        
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        
        if (!library.getMembers().containsKey(memberId)) {
            System.out.println("Error: Member not found.");
            return;
        }
        
        List<Transaction> history = library.getMemberBorrowHistory(memberId);
        
        if (history.isEmpty()) {
            System.out.println("No transaction history for this member.");
        } else {
            Member member = library.getMembers().get(memberId);
            System.out.println("\nMember: " + member.getName() + " (" + memberId + ")");
            System.out.println("Transaction History (" + history.size() + " transaction(s)):");
            System.out.println("-------------------------------------------");
            for (Transaction transaction : history) {
                System.out.println(transaction);
            }
            System.out.println("-------------------------------------------");
        }
    }

    /**
     * View overdue books
     */
    private static void viewOverdueBooks() {
        System.out.println("\n--- Overdue Books ---");
        
        List<Book> overdueBooks = library.getOverdueBooks();
        
        if (overdueBooks.isEmpty()) {
            System.out.println("No overdue books.");
        } else {
            System.out.println("\nOverdue Books (" + overdueBooks.size() + " book(s)):");
            System.out.println("-------------------------------------------");
            for (Book book : overdueBooks) {
                System.out.println(book);
            }
            System.out.println("-------------------------------------------");
        }
    }
}
