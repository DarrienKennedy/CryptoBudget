package sample;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
public class User {
    private int userId;
    private int enableOCR;
    private int primaryCurrency;
    private String refreshRate;
    private String userName;
    private String firstName;
    private String passwordSalt;
    private long lastLogin; // Date representation in a long form
    private String lastName;
    private String password;

    public User(){
        this.setUserId(0);
        this.setEnableOCR(1);
        this.setPrimaryCurrency(1);
        this.setRefreshRate("On Login");
        this.setUserName("defaultUsername");
        this.setFirstName("");
        this.setLastName("");
        //this.setLastLogin(new Date());
        this.setLastLogin(1000); // fix this
        this.setPassword("12345");
        this.setPasswordSalt("0");
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

    public int getOCR(){
        return enableOCR;
    }

    public int getPrimaryCurrency(){
        return primaryCurrency;
    }

    public String getRefreshRate(){
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

    public long getLastLogin(){
        return lastLogin;
    }

    public String getPasswordSalt() { return passwordSalt; }

    //TODO: password security
    public String getPassword(){
        return password;
    }

    public void setUserId(int userIdToSet) {
        userId = userIdToSet;
    }

    public void setEnableOCR(int enabled){
        enableOCR = enabled;
    }

    public void setPrimaryCurrency(int primaryCurrencyToSet){
        primaryCurrency = primaryCurrencyToSet;
    }

    public void setRefreshRate(String refreshRateToSet){
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

    public void setLastLogin(long lastLoginToSet){
        lastLogin = lastLoginToSet;
    }

    public void setPassword(String passwordToSet){
        password = passwordToSet;
    }

    public void setPasswordSalt(String passwordSaltToSet) { passwordSalt = passwordSaltToSet; }

    public void create() {
        try {
            String create = "INSERT INTO ACCOUNTLEDGER (ENABLEOCR, PRIMARYCURRENCY, REFRESHRATE, USERNAME, PASSWORD, " +
                    "PASSWORDSALT, FIRSTNAME, LASTNAME, LASTLOGIN) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prep = CryptoBudgetDatabase.connection.prepareStatement(create);
            prep.setInt(1, enableOCR);
            prep.setInt(2, primaryCurrency);
            prep.setString(3, refreshRate);
            prep.setString(4, userName);
            prep.setString(5, password);
            prep.setString(6, passwordSalt);
            prep.setString(7, firstName);
            prep.setString(8, lastName);
            prep.setLong(9, lastLogin);
            prep.execute();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void update() {
        try {
            String update = "UPDATE ACCOUNTLEDGER SET ENABLEOCR = ?, PRIMARYCURRENCY = ?, REFRESHRATE = ?, USERNAME = ?," +
                    " PASSWORD = ?, PASSWORDSALT = ?, FIRSTNAME = ?, LASTNAME = ?, LASTLOGIN = ? WHERE USERID = ?;";
            PreparedStatement prep = CryptoBudgetDatabase.connection.prepareStatement(update);
            prep.setInt(1, enableOCR);
            prep.setInt(2, primaryCurrency);
            prep.setString(3, refreshRate);
            prep.setString(4, userName);
            prep.setString(5, password);
            prep.setString(6, passwordSalt);
            prep.setString(7, firstName);
            prep.setString(8, lastName);
            prep.setLong(9, lastLogin);
            prep.setInt(10, Main.currentUser.getUserId());
            prep.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public static User getUser(String findUsername) {
        try {
            Statement stmt = CryptoBudgetDatabase.connection.createStatement();
            String findUser = "SELECT * FROM ACCOUNTLEDGER WHERE USERNAME = ?;";
            PreparedStatement prep = CryptoBudgetDatabase.connection.prepareStatement(findUser);
            prep.setString(1, findUsername);

            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                User result = new User();
                result.setUserId(rs.getInt("USERID"));
                result.setEnableOCR(rs.getInt("ENABLEOCR"));
                result.setPrimaryCurrency(rs.getInt("PRIMARYCURRENCY"));
                result.setRefreshRate(rs.getString("REFRESHRATE"));
                result.setUserName(rs.getString("USERNAME"));
                result.setPassword(rs.getString("PASSWORD"));
                result.setPasswordSalt(rs.getString("PASSWORDSALT"));
                result.setFirstName(rs.getString("FIRSTNAME"));
                result.setLastName(rs.getString("LASTNAME"));
                result.setLastLogin(rs.getLong("LASTLOGIN"));
                return result;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return null;
    }

}
