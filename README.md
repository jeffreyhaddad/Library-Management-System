# DVD_MANANGER
A command-line library management system built with Java that handles Books, DVDs, and different types of members (Students and Civilians). Created as a coursework project to demonstrate object-oriented programming principles.

## Features
- Item Management: Add, modify, delete, and search for Books and DVDs
- Member Management: Handle two types of members with different borrowing rules:
  - Students: Free borrowing up to 3 items simultaneously
  - Civilians: Pay-per-borrow system with balance tracking
- Borrowing System: Complete borrow and return workflow with validation
- AData Persistence: Automatic save/load of all data between sessions
- Search Functionality: Find items by serial number or title, members by ID or name

## Requirements
- Java JDK 8 or later
- Any Java IDE (optional): NetBeans, IntelliJ, VS Code

## Setup & Run
1. Compile
```bash
javac *.java
```

2. Run
```bash
java Main
```

3. Clean up (after you're done)
```bash
rm *.class
```

## Data Storage
The program uses two text files:

`members.txt` format:
```
Students:    S&name&address&gender&age&phone&studentId[&borrowed1##borrowed2]
Civilians:   C&name&address&gender&age&phone&id&balance[&borrowed1##borrowed2]
```

`items.txt` format:
```
Books:  B#serial#title#author#publisher#status#genre#date#pages[#owner1&&owner2]
DVDs:   D#serial#title#director#producer#status#genre#date#sizeMB[#owner1&&owner2]
```

Status codes: 'a' (available), 'o' (on loan), 'r' (reserved)

## Project Structure
├── LibraryItem.java       # Base class for all library items
│   ├── Book.java          # Book-specific implementation
│   └── DVD.java           # DVD-specific implementation
├── Person.java            # Base class for library members
│   ├── Student.java       # Student member with borrowing limits
│   └── Civilian.java      # Civilian member with balance system
├── Main.java              # Main application with menu system
├── members.txt            # Persistent storage for members
└── items.txt              # Persistent storage for items


## Quick Start Guide
1. Start: `java Main`
2. Menu options:
   - 1-3: Manage items (add/edit/delete)
   - 4-6: Manage members (add/edit/delete)
   - 7-8: Borrow/return items
   - 9-10: Search items/members
   - 11-12: List everything
   - 0: Save and exit

## What I Learned
This project helped reinforce several key programming concepts:

### Object-Oriented Programming (OOP)
- **Inheritance**: Created class hierarchies (`LibraryItem` → `Book`/`DVD`, `Person` → `Student`/`Civilian`) to share common attributes and behavior
- **Encapsulation**: Used private fields with getter/setter methods to control access to object state
- **Polymorphism**: Treated specialized classes through their base class interfaces, making the code more flexible and reusable

### Design Patterns & Best Practices
- **Single Responsibility**: Each class has a focused purpose (e.g., `Book` handles book-specific logic)
- **DRY (Don't Repeat Yourself)**: Common functionality placed in base classes
- **Data Validation**: Input checking in setters (age limits, phone format, etc.)
- **Documentation**: Javadoc comments make the code self-documenting

### File I/O & Data Management
- Designed a simple but effective text-based storage format
- Handled data persistence across program runs
- Managed relationships between objects (borrower-item connections)