package sample;

import java.sql.*;
import java.util.ArrayList;

public class Income extends Transaction {

    /*
        Constructors
     */
    public Income() {
        super();
    }

    public Income(int newCurrencyType, double newValue) {
        super(newCurrencyType, newValue);
    }

    public Income(int id, int userId, int currencyType, double amount, int date, int endDate,
                  int frequency, String otherParty) {
        super(id, userId, currencyType, amount, date, endDate, frequency, otherParty);
    }

    /*
        Database & Logic
     */
    public void create() {
        try {
            Statement stmt = CryptoBudgetDatabase.connection.createStatement();
            String create = "INSERT INTO INCOME (USERID, AMOUNT, DATE, ENDDATE, CURRENCYTYPE, FREQUENCY, " +
                    "OTHERPARTY) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement prep = CryptoBudgetDatabase.connection.prepareStatement(create);
            prep.setInt(1, 0);
            prep.setDouble(2, this.amount);
            prep.setInt(3, 1999);
            prep.setInt(4, 2001);
            prep.setInt(5, 1);
            prep.setInt(6, 0);
            prep.setString(7, "Darrien");
            prep.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void update() {
        try {
            Statement stmt = CryptoBudgetDatabase.connection.createStatement();
            String update = "UPDATE INCOME SET USERID = ?, AMOUNT = ?, DATE = ?, ENDDATE = ?, CURRENCYTYPE = ?, " +
                    "FREQUENCY = ?, OTHERPARTY = ? WHERE INCOMEID = ?;";
            PreparedStatement prep = CryptoBudgetDatabase.connection.prepareStatement(update);
            prep.setInt(1, userId);
            prep.setDouble(2, amount);
            prep.setInt(3, date);
            prep.setInt(4, endDate);
            prep.setInt(5, currencyType);
            prep.setInt(6, frequency);
            prep.setString(7, otherParty);
            prep.setInt(8, id);

            prep.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public static Income[] getAllIncome() {
        try {
            Statement stmt = CryptoBudgetDatabase.connection.createStatement();
            String getAll = String.format("SELECT * FROM INCOME;");
            ResultSet rs = stmt.executeQuery(getAll);

            ArrayList<Income> result = new ArrayList();

            while (rs.next()) {
                int id = rs.getInt("INCOMEID");
                int userId = rs.getInt("USERID");
                double amount = rs.getDouble("AMOUNT");
                int date = rs.getInt("DATE");
                int endDate = rs.getInt("ENDDATE");
                int currencyType = rs.getInt("CURRENCYTYPE");
                int frequency = rs.getInt("FREQUENCY");
                String otherParty = rs.getString("OTHERPARTY");
                result.add(new Income(id, userId, currencyType, amount, date, endDate, frequency, otherParty));

            }
            return result.toArray(new Income[0]);
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Income getIncome(int findId) {
        try {
            Statement stmt = CryptoBudgetDatabase.connection.createStatement();
            String findIncome = "SELECT * FROM INCOME WHERE INCOMEID = ?;";
            PreparedStatement prep = CryptoBudgetDatabase.connection.prepareStatement(findIncome);

            prep.setInt(1, findId);

            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("INCOMEID");
                int userId = rs.getInt("USERID");
                double amount = rs.getDouble("AMOUNT");
                int date = rs.getInt("DATE");
                int endDate = rs.getInt("ENDDATE");
                int currencyType = rs.getInt("CURRENCYTYPE");
                int frequency = rs.getInt("FREQUENCY");
                String otherParty = rs.getString("OTHERPARTY");

                Income result = new Income(id, userId, currencyType, amount, date, endDate, frequency, otherParty);
                return result;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static void removeIncome(int removeId) {
        try {
            Statement stmt = CryptoBudgetDatabase.connection.createStatement();
            String remove = "DELETE FROM INCOME WHERE INCOMEID = ?;";
            PreparedStatement prep = CryptoBudgetDatabase.connection.prepareStatement(remove);
            prep.setInt(1, removeId);
            prep.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    // TODO add search functionality for Income
}
