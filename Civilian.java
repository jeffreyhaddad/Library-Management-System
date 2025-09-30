/**
 * Represents a civilian member of the library system. Extends {@link Person}.
 * Civilians have a balance-based borrowing system where they need sufficient
 * credit to borrow items.
 * 
 * @author jeffreyhaddad
 */
public class Civilian extends Person {
    /** Unique identifier for the civilian */
    private String id;
    /** Current balance available for borrowing items */
    private double currentBal;

    /**
     * Constructs a civilian with specified details.
     * 
     * @param name The civilian's name
     * @param address The civilian's address
     * @param gender The civilian's gender
     * @param age The civilian's age
     * @param phoneNumber The civilian's phone number
     * @param id The civilian's unique identifier
     * @param currentBal The initial balance for the civilian
     */
    public Civilian(String name, String address, char gender, int age, String phoneNumber, String id, double currentBal) {
        super(name, address, gender, age, phoneNumber);
        this.id = id;
        setCurrentBal(currentBal);
    }
    public Civilian(){ this("unkown", "unknown", 'm', 18 , "00-000000", "unknown",50 );}

    //getters and setters

    /**
     * Sets the current balance for the civilian.
     * If the provided balance is less than or equal to 0,
     * a default value of 50 is used.
     * 
     * @param currentBal The balance to set
     */
    private void setCurrentBal(double currentBal) {
        if (currentBal > 0)
            this.currentBal = currentBal;
        else this.currentBal = 50;
    }
    
    /**
     * Adds credit to the civilian's balance. Only positive amounts are accepted.
     *
     * @param amount Amount to add
     */
    public void addCredit(double amount){
        if(amount > 0)
            setCurrentBal(currentBal+amount);
    }


    public double getCurrentBal() {
        return currentBal;
    }

    public String getId() {
        return id;
    }

    /**
     * Attempts to borrow an item using the civilian's balance. The item's price
     * must be less than or equal to the current balance and the item must be available.
     *
     * @param item The item to borrow
     * @return true if borrowing succeeded, false otherwise
     */
    public boolean borrowItem(LibraryItem item) {
        if (item.getPrice() <= currentBal && item.getStatus() == 'a') {
            currentBal -= item.getPrice();
            item.setStatus('o');
            item.setRegistration();
            item.getPastOwners().add(this);
            getBorrowedItem().add(item);
            return true;
        }
        return false;
    }

    /**
     * Returns a borrowed item. If the civilian currently has the item, it is
     * marked available and removed from the civilian's borrowed list.
     *
     * @param item The item to return
     * @return true if the item was returned, false otherwise
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

    @Override
    public String toString() {
        return "ID: " + id + "\n" + super.toString() + "\ncurrent Balance: $" + currentBal
                + "\nnumber of borrowed items: " + super.getBorrowedItem().size();
    }

}
