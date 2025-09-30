import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a generic library item that can be borrowed.
 * This is the base class for specific types of library items like books and DVDs.
 * Contains common attributes such as serial number, title, author, and status.
 * 
 * Note: Uses deprecated Date API for educational purposes. Production code should use java.time package.
 * 
 * @author jeffreyhaddad
 */
public class LibraryItem {
    
    /** The base cost applied to all library items */
    private static final double GENERAL_COST = 10;
    /** Unique identifier for the library item */
    private long serialNumber;
    /** Title of the library item */
    private String title;
    /** Author of the library item */
    private String author;
    /** Publisher of the library item */
    private String publisher;
    /** Genre or category of the library item */
    private String genre;
    /** Current status of the item: 'a'/'A' for available, 'r'/'R' for reserved, 'o'/'O' for checked out */
    private char status;
    /** Date when the item becomes available */
    private Date dateAvailable;
    /** List of people who have previously borrowed this item */
    private ArrayList<Person> pastOwners;
    
    /**
     * Default constructor. Creates a library item with default values.
     */
    public LibraryItem() {
        this(generateSerialNumber(), "title", "author", "publisher", 'a', "genre", new Date());
    }
    
    /**
     * Constructs a library item with specified details.
     * @param title The title of the item
     * @param author The author of the item
     * @param publisher The publisher of the item
     * @param status The status of the item
     * @param genre The genre of the item
     */
    public LibraryItem(String title, String author, String publisher, char status, String genre) {
        this(generateSerialNumber(), title, author, publisher, status, genre, new Date());
    }
    
    /**
     * Constructs a library item with all details specified.
     * Note: the serial number is generated automatically and the passed
     * serialNumber parameter is ignored in current implementation.
     *
     * @param serialNumber A serial number (not currently used; serial is generated)
     * @param title The title of the item
     * @param author The author of the item
     * @param publisher The publisher of the item
     * @param status The status of the item
     * @param genre The genre of the item
     * @param dateAvailable The date when the item is available
     */
    public LibraryItem(long serialNumber, String title, String author, String publisher, char status, String genre, Date dateAvailable) {
        this.serialNumber = generateSerialNumber();
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setStatus(status);
        setGenre(genre);
        this.dateAvailable = dateAvailable;
        this.pastOwners = new ArrayList<>();
        setRegistration();
    }
    
    //Setters and getters
    /**
     * Sets the title of the library item.
     * @param title New title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Sets the author of the library item.
     * @param author New author
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    
    /**
     * Sets the publisher of the library item.
     * @param publisher New publisher
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    /**
     * Sets the status for the item. Allowed values are 'a'/'A' (available),
     * 'r'/'R' (reference/reserved), and 'o'/'O' (on loan). Any other value
     * defaults to available ('a').
     *
     * @param status Status character
     */
    public void setStatus(char status) {
        if (status == 'A' || status == 'a' || status == 'r' || status == 'R' || status == 'o' || status == 'O') {
            this.status = Character.toLowerCase(status);
        } else {
            this.status = 'a';
        }
    }
    
    /**
     * Sets the genre/category of the item.
     * @param genre Genre string
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    /**
     * Sets the serial number for the item. Typically serial numbers are
     * generated automatically, but this setter allows overriding when needed.
     * @param serialNumber Serial number to assign
     */
    public void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    /**
     * Replaces the past owners list with the provided list.
     * @param pastOwners List of past owners
     */
    public void setPastOwners(ArrayList<Person> pastOwners) {
        this.pastOwners = pastOwners;
    }
    
    /**
     * Returns the serial number of the item.
     * @return serial number
     */
    public long getSerialNumber() {
        return serialNumber;
    }
    
    /**
     * Returns the title of the item.
     * @return title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Returns the author of the item.
     * @return author
     */
    public String getAuthor() {
        return author;
    }
    
    /**
     * Returns the publisher of the item.
     * @return publisher
     */
    public String getPublisher() {
        return publisher;
    }
    
    /**
     * Returns the genre of the item.
     * @return genre
     */
    public String getGenre() {
        return genre;
    }
    
    /**
     * Returns the status character for the item.
     * @return status ('a'/'o'/'r')
     */
    public char getStatus() {
        return status;
    }
    
    /**
     * Returns the date when the item will be available.
     * @return date available
     */
    public Date getDateAvailable() {
        return dateAvailable;
    }
    
    /**
     * Returns the list of past owners (borrowers) for the item.
     * @return list of past owners
     */
    public ArrayList<Person> getPastOwners() {
        return pastOwners;
    }
    
    //Other methods
    
    /**
     * Updates the registration (availability) date based on current status.
     * If the item is available ('a') the availability date is set to now.
     * Otherwise the availability date is set to roughly three months from now.
     */
    public void setRegistration() {
        if (status == 'a') {
            // If the item is available, set the dateAvailable to the current date
            dateAvailable = new Date();
        } else {
            // If the item is not available, set date to three months from now
            long currentTime = System.currentTimeMillis();
            long threeMonthsInMillis = (long) 3 * 30 * 24 * 60 * 60 * 1000;
            long futureTime = currentTime + threeMonthsInMillis;
            dateAvailable = new Date(futureTime);
        }
    }
    
    /**
     * Returns the number of whole days remaining until the item becomes available.
     * A negative value indicates the item is already available.
     *
     * @return days remaining until availability
     */
    public long getTimeRemainingDays() {
        Date currentDate = new Date();
        long differenceInMillis = dateAvailable.getTime() - currentDate.getTime();
        return differenceInMillis / (24 * 60 * 60 * 1000);
    }
    
    /**
     * Returns the base price for this item type. Subclasses may add
     * additional costs by overriding this method and calling super.getPrice().
     *
     * @return base price
     */
    public double getPrice() {
        return GENERAL_COST;
    }
    
    /**
     * Returns a human-readable representation of the library item.
     * Includes serial number, title, genre, author, publisher and status info.
     *
     * @return formatted string describing the item
     */
    @Override
    public String toString() {
        String statusString;
        if (status == 'a') {
            statusString = "available";
        } else if (status == 'o') {
            statusString = "on loan, available on " + dateAvailable;
        } else {
            statusString = "is a reference item";
        }
        return "SN:" + serialNumber + "\n" + title + "[" + genre + "]\n" + "by " + author + " published by " + publisher + "\n" + statusString;
    }
    
    /**
     * Utility method to generate a serial number based on the current date/time.
     * Format used: YMMDDHHmmss (last digit of year + 2-digit month + 2-digit day + 2-digit hour + 2-digit minute + 2-digit second)
     *
     * @return generated serial number as a long
     */
    @SuppressWarnings("deprecation")
    public static long generateSerialNumber() {
        Date currentDate = new Date();
        String year = String.valueOf(currentDate.getYear() % 10);
        String month = String.format("%02d", currentDate.getMonth() + 1);
        String day = String.format("%02d", currentDate.getDate());
        String hour = String.format("%02d", currentDate.getHours());
        String minute = String.format("%02d", currentDate.getMinutes());
        String second = String.format("%02d", currentDate.getSeconds());
        String serialNumberStr = year + month + day + hour + minute + second;
        return Long.parseLong(serialNumberStr);
    }
}