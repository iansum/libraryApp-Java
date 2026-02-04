# Library Management System - Java

A comprehensive library management system built with pure Java (no external dependencies or frameworks). This console-based application provides full library operations including book management, member registration, borrowing/returning books, and transaction tracking.

## Features

### Core Functionality
- **Book Management**: Add, remove, search, and view books
- **Member Management**: Register, remove, and view members
- **Borrowing System**: Borrow and return books with automatic due date calculation (14 days)
- **Search Capabilities**: Search books by title or author (partial matching supported)
- **Transaction Tracking**: Complete history of all borrow/return transactions
- **Overdue Detection**: Automatic identification of overdue books (>14 days)
- **Input Validation**: Comprehensive validation for ISBN, email, and member ID formats

### Technical Features
- Pure Java implementation (Java 8+)
- Object-oriented design with clear separation of concerns
- Console-based user interface with interactive menu
- In-memory data storage using Java Collections (HashMap, ArrayList)
- Date/time operations using java.util package
- Regular expression-based validation

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── library/
│               ├── LibraryApp.java              # Main entry point with menu system
│               ├── model/
│               │   ├── Book.java                # Book entity class
│               │   ├── Member.java              # Member entity class
│               │   └── Transaction.java         # Transaction tracking class
│               ├── service/
│               │   └── Library.java             # Core library operations
│               └── util/
│                   ├── DateUtils.java           # Date utility functions
│                   └── ValidationUtils.java     # Input validation functions
└── README.md
```

## How to Compile and Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Terminal/Command prompt

### Compilation

Navigate to the project root directory and compile all Java files:

```bash
# From the project root directory
javac -d bin src/main/java/com/library/*.java src/main/java/com/library/model/*.java src/main/java/com/library/service/*.java src/main/java/com/library/util/*.java
```

Or compile and run in one step:

```bash
# Compile from project root
javac -d bin src/main/java/com/library/**/*.java src/main/java/com/library/*.java

# Run the application
java -cp bin com.library.LibraryApp
```

### Running the Application

After compilation, run the application:

```bash
java -cp bin com.library.LibraryApp
```

### Quick Start (Simplified)

If you're in the `src/main/java` directory:

```bash
# Compile
javac com/library/LibraryApp.java com/library/model/*.java com/library/service/*.java com/library/util/*.java

# Run
java com.library.LibraryApp
```

## Usage Examples

### Main Menu
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

The application includes pre-loaded sample data:

**Books:**
- Clean Code by Robert C. Martin (ISBN: 9780132350884)
- Design Patterns by Gang of Four (ISBN: 9780201633610)
- Effective Java by Joshua Bloch (ISBN: 9780134685991)
- Java 8 in Action by Raoul-Gabriel Urma (ISBN: 9781617294945)
- Head First Java by Kathy Sierra (ISBN: 9780596009205)

**Members:**
- John Doe (M001)
- Jane Smith (M002)
- Bob Johnson (M003)

### Example Operations

#### Adding a Book
```
Enter your choice: 1

--- Add New Book ---
Enter ISBN (10 or 13 digits): 9781234567890
Enter title: Introduction to Algorithms
Enter author: Thomas H. Cormen
Enter publication year: 2009
Success: Book added successfully!
```

#### Borrowing a Book
```
Enter your choice: 5

--- Borrow Book ---
Enter Member ID: M001
Enter ISBN: 9780132350884
Success: Book borrowed successfully!
Due date: 2024-01-15 10:30:45
```

#### Searching Books
```
Enter your choice: 7

--- Search Books by Title ---
Enter title (or partial title): Java

Search Results (3 book(s) found):
-------------------------------------------
ISBN: 9780134685991 | Title: Effective Java | Author: Joshua Bloch | Year: 2017 | Available: Yes
ISBN: 9781617294945 | Title: Java 8 in Action | Author: Raoul-Gabriel Urma | Year: 2015 | Available: Yes
ISBN: 9780596009205 | Title: Head First Java | Author: Kathy Sierra | Year: 2005 | Available: Yes
-------------------------------------------
```

## Validation Rules

### ISBN Format
- Must be exactly 10 or 13 digits
- Examples: `1234567890` or `9781234567890`

### Email Format
- Must follow standard email format
- Example: `user@example.com`

### Member ID Format
- Must start with 'M' followed by at least 3 digits
- Examples: `M001`, `M123`, `M9999`

## Key Classes and Methods

### Book Class
- `borrow(String memberId)`: Mark book as borrowed
- `returnBook()`: Mark book as returned
- Tracks availability and borrower information

### Member Class
- `borrowBook(String isbn)`: Add book to member's borrowed list
- `returnBook(String isbn)`: Remove book from member's borrowed list
- `getBorrowedBooksCount()`: Get count of currently borrowed books

### Library Class
- `addBook(Book book)`: Add new book to library
- `removeBook(String isbn)`: Remove book from library
- `registerMember(Member member)`: Register new member
- `borrowBook(String memberId, String isbn)`: Process book borrowing
- `returnBook(String memberId, String isbn)`: Process book return
- `searchBooksByTitle(String title)`: Search books by title
- `searchBooksByAuthor(String author)`: Search books by author
- `getAvailableBooks()`: Get all available books
- `getOverdueBooks()`: Get books overdue (>14 days)

### DateUtils Class
- `getCurrentDate()`: Get current date
- `addDays(Date date, int days)`: Add days to date
- `calculateDaysBetween(Date start, Date end)`: Calculate date difference
- `formatDate(Date date)`: Format date for display

### ValidationUtils Class
- `isValidISBN(String isbn)`: Validate ISBN format
- `isValidEmail(String email)`: Validate email format
- `isValidMemberId(String memberId)`: Validate member ID format

## Design Principles

- **Single Responsibility**: Each class has a single, well-defined purpose
- **Encapsulation**: Data is protected with appropriate access modifiers
- **Separation of Concerns**: Model, Service, and Utility layers are clearly separated
- **Input Validation**: All user inputs are validated before processing
- **Error Handling**: Comprehensive error messages guide users

## Future Enhancements

Possible improvements for future versions:
- Persistent storage (file-based or database)
- Late fee calculation for overdue books
- Book reservation system
- Multiple copies of the same book
- Advanced reporting and statistics
- Export transaction history to CSV/PDF

## License

This is an educational project demonstrating Java programming concepts.

## Author

Library Management System - Pure Java Implementation