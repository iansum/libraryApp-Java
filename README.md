# Library Management System - Java

A comprehensive library management system built with pure Java, featuring a console-based interface for managing books, members, and transactions.

## Features

### Core Functionality
- **Book Management**: Add, remove, search, and view books
- **Member Management**: Register and manage library members
- **Transaction Tracking**: Complete borrowing and return history
- **Search Capabilities**: Search books by title or author with partial matching
- **Overdue Detection**: Automatically identify books that are overdue (>14 days)
- **Validation**: Built-in input validation for ISBN, email, and member IDs

### Book Operations
- Add new books with ISBN, title, author, and publication year
- Remove books (only if not currently borrowed)
- Search books by title or author
- View all books or only available books
- Track book availability status

### Member Operations
- Register new members with unique IDs
- Remove members (only if they have no borrowed books)
- View member borrowing history
- Track number of books borrowed by each member

### Transaction Management
- Record all borrow and return transactions
- Calculate due dates (14 days from borrowing)
- View overdue books
- Complete transaction history for audit purposes

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── library/
│               ├── LibraryApp.java          # Main entry point with console menu
│               ├── model/
│               │   ├── Book.java            # Book entity
│               │   ├── Member.java          # Member entity
│               │   └── Transaction.java     # Transaction entity with enum
│               ├── service/
│               │   └── Library.java         # Core library service logic
│               └── util/
│                   ├── DateUtils.java       # Date manipulation utilities
│                   └── ValidationUtils.java # Input validation utilities
└── README.md
```

## Requirements

- Java 8 or higher
- No external dependencies required (pure Java)

## Compilation

To compile the application, navigate to the project root directory and run:

```bash
javac -d out src/main/java/com/library/*.java src/main/java/com/library/**/*.java
```

Or compile from the src/main/java directory:

```bash
cd src/main/java
javac com/library/*.java com/library/**/*.java
```

## Running the Application

After compilation, run the application:

```bash
# From project root (if compiled with -d out)
java -cp out com.library.LibraryApp

# From src/main/java directory (if compiled without -d)
java com.library.LibraryApp
```

## Usage

### Main Menu

When you start the application, you'll see the following menu:

```
========================================
    LIBRARY MANAGEMENT SYSTEM
========================================
1.  Add a new book
2.  Remove a book
3.  Register a new member
4.  Remove a member
5.  Borrow a book
6.  Return a book
7.  Search books by title
8.  Search books by author
9.  View all available books
10. View all books
11. View all members
12. View member borrowing history
13. View overdue books
14. Exit
========================================
Enter your choice:
```

### Sample Data

The application comes pre-loaded with sample data:

**Books:**
- Effective Java by Joshua Bloch (ISBN: 9780134685991)
- Clean Code by Robert C. Martin (ISBN: 9780132350884)
- Head First Design Patterns by Eric Freeman (ISBN: 9780596009205)
- The Pragmatic Programmer by David Thomas (ISBN: 9780135957059)
- Design Patterns by Gang of Four (ISBN: 9780201633610)

**Members:**
- M0001: John Doe (john.doe@email.com)
- M0002: Jane Smith (jane.smith@email.com)
- M0003: Bob Johnson (bob.johnson@email.com)

### Example Operations

#### Adding a New Book
```
Enter your choice: 1
--- Add a New Book ---
Enter ISBN (10 or 13 digits): 9781234567890
Enter title: Learning Java
Enter author: John Smith
Enter publication year: 2023
Success: Book added successfully!
```

#### Borrowing a Book
```
Enter your choice: 5
--- Borrow a Book ---
Enter Member ID: M0001
Enter ISBN: 9780134685991
Success: Book borrowed successfully!
Due date: 2026-02-18 10:02:08
```

#### Searching for Books
```
Enter your choice: 7
--- Search Books by Title ---
Enter title (partial match): Java
Search Results (2 found):
----------------------------------------
ISBN: 9780134685991 | Title: Effective Java | Author: Joshua Bloch | Year: 2018 | Available: No (Borrowed by: M0001)
ISBN: 9781234567890 | Title: Learning Java | Author: John Smith | Year: 2023 | Available: Yes
```

## Validation Rules

### ISBN Format
- Must be exactly 10 or 13 digits
- Examples: `9780134685991`, `0134685997`

### Member ID Format
- Must start with 'M' followed by exactly 4 digits
- Examples: `M0001`, `M1234`

### Email Format
- Must be a valid email address
- Examples: `user@example.com`, `john.doe@library.org`

## Business Rules

- Books can only be removed if they are not currently borrowed
- Members can only be removed if they have no borrowed books
- Books are due 14 days after borrowing
- Each book can only be borrowed by one member at a time
- Members can borrow multiple books simultaneously

## Error Handling

The application includes comprehensive error handling:
- Input validation for all user inputs
- Clear error messages for invalid operations
- Prevention of duplicate ISBNs and Member IDs
- Protection against invalid state changes

## Technical Details

### Date Management
- Uses `java.util.Date` for date operations
- Due dates are automatically calculated as 14 days from borrowing
- Overdue detection compares current date with due dates

### Data Storage
- In-memory storage using Java collections
- `HashMap` for efficient book and member lookup by ID
- `ArrayList` for transaction history

### Transaction Types
- `BORROW`: Records when a book is borrowed
- `RETURN`: Records when a book is returned

## Code Quality

The codebase follows Java best practices:
- Proper encapsulation with getters and setters
- Input validation using utility classes
- Clean separation of concerns (Model, Service, Utility)
- Comprehensive javadoc comments
- Meaningful variable and method names
- Exception handling throughout

## Future Enhancements

Potential improvements could include:
- Persistent storage (file or database)
- Fine calculation for overdue books
- Book reservation system
- Multiple copies of the same book
- User authentication
- Export reports to file
- GUI interface

## License

This project is created for educational purposes.

## Author

Created as a demonstration of object-oriented programming principles in Java.