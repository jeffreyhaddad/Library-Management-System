import java.util.ArrayList;

/**
 * Represents a person in the library system.
 * This is the base class for library members, containing common attributes
 * such as name, address, and borrowed items.
 * 
 * @author jeffreyhaddad
 */
public class Person {
    /** Person's full name */
    private String name;
    /** Person's address */
    private String address;
    /** Person's gender (M/F) */
    char gender;
    /** Person's age */
    private int age;
    /** Person's contact phone number */
    String phoneNumber;
    /** List of items currently borrowed by this person */
    private ArrayList<LibraryItem> borrowedItem;

    /**
     * Constructs a person with the specified details.
     * 
     * @param name The person's name
     * @param address The person's address
     * @param gender The person's gender ('M'/'F')
     * @param age The person's age
     * @param phoneNumber The person's phone number
     */
    public Person(String name, String address, char gender, int age, String phoneNumber) {
        this.name = name;
        this.address = address;
        setGender(gender);
        setAge(age);
        setPhoneNumber(phoneNumber);
        borrowedItem = new ArrayList<>();
    }

    public Person(){
        this("unknown", "unknown", 'm', 0, "00-000000");
    }

    /**
     * Gets the person's name.
     * @return The person's name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns the person's gender as a char ('M' or 'F').
     * @return gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * Sets the person's gender. Accepts 'f'/'F' for female; anything else is
     * normalized to 'M'.
     * @param gender gender character
     */
    public void setGender(char gender){
        if(gender=='f' || gender=='F')
            this.gender = 'F';
        else
            this.gender='M';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age>=18)
            this.age = age;
        else
            this.age = 18;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        phoneNumber= phoneNumber.trim();
        if(checkValidNumber(phoneNumber))
            this.phoneNumber = phoneNumber;
    }

    /**
     * Validates a phone number format. Accepts two formats:
     * - 9 characters: digits with a dash at position 2 (eg "12-345678")
     * - 14 characters: starts with '+' and uses dashes at positions 4 and 7
     *
     * @param phoneNb phone number string to validate
     * @return true if valid, false otherwise
     */
    public static boolean checkValidNumber(String phoneNb){
        if(phoneNb.length()==9)
            {for(int i=0; i<9; i++)
               { if(i==2)
               {  if(phoneNb.charAt(i)!='-')
                    return false;}
                else if(!Character.isDigit(phoneNb.charAt(i)))
                    return false;
               }
              return true;
            }
        else if(phoneNb.length()==14)
        {
        for(int i=0; i<14; i++)
               { if(i==0 )
               {if( phoneNb.charAt(i)!='+')
                    return false;}
                else if(i==4 || i==7)
                   {   if( phoneNb.charAt(i)!='-')
                    return false;}
                else
                   {if(!Character.isDigit(phoneNb.charAt(i)))
                    return false;}
               }
              return true;
        }

        return false;       
    }
    public ArrayList<LibraryItem> getBorrowedItem() {
        return borrowedItem;
    }
    @Override
    public String toString() {
        return name + ", " + age + " " + gender + ", residence:" + address + "\nphone number: " + phoneNumber;
    }

}

