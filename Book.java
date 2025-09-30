import java.util.Date;

/**
 * Represents a book in the library system. Extends {@link LibraryItem}.
 * Includes additional attributes such as number of pages and book cost.
 * 
 * @author jeffreyhaddad
 */
public class Book extends LibraryItem{
   
    /**
     * The default cost of a book.
     */
    private static double BOOK_COST = 8;

    /**
     * Number of pages in the book.
     */
    private int nbOfPages;
    
    /**
     * Default constructor. Initializes a book with default values.
     */
    public Book(){
        this(generateSerialNumber(), "title", "author", "publisher", 'a', "genre", new Date(), 20);
    }
     
    /**
     * Constructs a book with specified details except serial number and date available.
     * @param title Title of the book
     * @param author Author of the book
     * @param publisher Publisher of the book
     * @param status Status of the book
     * @param genre Genre of the book
     * @param nbOfPages Number of pages
     */
    public Book(String title, String author, String publisher, char status, String genre, int nbOfPages) {
        this(generateSerialNumber(), title, author, publisher, status, genre, new Date(), nbOfPages);
    }
    
    /**
     * Constructs a book with all details specified.
     * @param serialNumber Serial number of the book
     * @param title Title of the book
     * @param author Author of the book
     * @param publisher Publisher of the book
     * @param status Status of the book
     * @param genre Genre of the book
     * @param dateAvailable Date the book is available
     * @param nbOfPages Number of pages
     */
    public Book(long serialNumber, String title, String author, String publisher, char status, String genre,
                Date dateAvailable, int nbOfPages) {
        super(serialNumber, title, author, publisher, status, genre, dateAvailable);
        setNbOfPages(nbOfPages);
    }
 
    /**
     * Sets the number of pages for the book. Minimum is 20 pages.
     * @param nbOfPages Number of pages
     */
    public void setNbOfPages(int nbOfPages) {
        if (nbOfPages < 20) {
            this.nbOfPages = 20;
        } else {
            this.nbOfPages = nbOfPages;
        }
    }
    
    /**
     * Sets the cost of the book. Minimum cost is 8.
     * @param BOOK_COST Cost to set
     */
    public void setBookCost(double BOOK_COST) {
        if (BOOK_COST < 8) {
            this.BOOK_COST = 8;
        } else {
            this.BOOK_COST = BOOK_COST;
        }
    }
    
    /**
     * Gets the number of pages in the book.
     * @return Number of pages
     */
    public int getNbOfPages() {
        return nbOfPages;
    }
    
    /**
     * Gets the price of the book, including base price and book cost.
     * @return Price of the book
     */
    public double getPrice() {
        return super.getPrice() + BOOK_COST;
    }
     
    /**
     * Returns a string representation of the book, including number of pages.
     * @return String representation
     */
    @Override
    public String toString() {
        return super.toString() + "\nnumber of pages: " + nbOfPages;
    }
    
}


