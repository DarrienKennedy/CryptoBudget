/* User Requirements from ERD
 * userId -- PK
 * enableOCR
 * primaryCurrency
 * refreshRate
 * userName
 * firstName
 * lastName
 * lastLogin
 * password
 * passwordSalt
 *
 */
import java.util.Date;
public class User {
    protected int userId;
    protected boolean enableOCR;
    protected String primaryCurrency;
    protected double refreshRate;
    protected String userName;
    protected String firstName;
    protected String lastName;
    protected Date lastLogin;
    protected String password;
    //protected String passwordSalt;

    public User(){
        this.setUserId(0);
        this.setEnableOCR(false);
        this.setPrimaryCurrency("USD");
        this.setRefreshRate(0);
        this.setUserName("DEFAULT USERNAME");
        this.setFirstName("DEFAULT FIRST NAME");
        this.setLastName("DEFAULT LAST NAME");
        this.setLastLogin(new Date());
        this.setPassword("12345");
    }

    public User(String uName, String fName, String lName, String pwd){
        new User();
        this.setUserName(uName);
        this.setFirstName(fName);
        this.setLastName(lName);
        this.setPassword(pwd);
    }

    public int getUserId(){
        return userId;
    }

    public boolean getOCR(){
        return enableOCR;
    }

    public String getPrimaryCurrency(){
        return primaryCurrency;
    }

    public double getRefreshRate(){
        return refreshRate;
    }

    public String getUserName(){
        return userName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public Date getLastLogin(){
        return lastLogin;
    }

    //TODO: password security
    public String getPassword(){
        return password;
    }

    public void setUserId(int userIdToSet) {
        userId = userIdToSet;
    }

    public void setEnableOCR(boolean enabled){
        enableOCR = enabled;
    }

    public void setPrimaryCurrency(String primaryCurrencyToSet){
        primaryCurrency = primaryCurrencyToSet;
    }

    public void setRefreshRate(double refreshRateToSet){
        refreshRate = refreshRateToSet;
    }

    public void setUserName(String userNameToSet){
        userName = userNameToSet;
    }

    public void setFirstName(String firstNameToSet){
        firstName = firstNameToSet;
    }

    public void setLastName(String lastNameToSet){
        lastName = lastNameToSet;
    }

    public void setLastLogin(Date lastLoginToSet){
        lastLogin = lastLoginToSet;
    }

    public void setPassword(String passwordToSet){
        password = passwordToSet;
    }
}
