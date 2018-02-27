package sample;

import java.sql.*;
import java.util.ArrayList;

public class Payment extends Transaction {

    /*
        Constructors
     */
    public Payment() {
        super();
    }

    public Payment(int newCurrencyType, double newValue) {
        super(newCurrencyType, newValue);
    }

    public Payment(int id, int userId, int currencyType, double amount, int date, int endDate,
                   int frequency, String otherParty) {
        super(id, userId, currencyType, amount, date, endDate, frequency, otherParty);
    }

    /*
        Database & Logic
     */
    public void create() {
        try {
            Statement stmt = CryptoBudgetDatabase.connection.createStatement();
            String savePayment = "INSERT INTO PAYMENT (USERID, AMOUNT, DATE, ENDDATE, CURRENCYTYPE, FREQUENCY, " +
                    "OTHERPARTY) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement prep = CryptoBudgetDatabase.connection.prepareStatement(savePayment);
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
            String update = "UPDATE PAYMENT SET USERID = ?, AMOUNT = ?, DATE = ?, ENDDATE = ?, CURRENCYTYPE = ?, " +
                    "FREQUENCY = ?, OTHERPARTY = ? WHERE PAYMENTID = ?;";
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

    public static Payment[] getAllPayments() {
        try {
            Statement stmt = CryptoBudgetDatabase.connection.createStatement();
            String getAll = String.format("SELECT * FROM PAYMENT;");
            ResultSet rs = stmt.executeQuery(getAll);

            ArrayList<Payment> result = new ArrayList();

            while (rs.next()) {
                int id = rs.getInt("PAYMENTID");
                int userId = rs.getInt("USERID");
                double amount = rs.getDouble("AMOUNT");
                int date = rs.getInt("DATE");
                int endDate = rs.getInt("ENDDATE");
                int currencyType = rs.getInt("CURRENCYTYPE");
                int frequency = rs.getInt("FREQUENCY");
                String otherParty = rs.getString("OTHERPARTY");
                result.add(new Payment(id, userId, currencyType, amount, date, endDate, frequency, otherParty));

            }
            return result.toArray(new Payment[0]);
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Payment getPayment(int findId) {
        try {
            Statement stmt = CryptoBudgetDatabase.connection.createStatement();
            String findPayment = "SELECT * FROM PAYMENT WHERE PAYMENTID = ?;";
            PreparedStatement prep = CryptoBudgetDatabase.connection.prepareStatement(findPayment);

            prep.setInt(1, findId);

            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("PAYMENTID");
                int userId = rs.getInt("USERID");
                double amount = rs.getDouble("AMOUNT");
                int date = rs.getInt("DATE");
                int endDate = rs.getInt("ENDDATE");
                int currencyType = rs.getInt("CURRENCYTYPE");
                int frequency = rs.getInt("FREQUENCY");
                String otherParty = rs.getString("OTHERPARTY");

                Payment result = new Payment(id, userId, currencyType, amount, date, endDate, frequency, otherParty);
                return result;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static void removePayment(int removeId) {
        try {
            Statement stmt = CryptoBudgetDatabase.connection.createStatement();
            String remove = "DELETE FROM PAYMENT WHERE PAYMENTID = ?;";
            PreparedStatement prep = CryptoBudgetDatabase.connection.prepareStatement(remove);
            prep.setInt(1, removeId);
            prep.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    // TODO add search functionality for Payment
}
