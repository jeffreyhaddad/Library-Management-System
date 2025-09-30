import java.util.*;
import java.io.*;

/**
 * Main application class for the Library Management System.
 * Provides functionality for managing library items and members,
 * including adding, modifying, and deleting records.
 * 
 * @author jeffreyhaddad
 */
public class Main {
    /** Scanner object for reading user input */
    public static Scanner input = new Scanner(System.in);
    
    // Menu choice constants for better readability
    private static final int ADD_ITEM = 1;
    private static final int MODIFY_ITEM = 2;
    private static final int DELETE_ITEM = 3;
    private static final int ADD_MEMBER = 4;
    private static final int MODIFY_MEMBER = 5;
    private static final int DELETE_MEMBER = 6;
    private static final int SEARCH_ITEM = 7;
    private static final int SEARCH_MEMBER = 8;
    private static final int BORROW_ITEM = 9;
    private static final int RETURN_ITEM = 10;
    private static final int DISPLAY_ITEMS = 11;
    private static final int DISPLAY_MEMBERS = 12;
    private static final int EXIT = 13;

    /**
     * The main entry point of the application.
     * Initializes the library system and provides an interactive
     * menu for managing library items and members.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        ArrayList<LibraryItem> myItems = new ArrayList<>();
        ArrayList<Person> myMembers = new ArrayList<>();

        try {
            LoadFromFiles(myMembers, myItems);
        } catch (FileNotFoundException e) {
            System.out.println("Data files not found. Starting with empty library.");
        }

        while (true) {
            System.out.println("\n\n*******************************\n\n");
            int choice = getChoice();

            switch (choice) {
                case ADD_ITEM:
                    addNewLibraryItem(myItems);
                    break;
                case MODIFY_ITEM:
                    modifyLibraryItem(myItems);
                    break;
                case DELETE_ITEM:
                    deleteLibraryItem(myItems);
                    break;
                case ADD_MEMBER:
                    addNewMember(myMembers);
                    break;
                case MODIFY_MEMBER:
                    modifyMemberInfo(myMembers);
                    break;
                case DELETE_MEMBER:
                    System.out.println();
                    System.out.println("Enter the ID or the name of the member to delete:");
                    String memberId = input.nextLine();
                    deleteMember(myMembers, memberId);
                    break;
                case SEARCH_ITEM:
                    searchItemMenu(myItems);
                    break;
                case SEARCH_MEMBER:
                    searchMemberMenu(myMembers);
                    break;
                case BORROW_ITEM:
                    borrowItem(myMembers, myItems);
                    break;
                case RETURN_ITEM:
                    returnLibraryItem(myMembers, myItems);
                    break;
                case DISPLAY_ITEMS:
                    displayAllItems(myItems);
                    break;
                case DISPLAY_MEMBERS:
                    displayAllMembers(myMembers);
                    break;
                case EXIT:
                    try {
                        SaveAllToFiles(myMembers, myItems);
                        System.out.println("Data saved successfully. Exiting...");
                    } catch (Exception e) {
                        System.out.println("Error saving data: " + e.getMessage());
                    }
                    return;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }

    /**
     * Handles the search item menu logic with better error handling.
     */
    private static void searchItemMenu(ArrayList<LibraryItem> myItems) {
        System.out.println();
        System.out.println("Enter the serial number or name of the item to search: ");
        String searchInput = input.nextLine();

        try {
            long searchSerialNumber = Long.parseLong(searchInput);
            LibraryItem foundItem = searchItemBySerialNb(searchSerialNumber, myItems);
            if (foundItem != null) {
                System.out.println("Item found:");
                displayItemType(foundItem);
                System.out.println(foundItem);
                System.out.println("Availability: " + (foundItem.getStatus() == 'a' ? "Available" : "Not Available"));
            } else {
                System.out.println("Item with serial number " + searchSerialNumber + " not found.");
            }
        } catch (NumberFormatException e) {
            ArrayList<LibraryItem> foundItems = searchItemByName(searchInput, myItems);
            if (!foundItems.isEmpty()) {
                System.out.println("Items found:");
                for (LibraryItem item : foundItems) {
                    displayItemType(item);
                    System.out.println(item);
                    System.out.println("Availability: " + (item.getStatus() == 'a' ? "Available" : "Not Available"));
                    System.out.println();
                }
            } else {
                System.out.println("No items found with the name '" + searchInput + "'.");
            }
        }
    }

