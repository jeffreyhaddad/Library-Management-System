/**
 * Represents a student member of the library system. Extends {@link Person}.
 * Students have specific borrowing privileges and limitations, including a
 * maximum number of items they can borrow simultaneously.
 * 
 * @author jeffreyhaddad
 */
public class Student extends Person {
    /** Unique identifier for the student */
    private String studentId;
    /** Maximum number of items a student can borrow at once */
    private static int maxNumberToBorrow = 3;

    /**
     * Constructs a student with specified details.
     * 
     * @param name The student's name
     * @param address The student's address
     * @param gender The student's gender
     * @param age The student's age
     * @param phoneNumber The student's phone number
     * @param studentId The student's unique identifier
     */
    public Student(String name, String address, char gender, int age, String phoneNumber, String studentId) {
        super(name, address, gender, age, phoneNumber);
        this.studentId = studentId;
    }

    public Student() {
          this("unkown", "unknown", 'm', 18 , "00-000000", "unknown");
    }

    /**
     * Attempts to borrow a library item.
     * Student can only borrow if they haven't reached their maximum limit
     * and the item is available.
     * 
     * @param item The item to borrow
     * @return true if the borrowing was successful, false otherwise
     */
    public boolean borrowItem(LibraryItem item) {
        if (getBorrowedItem().size() < maxNumberToBorrow && item.getStatus() == 'a'){
            item.setStatus('o');
            item.setRegistration();
            item.getPastOwners().add(this);
            getBorrowedItem().add(item);
            return true;
        }
        return false;
    }

    /**
     * Returns a borrowed library item. If the student currently has the item,
     * its status is set to available and it's removed from the student's borrowed list.
     *
     * @param item The item to return
     * @return true if the item was returned, false if the student didn't have it
     */
    public boolean returnItem(LibraryItem item) {
        if (getBorrowedItem().contains(item)){
            item.setStatus('a');
            item.setRegistration();
            getBorrowedItem().remove(item);
            return true;
        }
        return false;
    }

    public String getStudentId() {
        return studentId;
    }

    public static int getMaxNumberToBorrow() {
        return maxNumberToBorrow;
    }

    public static void setMaxNumberToBorrow(int maxNumberToBorrow) {
        Student.maxNumberToBorrow = maxNumberToBorrow;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + "\n" + super.toString() + "\nnumber of borrowed items: "+ super.getBorrowedItem().size() ;
    }
}
