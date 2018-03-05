package sample;

import javax.swing.plaf.nimbus.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Currency {

    private int userId;

    public Currency(int userId){
        this.userId = userId;
    }

    public double getCurrencyValue(int currencyId){
        double currencyValue = 0.0;
        try{
            Statement stmt = CryptoBudgetDatabase.connection.createStatement();
            String valueSQL = "SELECT CURRENCYVALUE FROM CURRENCYVALUE WHERE CURRENCYID = ?;";
            PreparedStatement pstmt = CryptoBudgetDatabase.connection.prepareStatement(valueSQL);
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
            Statement stmt = CryptoBudgetDatabase.connection.createStatement();
            String valueSQL = "SELECT CURRENCYVALUE FROM CURRENCYVALUE WHERE CURRENCYNAME = ?;";
            PreparedStatement pstmt = CryptoBudgetDatabase.connection.prepareStatement(valueSQL);
            pstmt.setString(1, currencyName);
            ResultSet rs = pstmt.executeQuery();

            currencyValue = rs.getDouble("CURRENCYVALUE");
        } catch (SQLException e) {

        }
        return currencyValue;
    }
}