    /**
     * Handles the search member menu logic.
     */
    private static void searchMemberMenu(ArrayList<Person> myMembers) {
        System.out.println();
        System.out.print("Enter the ID or name of the member to search: ");
        String searchMemberIdOrName = input.nextLine();

        Person foundMember = null;
        if (searchMemberIdOrName.matches("\\d+")) {
            foundMember = searchMemberById(searchMemberIdOrName, myMembers);
        } else {
            foundMember = searchMemberByName(searchMemberIdOrName, myMembers);
        }

        if (foundMember != null) {
            System.out.println("Member found:");
            System.out.println(foundMember);
        } else {
            System.out.println("Member with ID or name '" + searchMemberIdOrName + "' not found.");
        }
    }

    /**
     * Displays all items in the library.
     */
    private static void displayAllItems(ArrayList<LibraryItem> myItems) {
        if (myItems.isEmpty()) {
            System.out.println();
            System.out.println("No items available.");
        } else {
            System.out.println();
            System.out.println("Displaying all items:");
            System.out.println("---------------------");
            System.out.println();
            for (LibraryItem item : myItems) {
                displayItemType(item);
                System.out.println(item);
                System.out.println("-------");
                System.out.println();
            }
        }
    }

    /**
     * Displays all members in the library.
     */
    private static void displayAllMembers(ArrayList<Person> myMembers) {
        if (myMembers.isEmpty()) {
            System.out.println();
            System.out.println("No members available.");
        } else {
            System.out.println();
            System.out.println("Displaying all members:");
            System.out.println("----------------------");
            System.out.println();
            for (Person member : myMembers) {
                if (member instanceof Student) {
                    System.out.println("Student:");
                } else if (member instanceof Civilian) {
                    System.out.println("Civilian:");
                }
                System.out.println(member);
                System.out.println("-------");
                System.out.println();
            }
        }
    }

    /**
     * Helper method to display item type.
     */
    private static void displayItemType(LibraryItem item) {
        if (item instanceof Book) {
            System.out.println("Book:");
        } else if (item instanceof DVD) {
            System.out.println("DVD:");
        }
    }

