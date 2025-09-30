import java.util.Date;

/**
 * Represents a DVD in the library system. Extends {@link LibraryItem}.
 * Includes additional attributes specific to DVDs such as size in megabytes
 * and DVD-specific cost.
 * 
 * @author jeffreyhaddad
 */
public class DVD extends LibraryItem{
   
    /** The additional cost specific to DVDs */
    private static double DVD_COST = 5;  
    /** The size of the DVD content in megabytes */
    private double sizeInMB;
    
    /**
     * Default constructor. Creates a DVD with default values.
     */
    public DVD(){
        this(generateSerialNumber(),"title","author","publisher",
                'a',"genre",new Date(), 1);
    }
    
   public DVD(String title, String author, String publisher, char status, String genre, double sizeInMB){
        this(generateSerialNumber(), title, author, publisher, status, genre, new Date() , sizeInMB);
    }
   
    public DVD(long serialNumber, String title, String author, String publisher, char status, 
            String genre, Date dateAvailable, double sizeInMB){
    
        super(serialNumber, title, author, publisher, status, genre, dateAvailable);
        setSizeInMB( sizeInMB);      
    }
    
    /**
     * Sets the size of the DVD in megabytes.
     * @param sizeInMB The size in MB (must be greater than 1)
     */
    public void setSizeInMB(double sizeInMB){ 
        if(sizeInMB > 1)
        this.sizeInMB = sizeInMB;
    }
      
    /**
     * Returns the additional cost for DVDs.
     * @return DVD-specific cost
     */
    public double getDVDCost(){
        return DVD_COST;
    }
    
    /**
     * Returns the size of the DVD in megabytes.
     * @return size in MB
     */
    public double getSizeInMB(){
        return sizeInMB;
    }
    
    /**
     * Returns the full price for this DVD (base price + DVD-specific cost).
     * @return total price
     */
    public double getPrice(){
        return super.getPrice() + DVD_COST;
    }
    
    @Override
    public String toString(){
        return super.toString() + "\nDVD size: " + sizeInMB + "MB";
    
    }
    
}
