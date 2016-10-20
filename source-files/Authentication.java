package pdxservices;

/**
 * @author Michael Boyd
 * 
 * Date: 5/29/2015
 */

public class Authentication 
{
    /**
     * Data members.
     */
    private String userName, password;
    private boolean adminMode = false, userMode = false;
    
    /**
     * Constructor.
     * 
     * @param userName
     * @param password 
     */
    public Authentication(String userName, char[] password)
    {
        setUserName(userName);
        setPassword(password);
    }
    
    /**
     * Validates and sets the userName variable.
     * 
     * @param userName 
     */
    public void setUserName(String userName)
    {
            this.userName = userName;
    }
    
    /**
     * Validates and sets the password variable.
     * 
     * @param password 
     */
    public void setPassword(char[] password)
    {
        this.password = String.valueOf(password);
    }
    
    /**
     * Determines which GUI to display to the user based on the 
     * username/password combination.
     */
    public void setUIMode()
    {
        if(userName.matches("admin") && password.matches("123"))
        {
            adminMode = true;
        }
        else
        {
            userMode = true;
        }
    }
    
    /**
     * Returns the value stored in userName.
     * 
     * @return 
     */
    public String getUserName() 
    {
        return userName;
    }
    
    /**
     * Returns the value stored in password.
     * 
     * @return 
     */
    public String getPassword() 
    {
        return password;
    }
    
    /**
     * Returns the value stored in adminMode.
     * 
     * @return 
     */
    public boolean isAdminMode() 
    {
        return adminMode;
    }
    
    /**
     * Returns the value stored in userMode.
     * 
     * @return 
     */
    public boolean isUserMode() 
    {
        return userMode;
    }
}
