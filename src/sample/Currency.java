package sample;

import javax.swing.plaf.nimbus.State;
import java.security.spec.PSSParameterSpec;
import java.sql.*;
import java.util.ArrayList;

public class Currency {

    private int userId;
    private Connection connection;

    public Currency(int userId, Connection connection){
        this.userId = userId;
        this.connection = connection;
    }

    public double getCurrencyValue(int currencyId){
        double currencyValue = 0.0;
        try{
            Statement stmt = connection.createStatement();
            String valueSQL = "SELECT CURRENCYVALUE FROM CURRENCYVALUE WHERE CURRENCYID = ?;";
            PreparedStatement pstmt = connection.prepareStatement(valueSQL);
            pstmt.setInt(1, currencyId);
            ResultSet rs = pstmt.executeQuery();

            currencyValue = rs.getDouble("CURRENCYVALUE");
        } catch (SQLException e) {

        }
        return currencyValue;
    }

    public double getCurrencyValue(String currencyName){
        double currencyValue = 0.0;
        try{
            String valueSQL = "SELECT CURRENCYVALUE FROM CURRENCYVALUE WHERE CURRENCYNAME = ?;";
            PreparedStatement pstmt = connection.prepareStatement(valueSQL);
            pstmt.setString(1, currencyName);
            ResultSet rs = pstmt.executeQuery();

            currencyValue = rs.getDouble("CURRENCYVALUE");
        } catch (SQLException e) {

        }
        return currencyValue;
    }

    public int[] getUserCurrencies(){
        ArrayList<Integer> userCurrencies = new ArrayList<>();
        String getUserCurrencies = "SELECT CURRENCYID FROM ACCOUNTCURRENCIES WHERE USERID = ?;";
        try{
            PreparedStatement pstmt = connection.prepareStatement(getUserCurrencies);
            pstmt.setInt(1, userId);
            ResultSet rs  = pstmt.executeQuery();

            while(rs.next()){
                userCurrencies.add(rs.getInt("CURRENCYID"));
            }
        } catch (SQLException e){

        }

        int[] userCurrencyNames = new int[userCurrencies.size()];

        for(int i = 0; i < userCurrencyNames.length; i++){
            userCurrencyNames[i] = userCurrencies.get(i).intValue();
        }

        return userCurrencyNames;
    }

    public void updateCurrencyAmount(int currencyId, double amount){
        if(isUserHasCurrency(currencyId)){
            //get how much they have then add the amount to it
            double currentAmount = 0.0;
            String getCurrencyAmount = "SELECT AMOUNTOFCURRENCY FROM ACCOUNTCURRENCIES WHERE USERID = ? AND CURRENCYID = ?;";
            try{
                PreparedStatement pstmt = connection.prepareStatement(getCurrencyAmount);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, currencyId);
                ResultSet rs = pstmt.executeQuery();

                currentAmount = rs.getDouble("AMOUNTOFCURRENCY");
            } catch (SQLException e){

            }

            currentAmount = currentAmount + amount;
            String updateUserCurrency = "UPDATE ACCOUNTCURRENCIES SET AMOUNTOFCURRENCY = ? WHERE USERID = ? AND CURRENCYID = ?;";

            try{
                PreparedStatement pstmt = connection.prepareStatement(updateUserCurrency);
                pstmt.setDouble(1, currentAmount);
                pstmt.setInt(2, userId);
                pstmt.setInt(3, currencyId);

                pstmt.executeUpdate();
            } catch (SQLException e){

            }
        } else {
            //create a new row with the given amount
            String createNewAccountCurrency = "INSERT INTO ACCOUNTCURRENCIES (USERID, CURRENCYID, AMOUNTOFCURRENCY) VALUES ( ? , ? , ? )";
            try {
                PreparedStatement pstmt = connection.prepareStatement(createNewAccountCurrency);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, currencyId);
                pstmt.setDouble(3, amount);

                pstmt.executeUpdate();
            } catch (SQLException e) {

            }
        }
    }

    public void updateCurrencyAmount(String currencyName, double amount){
        //get the currency id then call updateCurrencyAmount with it
        String getCurrencyID = "SELECT CURRENCYID FROM CURRENCYVALUE WHERE CURRENCYNAME = ?";
        int currencyId = 0;
        try{
            PreparedStatement pstmt = connection.prepareStatement(getCurrencyID);
            pstmt.setString(1, currencyName);
            ResultSet rs = pstmt.executeQuery();

            currencyId = rs.getInt("CURRENCYID");
        } catch (SQLException e){

        }

        updateCurrencyAmount(currencyId, amount);
    }

    public boolean isUserHasCurrency(int currencyID){
        //check if it was already there
        String selectSQL = "SELECT * " +
                "FROM ACCOUNTCURRENCIES " +
                "WHERE USERID = ? " +
                "AND CURRENCYID = ?;";

        boolean userHasCurrency = false;

        try {
            PreparedStatement pstmt = connection.prepareStatement(selectSQL);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, currencyID);

            ResultSet rs = pstmt.executeQuery();

            if(rs.isBeforeFirst()){
                userHasCurrency = true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return userHasCurrency;
    }

    public boolean isUserHasCurrency(String currencyName){
        //check if it was already there
        String selectSQL = "SELECT * " +
                "FROM ACCOUNTCURRENCIES " +
                "WHERE USERID = ? " +
                "AND CURRENCYNAME = ?;";

        boolean userHasCurrency = false;

        try {
            PreparedStatement pstmt = connection.prepareStatement(selectSQL);
            pstmt.setInt(1, userId);
            pstmt.setString(2, currencyName);

            ResultSet rs = pstmt.executeQuery();

            if(rs.isBeforeFirst()){
                userHasCurrency = true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return userHasCurrency;
    }
}
