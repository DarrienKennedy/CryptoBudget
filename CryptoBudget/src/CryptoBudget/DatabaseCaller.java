package CryptoBudget;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * The DatabaseCall class is used to interact
 * with the database all interactions to the database
 * are done through this class.
 */
public class DatabaseCaller {
    private java.sql.Connection connection;

    public DatabaseCaller(java.sql.Connection cn){
        connection = cn;
    }

    /**
     * Create the sqlite database ONLY
     * if it is not already created.
     *
     * When creating database allow all PK to autoincrement
     *
     * On creation of the database insert real world currency(dollar, yen, pound, etc)
     * into the currency table
     */
    public void createDatabase(){

    }

    /**
     * Insert or update a coin into the database.
     *
     * @param name: The cryptocurrency coin name
     * @param value: The value of the coin
     * @param percentChange: The percent change of the coin over 24 hours.
     */
    public void insertCoin(String name, String value, String percentChange){

        //check if the coin was already there
        String selectSQL = "SELECT * " +
                            "FROM cryptoValue " +
                            "WHERE currencyName = ?";

        boolean coinIsThere = false;

        try {
            PreparedStatement pstmt = connection.prepareStatement(selectSQL);
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            if(rs.isBeforeFirst()){
                coinIsThere = true;
            } else {
                coinIsThere = false;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }

        //populating a row in cryptoValue table
        String currentTime = new SimpleDateFormat("HH.mm.ss").format(new java.util.Date());
        String sql = "INSERT INTO cryptoValue VALUES(?,?,?,?)";

        if(coinIsThere){
            sql = "UPDATE cryptoValue SET currencyValue = ? , percentChange = ?, lastUpdated = ? WHERE currencyName = ?";
        }
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            if(coinIsThere){
                pstmt.setString(1, value);
                pstmt.setString(2, percentChange);
                pstmt.setDate(3, currentTime);
                pstmt.setString(4, name);
            } else {
                pstmt.setString(1, name);
                pstmt.setString(2, value);
                pstmt.setDate(3, percentChange);
                pstmt.setString(4, currentTime);
            }
            pstmt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String [][] getAllTransactions(int userId){

    }

    public String [][] getAllPayments(int userId, boolean isRecurring){

    }

    public String [][] getAllIncome(int userId, boolean isRecurring){

    }

    public void setSinglePayment(int userId, double amount, String date, String currencyType, String paymentType, String payeeName){
        String sql = "INSERT INTO Payment VALUES (?,?,?,?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1,userId);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, date);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void setRecurringPayment(){

    }

    public void setSingleIncome(){

    }

    public void setRecurringIncome(){

    }

    public  String [][] getGoals(){

    }

    public void setGoals(){

    }

    public void createUser(){

    }

    public void updateAccount(){

    }

    public void updateAccountCurrencies(){

    }

    public String getUserId(String userName, String password){

    }

}