    /**
     * Prompts the user and adds a new library item (Book or DVD) to the provided list.
     */
    public static void addNewLibraryItem(ArrayList<LibraryItem> items) {
        System.out.println();
        System.out.println("Adding a new Library Item...");
        System.out.println("---------------------------------");
        System.out.println("What is the type of the new item?");
        System.out.println("[B] Book or [D] DVD?");
        char itemType = input.nextLine().toUpperCase().charAt(0);

        try {
            switch (itemType) {
                case 'B':
                    addBook(items);
                    break;
                case 'D':
                    addDVD(items);
                    break;
                default:
                    System.out.println("Invalid item type! Must be 'B' or 'D'!");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error adding item: " + e.getMessage());
            input.nextLine(); // Clear buffer
        }
    }

    /**
     * Helper method to add a book.
     */
    private static void addBook(ArrayList<LibraryItem> items) {
        System.out.println("Enter the Book title:");
        String bookTitle = input.nextLine();
        System.out.println("Enter the author name:");
        String author = input.nextLine();
        System.out.println("Enter the publisher:");
        String publisher = input.nextLine();
        System.out.println("Enter the status [a] for available or [r] for reference:");
        char status = input.nextLine().toLowerCase().charAt(0);
        System.out.println("Enter the book's genre:");
        String genre = input.nextLine();
        System.out.println("Enter the number of pages:");
        int numberOfPages = input.nextInt();
        input.nextLine(); // Consume newline

        Book newBook = new Book(LibraryItem.generateSerialNumber(), bookTitle, author, 
                                publisher, status, genre, new Date(), numberOfPages);
        items.add(newBook);
        System.out.println("New Book added correctly.");
    }

    /**
     * Helper method to add a DVD.
     */
    private static void addDVD(ArrayList<LibraryItem> items) {
        System.out.println("Enter the DVD title:");
        String dvdTitle = input.nextLine();
        System.out.println("Enter the director's name:");
        String director = input.nextLine();
        System.out.println("Enter the producer:");
        String producer = input.nextLine();
        System.out.println("Enter the status [a] for available or [r] for reference:");
        char dvdStatus = input.nextLine().toLowerCase().charAt(0);
        System.out.println("Enter the DVD's genre:");
        String dvdGenre = input.nextLine();
        System.out.println("Enter the size of the DVD in MB:");
        double dvdSize = input.nextDouble();
        input.nextLine(); // Consume newline

        DVD newDVD = new DVD(LibraryItem.generateSerialNumber(), dvdTitle, director, 
                             producer, dvdStatus, dvdGenre, new Date(), dvdSize);
        items.add(newDVD);
        System.out.println("New DVD added correctly.");
    }

    /**
     * Prompts the user to select an existing library item and modify its fields.
     */
    public static void modifyLibraryItem(ArrayList<LibraryItem> items) {
        System.out.println();
        System.out.println("Modifying an existing Library Item...");
        System.out.print("Enter the name or serial number of the item to modify: ");
        String searchInput = input.nextLine();

        LibraryItem itemToModify = findItemByNameOrSerial(items, searchInput);

        if (itemToModify == null) {
            System.out.println("Item not found.");
            return;
        }

        System.out.println("Item details:");
        System.out.println(itemToModify);

        System.out.println("Choose which field to modify:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Publisher");
        System.out.println("4. Status");
        System.out.println("5. Genre");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = input.nextInt();
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the new title: ");
                    itemToModify.setTitle(input.nextLine());
                    System.out.println("Title updated successfully.");
                    break;
                case 2:
                    System.out.print("Enter the new author: ");
                    itemToModify.setAuthor(input.nextLine());
                    System.out.println("Author updated successfully.");
                    break;
                case 3:
                    System.out.print("Enter the new publisher: ");
                    itemToModify.setPublisher(input.nextLine());
                    System.out.println("Publisher updated successfully.");
                    break;
                case 4:
                    System.out.print("Enter the new status (a for available, r for reference): ");
                    itemToModify.setStatus(input.nextLine().charAt(0));
                    System.out.println("Status updated successfully.");
                    break;
                case 5:
                    System.out.print("Enter the new genre: ");
                    itemToModify.setGenre(input.nextLine());
                    System.out.println("Genre updated successfully.");
                    break;
                case 6:
                    System.out.println("Exiting modification.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Error modifying item: " + e.getMessage());
            input.nextLine(); // Clear buffer
        }
    }

