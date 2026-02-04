# Library Management System

A comprehensive library management system built with pure Java (no external frameworks or dependencies).

## Project Overview
This application provides a console-based interface for managing library operations including book management, member registration, and borrowing/returning books. It demonstrates object-oriented programming principles and uses Java standard libraries exclusively.

## Features
- 📚 **Book Management**: Add, remove, and search books by title or author
- 👤 **Member Management**: Register and manage library members
- 📖 **Borrowing System**: Borrow and return books with automatic due date tracking (14 days)
- 🔍 **Search Functionality**: Partial match search for books by title or author
- 📊 **Transaction History**: Track all borrowing and return activities
- ⏰ **Overdue Tracking**: Automatically identify overdue books
- ✅ **Input Validation**: Validate ISBN, email, and member ID formats
- 📋 **Sample Data**: Pre-loaded sample data for testing

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
│               │   └── Transaction.java     # Transaction entity
│               ├── service/
│               │   └── Library.java         # Core library business logic
│               └── util/
│                   ├── DateUtils.java       # Date utility methods
│                   └── ValidationUtils.java # Input validation utilities
└── README.md
```

## Technical Specifications
- **Language**: Pure Java (Java 8 or higher)
- **Dependencies**: None (uses only Java standard library)
- **Date/Time API**: java.time (LocalDate)
- **Collections**: HashMap, ArrayList
- **Architecture**: Layered (Model, Service, Utility)

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command line terminal

### Compilation
Navigate to the project root directory and compile all Java files:

```bash
# Navigate to project root
cd /path/to/libraryApp-Java

# Compile all Java files
javac -d bin src/main/java/com/library/*.java src/main/java/com/library/*/*.java

# Or compile individually
javac -d bin -sourcepath src/main/java src/main/java/com/library/LibraryApp.java
```

### Running the Application
After compilation, run the main application:

```bash
# From project root
java -cp bin com.library.LibraryApp

# Or if you compiled to the default location
java -cp src/main/java com.library.LibraryApp
```

Alternatively, compile and run in one step:
```bash
# Navigate to source directory
cd src/main/java

# Compile and run
javac com/library/*.java com/library/*/*.java
java com.library.LibraryApp
```

## Usage

### Main Menu Options
When you run the application, you'll see a menu with the following options:

1. **Add a new book** - Add a book with ISBN, title, author, and publication year
2. **Remove a book** - Remove a book by ISBN (only if not borrowed)
3. **Register a new member** - Register a member with ID, name, and email
4. **Remove a member** - Remove a member by ID (only if no borrowed books)
5. **Borrow a book** - Process a book borrowing transaction
6. **Return a book** - Process a book return transaction
7. **Search books by title** - Find books using partial title match
8. **Search books by author** - Find books using partial author match
9. **View all available books** - List only available books
10. **View all books** - List all books with their status
11. **View all members** - List all registered members
12. **View member borrowing history** - See all transactions for a member
13. **View overdue books** - List books that are overdue (>14 days)
14. **Load sample data** - Load pre-configured sample data for testing
15. **Exit** - Exit the application

### Example Usage

#### Adding a Book
```
Enter your choice: 1
--- Add a New Book ---
Enter ISBN (10 or 13 digits): 9780134685991
Enter title: Effective Java
Enter author: Joshua Bloch
Enter publication year: 2018
Book added successfully!
```

#### Registering a Member
```
Enter your choice: 3
--- Register a New Member ---
Enter member ID (3-20 alphanumeric characters): MEM001
Enter name: Alice Johnson
Enter email: alice@example.com
Member registered successfully!
```

#### Borrowing a Book
```
Enter your choice: 5
--- Borrow a Book ---
Enter member ID: MEM001
Enter ISBN: 9780134685991
Book borrowed successfully. Due date: 2026-02-18
```

#### Searching for Books
```
Enter your choice: 7
--- Search Books by Title ---
Enter title (partial match): java
Search Results:
========================================
ISBN: 9780134685991 | Title: Effective Java | Author: Joshua Bloch | Year: 2018 | Status: Borrowed by MEM001
========================================
Total results: 1
```

## Validation Rules

### ISBN Format
- Must be exactly 10 or 13 digits
- Examples: `1234567890` or `9781234567890`

### Email Format
- Standard email format validation
- Example: `user@example.com`

### Member ID Format
- Alphanumeric characters only
- Length: 3-20 characters
- Example: `MEM001`, `USER123`

## Features in Detail

### Book Management
- Each book has a unique ISBN
- Books track their availability status
- Borrowed books store the borrower's member ID
- Books can only be removed if they are not currently borrowed

### Member Management
- Each member has a unique member ID
- Members track their borrowed books
- Registration date is automatically recorded
- Members can only be removed if they have no borrowed books

### Transaction System
- Every borrow/return action creates a transaction record
- Borrow transactions automatically set a due date (14 days)
- Transaction history is maintained for all operations
- Unique transaction ID generated for each transaction

### Overdue Detection
- Books are considered overdue if kept beyond 14 days
- Overdue books can be listed with the borrower information
- Due dates are automatically calculated on borrowing

## Sample Data
The application includes a "Load sample data" option that populates:
- 5 sample books (Java programming books)
- 3 sample members
- 2 sample borrowing transactions

This is useful for testing without manually entering data.

## Error Handling
The application includes comprehensive error handling:
- Invalid input format detection
- Duplicate ISBN/Member ID prevention
- Validation for all user inputs
- Meaningful error messages
- Graceful exception handling

## Code Quality
- Clear separation of concerns (Model, Service, Utility layers)
- Proper encapsulation with getters/setters
- Meaningful variable and method names
- JavaDoc comments for all public methods
- Input validation at multiple levels
- Defensive programming practices

## Future Enhancements
Potential improvements for future versions:
- Persistent storage (file or database)
- Fine calculation for overdue books
- Book categories/genres
- Member borrowing limits
- Book reservation system
- Advanced search filters
- Export reports to files
- Multi-copy book support

## License
This project is open source and available for educational purposes.

## Author
Created as a demonstration of pure Java console application development.