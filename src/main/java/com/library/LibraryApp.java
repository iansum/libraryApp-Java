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
        
        // Load sample data
        library.loadSampleData();
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
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
                        System.out.println("\nThank you for using the Library Management System!");
                        running = false;
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please enter a number between 1 and 14.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a number.");
            } catch (Exception e) {
                System.out.println("\nAn error occurred: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
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
        System.out.println("14. Exit");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
    }
    
    private static void addNewBook() {
        System.out.println("\n--- Add a New Book ---");
        
        System.out.print("Enter ISBN (10 or 13 digits): ");
        String isbn = scanner.nextLine().trim();
        
        if (!ValidationUtils.isValidISBN(isbn)) {
            System.out.println("Error: Invalid ISBN format. Must be 10 or 13 digits.");
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
                System.out.println("Error: Invalid year.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid year format.");
            return;
        }
        
        Book book = new Book(isbn, title, author, year);
        if (library.addBook(book)) {
            System.out.println("Success: Book added successfully!");
        } else {
            System.out.println("Error: Failed to add book.");
        }
    }
    
    private static void removeBook() {
        System.out.println("\n--- Remove a Book ---");
        
        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine().trim();
        
        if (library.removeBook(isbn)) {
            System.out.println("Success: Book removed successfully!");
        } else {
            System.out.println("Error: Failed to remove book.");
        }
    }
    
    private static void registerNewMember() {
        System.out.println("\n--- Register a New Member ---");
        
        System.out.print("Enter Member ID (format: M####): ");
        String memberId = scanner.nextLine().trim();
        
        if (!ValidationUtils.isValidMemberId(memberId)) {
            System.out.println("Error: Invalid Member ID format. Must be M followed by 4 digits (e.g., M0001).");
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
        
        Member member = new Member(memberId, name, email);
        if (library.registerMember(member)) {
            System.out.println("Success: Member registered successfully!");
        } else {
            System.out.println("Error: Failed to register member.");
        }
    }
    
    private static void removeMember() {
        System.out.println("\n--- Remove a Member ---");
        
        System.out.print("Enter Member ID to remove: ");
        String memberId = scanner.nextLine().trim();
        
        if (library.removeMember(memberId)) {
            System.out.println("Success: Member removed successfully!");
        } else {
            System.out.println("Error: Failed to remove member.");
        }
    }
    
    private static void borrowBook() {
        System.out.println("\n--- Borrow a Book ---");
        
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();
        
        if (library.borrowBook(memberId, isbn)) {
            System.out.println("Success: Book borrowed successfully!");
            System.out.println("Due date: " + DateUtils.formatDate(DateUtils.addDays(DateUtils.getCurrentDate(), 14)));
        } else {
            System.out.println("Error: Failed to borrow book.");
        }
    }
    
    private static void returnBook() {
        System.out.println("\n--- Return a Book ---");
        
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();
        
        if (library.returnBook(memberId, isbn)) {
            System.out.println("Success: Book returned successfully!");
        } else {
            System.out.println("Error: Failed to return book.");
        }
    }
    
    private static void searchBooksByTitle() {
        System.out.println("\n--- Search Books by Title ---");
        
        System.out.print("Enter title (partial match): ");
        String title = scanner.nextLine().trim();
        
        List<Book> results = library.searchBooksByTitle(title);
        
        if (results.isEmpty()) {
            System.out.println("No books found matching the title.");
        } else {
            System.out.println("\nSearch Results (" + results.size() + " found):");
            System.out.println("----------------------------------------");
            for (Book book : results) {
                System.out.println(book);
            }
        }
    }
    
    private static void searchBooksByAuthor() {
        System.out.println("\n--- Search Books by Author ---");
        
        System.out.print("Enter author name (partial match): ");
        String author = scanner.nextLine().trim();
        
        List<Book> results = library.searchBooksByAuthor(author);
        
        if (results.isEmpty()) {
            System.out.println("No books found by this author.");
        } else {
            System.out.println("\nSearch Results (" + results.size() + " found):");
            System.out.println("----------------------------------------");
            for (Book book : results) {
                System.out.println(book);
            }
        }
    }
    
    private static void viewAvailableBooks() {
        System.out.println("\n--- Available Books ---");
        
        List<Book> availableBooks = library.getAvailableBooks();
        
        if (availableBooks.isEmpty()) {
            System.out.println("No books currently available.");
        } else {
            System.out.println("Available Books (" + availableBooks.size() + "):");
            System.out.println("----------------------------------------");
            for (Book book : availableBooks) {
                System.out.println(book);
            }
        }
    }
    
    private static void viewAllBooks() {
        library.displayAllBooks();
    }
    
    private static void viewAllMembers() {
        library.displayAllMembers();
    }
    
    private static void viewMemberBorrowHistory() {
        System.out.println("\n--- Member Borrowing History ---");
        
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        
        Member member = library.getMember(memberId);
        if (member == null) {
            System.out.println("Error: Member not found.");
            return;
        }
        
        List<Transaction> history = library.getMemberBorrowHistory(memberId);
        
        if (history.isEmpty()) {
            System.out.println("No borrowing history found for this member.");
        } else {
            System.out.println("\nBorrowing History for " + member.getName() + " (" + memberId + "):");
            System.out.println("----------------------------------------");
            for (Transaction transaction : history) {
                System.out.println(transaction);
            }
        }
    }
    
    private static void viewOverdueBooks() {
        System.out.println("\n--- Overdue Books ---");
        
        List<Book> overdueBooks = library.getOverdueBooks();
        
        if (overdueBooks.isEmpty()) {
            System.out.println("No overdue books.");
        } else {
            System.out.println("Overdue Books (" + overdueBooks.size() + "):");
            System.out.println("----------------------------------------");
            for (Book book : overdueBooks) {
                System.out.println(book);
            }
        }
    }
}