    /**
     * Helper method to find item by name or serial number.
     */
    private static LibraryItem findItemByNameOrSerial(ArrayList<LibraryItem> items, String searchInput) {
        for (LibraryItem item : items) {
            if (String.valueOf(item.getSerialNumber()).equals(searchInput) || 
                item.getTitle().equalsIgnoreCase(searchInput)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Deletes a library item after confirmation.
     */
    public static void deleteLibraryItem(ArrayList<LibraryItem> items) {
        System.out.println();
        System.out.println("Deleting an existing Library Item...");
        System.out.print("Enter the serial number or name of the item to delete: ");
        String searchInput = input.nextLine();

        LibraryItem itemToDelete = findItemByNameOrSerial(items, searchInput);

        if (itemToDelete == null) {
            System.out.println("Item not found.");
            return;
        }

        System.out.println("Item details:");
        System.out.println(itemToDelete);
        System.out.print("Are you sure you want to delete this item? (yes/no): ");
        String confirmation = input.nextLine().toLowerCase();

        if (confirmation.equals("yes")) {
            items.remove(itemToDelete);
            System.out.println("Item deleted successfully.");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    /**
     * Prompts the user to add a new member (Civilian or Student).
     */
    public static void addNewMember(ArrayList<Person> myMembers) {
        System.out.println();
        System.out.println("Adding a new member...");
        System.out.println("Choose the type of member to add:");
        System.out.println("[c] for Civilian");
        System.out.println("[s] for Student");
        System.out.print("Enter your choice (c/s): ");
        char memberTypeChoice = Character.toLowerCase(input.nextLine().charAt(0));

        try {
            switch (memberTypeChoice) {
                case 'c':
                    addCivilian(myMembers);
                    break;
                case 's':
                    addStudent(myMembers);
                    break;
                default:
                    System.out.println("Invalid member type!");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error adding member: " + e.getMessage());
            input.nextLine(); // Clear buffer
        }
    }

    /**
     * Helper method to add a civilian.
     */
    private static void addCivilian(ArrayList<Person> myMembers) {
        System.out.println("Enter the civilian's ID:");
        String civilianID = input.nextLine();
        System.out.println("Enter the civilian's name:");
        String civilianName = input.nextLine();
        System.out.println("Enter the civilian's address:");
        String civilianAddress = input.nextLine();
        System.out.println("Enter the civilian's gender (M/F):");
        char civilianGender = Character.toUpperCase(input.nextLine().charAt(0));
        System.out.println("Enter the civilian's age:");
        int civilianAge = input.nextInt();
        input.nextLine();
        System.out.println("Enter the civilian's phone number:");
        String civilianPhoneNumber = input.nextLine();
        System.out.println("Enter the civilian's current balance:");
        double civilianCurrentBal = input.nextDouble();
        input.nextLine();

        Civilian newCivilian = new Civilian(civilianName, civilianAddress, civilianGender, 
                                           civilianAge, civilianPhoneNumber, civilianID, civilianCurrentBal);
        myMembers.add(newCivilian);
        System.out.println("New civilian added correctly.");
    }

    /**
     * Helper method to add a student.
     */
    private static void addStudent(ArrayList<Person> myMembers) {
        System.out.println("Enter the student's ID:");
        String studentID = input.nextLine();
        System.out.println("Enter the student's name:");
        String studentName = input.nextLine();
        System.out.println("Enter the student's address:");
        String studentAddress = input.nextLine();
        System.out.println("Enter the student's gender (M/F):");
        char studentGender = Character.toUpperCase(input.nextLine().charAt(0));
        System.out.println("Enter the student's age:");
        int studentAge = input.nextInt();
        input.nextLine();
        System.out.println("Enter the student's phone number:");
        String studentPhoneNumber = input.nextLine();

        Student newStudent = new Student(studentName, studentAddress, studentGender, 
                                        studentAge, studentPhoneNumber, studentID);
        myMembers.add(newStudent);
        System.out.println("New student added correctly.");
    }

    /**
     * Prompts the user to modify a member's information.
     */
    public static void modifyMemberInfo(ArrayList<Person> myMembers) {
        System.out.println();
        System.out.println("Modifying a member's information...");
        System.out.println("Enter the ID or name of the member whose information you want to modify:");
        String memberIdOrName = input.nextLine();

        Person memberToModify = null;
        if (memberIdOrName.matches("\\d+")) {
            memberToModify = searchMemberById(memberIdOrName, myMembers);
        } else {
            memberToModify = searchMemberByName(memberIdOrName, myMembers);
        }

        if (memberToModify == null) {
            System.out.println("Member with ID or name " + memberIdOrName + " not found.");
            return;
        }

        System.out.println("Member details:");
        System.out.println(memberToModify);

        System.out.println("Choose which field to modify:");
        System.out.println("1. Name");
        System.out.println("2. Address");
        System.out.println("3. Gender");
        System.out.println("4. Age");
        System.out.println("5. Phone Number");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter the new name: ");
                    memberToModify.setName(input.nextLine());
                    System.out.println("Name updated successfully.");
                    break;
                case 2:
                    System.out.print("Enter the new address: ");
                    memberToModify.setAddress(input.nextLine());
                    System.out.println("Address updated successfully.");
                    break;
                case 3:
                    System.out.print("Enter the new gender (M/F): ");
                    memberToModify.setGender(Character.toUpperCase(input.nextLine().charAt(0)));
                    System.out.println("Gender updated successfully.");
                    break;
                case 4:
                    System.out.print("Enter the new age: ");
                    memberToModify.setAge(input.nextInt());
                    System.out.println("Age updated successfully.");
                    break;
                case 5:
                    System.out.print("Enter the new phone number: ");
                    memberToModify.setPhoneNumber(input.nextLine());
                    System.out.println("Phone number updated successfully.");
                    break;
                case 6:
                    System.out.println("Exiting modification.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Error modifying member: " + e.getMessage());
            input.nextLine();
        }
    }

    /**
     * Loads members and items from disk files and reconstructs relationships.
     */
    public static void LoadFromFiles(ArrayList<Person> members, ArrayList<LibraryItem> items) throws FileNotFoundException {
        ArrayList<String> borrowed = new ArrayList<>();
        ArrayList<String> owners = new ArrayList<>();
        loadAllmembers(members, borrowed, "members.txt");
        loadAllItems(items, owners, "items.txt");
        adjustOwners(members, items, owners);
        adjustBorrowed(members, items, borrowed);
    }

    /**
     * Reconstructs each member's borrowed items from serialized strings.
     */
    public static void adjustBorrowed(ArrayList<Person> members, ArrayList<LibraryItem> items, ArrayList<String> borrowed) {
        for (int i = 0; i < members.size(); i++) {
            if (borrowed.get(i) != null) {
                String[] itemsBorrowed = borrowed.get(i).split("##");
                for (String serial : itemsBorrowed) {
                    if (serial != null && !serial.isEmpty()) {
                        try {
                            LibraryItem item = searchItemBySerialNb(Long.parseLong(serial), items);
                            if (item != null) {
                                members.get(i).getBorrowedItem().add(item);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Warning: Invalid serial number in borrowed items: " + serial);
                        }
                    }
                }
            }
        }
    }

    /**
     * Reconstructs each item's past owners list from serialized owner id strings.
     * Fixed to handle both Student and Civilian types safely.
     */
    public static void adjustOwners(ArrayList<Person> members, ArrayList<LibraryItem> items, ArrayList<String> owners) {
        for (int i = 0; i < items.size(); i++) {
            if (owners.get(i) != null) {
                String[] oldOwners = owners.get(i).split("&&");
                for (String id : oldOwners) {
                    if (id != null && !id.isEmpty()) {
                        Person member = searchMemberById(id, members);
                        if (member != null) {
                            // Add person to past owners list (works for both Student and Civilian)
                            items.get(i).getPastOwners().add(member);
                        }
                    }
                }
            }
        }
    }

    /**
     * Loads member data from a text file.
     */
    public static void loadAllmembers(ArrayList<Person> members, ArrayList<String> borrowed, String filePath) throws FileNotFoundException {
        File myFile = new File(filePath);
        if (!myFile.exists()) {
            return;
        }

        Scanner reader = new Scanner(myFile);
        while (reader.hasNext()) {
            String line = reader.nextLine();
            String[] tokens = line.split("&");
            try {
                if (tokens[0].equals("C")) {
                    members.add(new Civilian(
                        tokens[1], tokens[2], tokens[3].charAt(0), 
                        Integer.parseInt(tokens[4]), tokens[5], tokens[6], 
                        Double.parseDouble(tokens[7])));
                    borrowed.add(tokens.length == 9 ? tokens[8] : null);
                } else if (tokens[0].equals("S")) {
                    members.add(new Student(
                        tokens[1], tokens[2], tokens[3].charAt(0), 
                        Integer.parseInt(tokens[4]), tokens[5], tokens[6]));
                    borrowed.add(tokens.length == 8 ? tokens[7] : null);
                }
            } catch (Exception e) {
                System.out.println("Warning: Error loading member from line: " + line);
            }
        }
        reader.close();
    }

    /**
     * Loads item data from a text file.
     */
    public static void loadAllItems(ArrayList<LibraryItem> items, ArrayList<String> owners, String filePath) throws FileNotFoundException {
        File myFile = new File(filePath);
        if (!myFile.exists()) {
            return;
        }

        Scanner reader = new Scanner(myFile);
        while (reader.hasNext()) {
            String line = reader.nextLine();
            String[] tokens = line.split("#");
            try {
                if (tokens[0].equals("D")) {
                    items.add(new DVD(
                        Long.parseLong(tokens[1]), tokens[2], tokens[3], tokens[4], 
                        tokens[5].charAt(0), tokens[6], 
                        new Date(Long.parseLong(tokens[7])), 
                        Double.parseDouble(tokens[8])));
                } else if (tokens[0].equals("B")) {
                    items.add(new Book(
                        Long.parseLong(tokens[1]), tokens[2], tokens[3], tokens[4], 
                        tokens[5].charAt(0), tokens[6], 
                        new Date(Long.parseLong(tokens[7])), 
                        Integer.parseInt(tokens[8])));
                }
                owners.add(tokens.length == 10 ? tokens[9] : null);
            } catch (Exception e) {
                System.out.println("Warning: Error loading item from line: " + line);
            }
        }
        reader.close();
    }

    /**
     * Searches for a library item by serial number.
     */
    public static LibraryItem searchItemBySerialNb(long serialNb, ArrayList<LibraryItem> items) {
        if (items == null) {
            return null;
        }
        for (LibraryItem item : items) {
            if (item.getSerialNumber() == serialNb) {
                return item;
            }
        }
        return null;
    }

    /**
     * Searches for library items matching the provided title.
     */
    public static ArrayList<LibraryItem> searchItemByName(String name, ArrayList<LibraryItem> items) {
        ArrayList<LibraryItem> foundItems = new ArrayList<>();
        for (LibraryItem item : items) {
            if (item.getTitle().equalsIgnoreCase(name)) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    /**
     * Searches for a member by their ID.
     */
    public static Person searchMemberById(String id, ArrayList<Person> members) {
        for (Person member : members) {
            if (member instanceof Student) {
                if (((Student) member).getStudentId().equals(id)) {
                    return member;
                }
            } else if (member instanceof Civilian) {
                if (((Civilian) member).getId().equals(id)) {
                    return member;
                }
            }
        }
        return null;
    }

    /**
     * Searches for a member by name.
     */
    public static Person searchMemberByName(String name, ArrayList<Person> members) {
        for (Person member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                return member;
            }
        }
        return null;
    }

    /**
     * Deletes a member from the provided list by ID or name.
     */
    public static void deleteMember(ArrayList<Person> members, String searchInput) {
        Person memberToDelete = null;
        
        for (Person member : members) {
            if (member instanceof Civilian) {
                Civilian civilianMember = (Civilian) member;
                if (civilianMember.getId().equals(searchInput) || 
                    civilianMember.getName().equalsIgnoreCase(searchInput)) {
                    memberToDelete = member;
                    break;
                }
            } else if (member instanceof Student) {
                Student studentMember = (Student) member;
                if (studentMember.getStudentId().equals(searchInput) || 
                    studentMember.getName().equalsIgnoreCase(searchInput)) {
                    memberToDelete = member;
                    break;
                }
            }
        }
        
        if (memberToDelete != null) {
            members.remove(memberToDelete);
            System.out.println("Member '" + searchInput + "' deleted successfully.");
        } else {
            System.out.println("Member '" + searchInput + "' not found.");
        }
    }

    /**
     * Handles the borrow flow with better error handling.
     */
    public static void borrowItem(ArrayList<Person> members, ArrayList<LibraryItem> items) {
        System.out.println();
        System.out.println("Borrowing an item...");
        System.out.print("Enter the ID of the member who wants to borrow an item: ");
        String Id = input.nextLine();

        Person borrower = searchMemberById(Id, members);
        if (borrower == null) {
            System.out.println("Member with ID " + Id + " not found.");
            return;
        }

        System.out.println("Available items:");
        boolean hasAvailableItems = false;
        for (LibraryItem item : items) {
            if (item.getStatus() == 'a') {
                System.out.println(item);
                hasAvailableItems = true;
            }
        }
        
        if (!hasAvailableItems) {
            System.out.println("No available items to borrow.");
            return;
        }

        System.out.print("Enter the serial number of the item to borrow: ");
        try {
            long itemSerialNumber = Long.parseLong(input.nextLine());
            LibraryItem itemToBorrow = searchItemBySerialNb(itemSerialNumber, items);

            if (itemToBorrow == null) {
                System.out.println("Item with serial number " + itemSerialNumber + " not found.");
                return;
            }

            if (itemToBorrow.getStatus() != 'a') {
                System.out.println("Item is not available for borrowing.");
                return;
            }

            boolean success = false;
            if (borrower instanceof Student) {
                success = ((Student) borrower).borrowItem(itemToBorrow);
            } else if (borrower instanceof Civilian) {
                success = ((Civilian) borrower).borrowItem(itemToBorrow);
            }

            if (success) {
                System.out.println("Item borrowed successfully.");
            } else {
                System.out.println("Failed to borrow item. Check borrowing limits or balance.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid serial number format.");
        }
    }

    /**
     * Handles the return flow with better error handling.
     */
    public static void returnLibraryItem(ArrayList<Person> members, ArrayList<LibraryItem> items) {
        System.out.println();
        System.out.println("Returning an item...");
        System.out.print("Enter the serial number of the item to return: ");
        
        try {
            long serialNumber = Long.parseLong(input.nextLine());
            LibraryItem itemToReturn = searchItemBySerialNb(serialNumber, items);

            if (itemToReturn == null) {
                System.out.println("Item with serial number " + serialNumber + " not found.");
                return;
            }

            System.out.print("Enter the ID of the member who borrowed the item: ");
            String memberId = input.nextLine();
            Person borrower = searchMemberById(memberId, members);

            if (borrower == null) {
                System.out.println("Member with ID " + memberId + " not found.");
                return;
            }

            boolean success = false;
            if (borrower instanceof Student) {
                success = ((Student) borrower).returnItem(itemToReturn);
            } else if (borrower instanceof Civilian) {
                success = ((Civilian) borrower).returnItem(itemToReturn);
            }

            if (success) {
                System.out.println("Item returned successfully.");
            } else {
                System.out.println("Member did not have this item borrowed.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid serial number format.");
        }
    }

    /**
     * Saves all members and items to their respective files.
     */
    public static void SaveAllToFiles(ArrayList<Person> members, ArrayList<LibraryItem> items) {
        saveMembersToFile(members);
        saveItemsToFile(items);
    }

    /**
     * Writes members to the "members.txt" file.
     */
    private static void saveMembersToFile(ArrayList<Person> members) {
        try (FileWriter writer = new FileWriter("members.txt")) {
            for (Person member : members) {
                StringBuilder line = new StringBuilder();
                if (member instanceof Civilian) {
                    Civilian c = (Civilian) member;
                    line.append("C&")
                        .append(escapeField(c.getName())).append('&')
                        .append(escapeField(c.getAddress())).append('&')
                        .append(c.getGender()).append('&')
                        .append(c.getAge()).append('&')
                        .append(escapeField(c.getPhoneNumber())).append('&')
                        .append(escapeField(c.getId())).append('&')
                        .append(c.getCurrentBal());
                    if (!c.getBorrowedItem().isEmpty()) {
                        line.append('&');
                        StringJoiner sj = new StringJoiner("##");
                        for (LibraryItem it : c.getBorrowedItem()) {
                            sj.add(String.valueOf(it.getSerialNumber()));
                        }
                        line.append(sj.toString());
                    }
                } else if (member instanceof Student) {
                    Student s = (Student) member;
                    line.append("S&")
                        .append(escapeField(s.getName())).append('&')
                        .append(escapeField(s.getAddress())).append('&')
                        .append(s.getGender()).append('&')
                        .append(s.getAge()).append('&')
                        .append(escapeField(s.getPhoneNumber())).append('&')
                        .append(escapeField(s.getStudentId()));
                    if (!s.getBorrowedItem().isEmpty()) {
                        line.append('&');
                        StringJoiner sj = new StringJoiner("##");
                        for (LibraryItem it : s.getBorrowedItem()) {
                            sj.add(String.valueOf(it.getSerialNumber()));
                        }
                        line.append(sj.toString());
                    }
                }
                writer.write(line.toString() + System.lineSeparator());
            }
            System.out.println("Members data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving members data: " + e.getMessage());
        }
    }

    /**
     * Writes items to the "items.txt" file.
     */
    private static void saveItemsToFile(ArrayList<LibraryItem> items) {
        try (FileWriter writer = new FileWriter("items.txt")) {
            for (LibraryItem item : items) {
                StringBuilder line = new StringBuilder();
                if (item instanceof DVD) {
                    DVD d = (DVD) item;
                    line.append('D').append('#')
                        .append(d.getSerialNumber()).append('#')
                        .append(escapeField(d.getTitle())).append('#')
                        .append(escapeField(d.getAuthor())).append('#')
                        .append(escapeField(d.getPublisher())).append('#')
                        .append(d.getStatus()).append('#')
                        .append(escapeField(d.getGenre())).append('#')
                        .append(d.getDateAvailable().getTime()).append('#')
                        .append(d.getSizeInMB());
                    if (!d.getPastOwners().isEmpty()) {
                        line.append('#');
                        StringJoiner sj = new StringJoiner("&&");
                        for (Person p : d.getPastOwners()) {
                            if (p instanceof Student) {
                                sj.add(((Student) p).getStudentId());
                            } else if (p instanceof Civilian) {
                                sj.add(((Civilian) p).getId());
                            }
                        }
                        line.append(sj.toString());
                    }
                } else if (item instanceof Book) {
                    Book b = (Book) item;
                    line.append('B').append('#')
                        .append(b.getSerialNumber()).append('#')
                        .append(escapeField(b.getTitle())).append('#')
                        .append(escapeField(b.getAuthor())).append('#')
                        .append(escapeField(b.getPublisher())).append('#')
                        .append(b.getStatus()).append('#')
                        .append(escapeField(b.getGenre())).append('#')
                        .append(b.getDateAvailable().getTime()).append('#')
                        .append(b.getNbOfPages());
                    if (!b.getPastOwners().isEmpty()) {
                        line.append('#');
                        StringJoiner sj = new StringJoiner("&&");
                        for (Person p : b.getPastOwners()) {
                            if (p instanceof Student) {
                                sj.add(((Student) p).getStudentId());
                            } else if (p instanceof Civilian) {
                                sj.add(((Civilian) p).getId());
                            }
                        }
                        line.append(sj.toString());
                    }
                }
                writer.write(line.toString() + System.lineSeparator());
            }
            System.out.println("Items data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving items data: " + e.getMessage());
        }
    }

    private static String escapeField(String s) {
        if (s == null) return "";
        return s.replace("&", "\\&").replace("#", "\\#");
    }

    /**
     * Displays the main menu and reads the user's choice.
     */
    public static int getChoice() {
        int choice = -1;
        while (choice < 1 || choice > EXIT) {
            System.out.println("Choose a number: ");
            System.out.println("1- Add new library item");
            System.out.println("2- Modify an item");
            System.out.println("3- Delete an item");
            System.out.println("4- Add new member");
            System.out.println("5- Modify a member info");
            System.out.println("6- Delete a member info");
            System.out.println("7- Search an item");
            System.out.println("8- Search a member");
            System.out.println("9- Borrow an item");
            System.out.println("10- Return an item");
            System.out.println("11- Display all items");
            System.out.println("12- Display all members");
            System.out.println("13- Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = input.nextInt();
                input.nextLine();
                if (choice < 1 || choice > EXIT) {
                    System.out.println("Invalid choice! Please enter a number between 1 and 13.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                input.nextLine(); // Clear buffer
                choice = -1;
            }
        }
        return choice;
    }
}