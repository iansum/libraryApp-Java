package com.library;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import com.library.service.Library;
import com.library.util.ValidationUtils;

import java.util.List;
import java.util.Scanner;

/**
 * Main application class with console-based menu system.
 */
public class LibraryApp {
    private Library library;
    private Scanner scanner;

    public LibraryApp() {
        this.library = new Library();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display the main menu.
     */
    private void displayMenu() {
        System.out.println("\n========================================");
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
        System.out.println("14. Load sample data");
        System.out.println("15. Exit");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
    }

    /**
     * Add a new book.
     */
    private void addBook() {
        System.out.println("\n--- Add a New Book ---");
        
        System.out.print("Enter ISBN (10 or 13 digits): ");
        String isbn = scanner.nextLine().trim();
        
        if (!ValidationUtils.isValidISBN(isbn)) {
            System.out.println("Invalid ISBN format. Please enter 10 or 13 digits.");
            return;
        }

        System.out.print("Enter title: ");
        String title = scanner.nextLine().trim();
        
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        System.out.print("Enter author: ");
        String author = scanner.nextLine().trim();
        
        if (author.isEmpty()) {
            System.out.println("Author cannot be empty.");
            return;
        }

        System.out.print("Enter publication year: ");
        int year;
        try {
            year = Integer.parseInt(scanner.nextLine().trim());
            if (year < 1000 || year > 2100) {
                System.out.println("Invalid year. Please enter a year between 1000 and 2100.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid year format. Please enter a number.");
            return;
        }

        Book book = new Book(isbn, title, author, year);
        if (library.addBook(book)) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Failed to add book.");
        }
    }

    /**
     * Remove a book.
     */
    private void removeBook() {
        System.out.println("\n--- Remove a Book ---");
        
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();

        if (library.removeBook(isbn)) {
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("Failed to remove book. Book may not exist or is currently borrowed.");
        }
    }

    /**
     * Register a new member.
     */
    private void registerMember() {
        System.out.println("\n--- Register a New Member ---");
        
        System.out.print("Enter member ID (3-20 alphanumeric characters): ");
        String memberId = scanner.nextLine().trim();
        
        if (!ValidationUtils.isValidMemberId(memberId)) {
            System.out.println("Invalid member ID format.");
            return;
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        
        if (!ValidationUtils.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }

        Member member = new Member(memberId, name, email);
        if (library.registerMember(member)) {
            System.out.println("Member registered successfully!");
        } else {
            System.out.println("Failed to register member.");
        }
    }

    /**
     * Remove a member.
     */
    private void removeMember() {
        System.out.println("\n--- Remove a Member ---");
        
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().trim();

        if (library.removeMember(memberId)) {
            System.out.println("Member removed successfully!");
        } else {
            System.out.println("Failed to remove member. Member may not exist or has borrowed books.");
        }
    }

    /**
     * Borrow a book.
     */
    private void borrowBook() {
        System.out.println("\n--- Borrow a Book ---");
        
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().trim();

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();

        library.borrowBook(memberId, isbn);
    }

    /**
     * Return a book.
     */
    private void returnBook() {
        System.out.println("\n--- Return a Book ---");
        
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().trim();

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();

        library.returnBook(memberId, isbn);
    }

    /**
     * Search books by title.
     */
    private void searchByTitle() {
        System.out.println("\n--- Search Books by Title ---");
        
        System.out.print("Enter title (partial match): ");
        String title = scanner.nextLine().trim();

        List<Book> results = library.searchBooksByTitle(title);
        
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("\nSearch Results:");
            System.out.println("========================================");
            for (Book book : results) {
                System.out.println(book);
            }
            System.out.println("========================================");
            System.out.println("Total results: " + results.size());
        }
    }

    /**
     * Search books by author.
     */
    private void searchByAuthor() {
        System.out.println("\n--- Search Books by Author ---");
        
        System.out.print("Enter author (partial match): ");
        String author = scanner.nextLine().trim();

        List<Book> results = library.searchBooksByAuthor(author);
        
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("\nSearch Results:");
            System.out.println("========================================");
            for (Book book : results) {
                System.out.println(book);
            }
            System.out.println("========================================");
            System.out.println("Total results: " + results.size());
        }
    }

    /**
     * View all available books.
     */
    private void viewAvailableBooks() {
        System.out.println("\n--- Available Books ---");
        
        List<Book> availableBooks = library.getAvailableBooks();
        
        if (availableBooks.isEmpty()) {
            System.out.println("No available books.");
        } else {
            System.out.println("========================================");
            for (Book book : availableBooks) {
                System.out.println(book);
            }
            System.out.println("========================================");
            System.out.println("Total available books: " + availableBooks.size());
        }
    }

    /**
     * View member borrowing history.
     */
    private void viewMemberHistory() {
        System.out.println("\n--- Member Borrowing History ---");
        
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().trim();

        Member member = library.getMember(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        System.out.println("\nMember: " + member.getName());
        List<Transaction> history = library.getMemberBorrowHistory(memberId);
        
        if (history.isEmpty()) {
            System.out.println("No transaction history.");
        } else {
            System.out.println("Transaction History:");
            System.out.println("========================================");
            for (Transaction transaction : history) {
                System.out.println(transaction);
            }
            System.out.println("========================================");
            System.out.println("Total transactions: " + history.size());
        }
    }

    /**
     * View overdue books.
     */
    private void viewOverdueBooks() {
        System.out.println("\n--- Overdue Books ---");
        
        List<Book> overdueBooks = library.getOverdueBooks();
        
        if (overdueBooks.isEmpty()) {
            System.out.println("No overdue books.");
        } else {
            System.out.println("========================================");
            for (Book book : overdueBooks) {
                System.out.println(book);
            }
            System.out.println("========================================");
            System.out.println("Total overdue books: " + overdueBooks.size());
        }
    }

    /**
     * Run the application.
     */
    public void run() {
        System.out.println("\nWelcome to the Library Management System!");
        System.out.println("=========================================\n");

        boolean running = true;
        while (running) {
            displayMenu();
            
            String choice = scanner.nextLine().trim();
            
            try {
                switch (choice) {
                    case "1":
                        addBook();
                        break;
                    case "2":
                        removeBook();
                        break;
                    case "3":
                        registerMember();
                        break;
                    case "4":
                        removeMember();
                        break;
                    case "5":
                        borrowBook();
                        break;
                    case "6":
                        returnBook();
                        break;
                    case "7":
                        searchByTitle();
                        break;
                    case "8":
                        searchByAuthor();
                        break;
                    case "9":
                        viewAvailableBooks();
                        break;
                    case "10":
                        library.displayAllBooks();
                        break;
                    case "11":
                        library.displayAllMembers();
                        break;
                    case "12":
                        viewMemberHistory();
                        break;
                    case "13":
                        viewOverdueBooks();
                        break;
                    case "14":
                        library.loadSampleData();
                        break;
                    case "15":
                        running = false;
                        System.out.println("\nThank you for using the Library Management System!");
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 15.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
        
        scanner.close();
    }

    /**
     * Main entry point.
     */
    public static void main(String[] args) {
        LibraryApp app = new LibraryApp();
        app.run();
    }
}
