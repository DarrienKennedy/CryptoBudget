package sample;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    protected int id;
    protected int currencyType;
    protected String currencyAbbreviation;
    protected double amount;
    protected int userId; // This will eventually be type User
    protected int frequency;
    protected String otherParty;
    protected long date;
    protected long endDate;
    protected boolean recurring;
    protected String transactionType;

    /**
     * Default Constructor for a Transaction.
     */
    public Transaction() {
        this.setCurrencyType(1);
        this.setAmount(0);
        this.setUserId(0);
        this.setFrequency(0);
        this.setDate(0);
        this.setTransactionType("Transaction");
    }

    /**
     * Constructor for a Transaction which takes the currency type and value.
     * @param newCurrencyType The new value for the type of currency.
     * @param newAmount The new value for amount.
     */
    public Transaction(int newCurrencyType, double newAmount) {
        this();
        this.setCurrencyType(newCurrencyType);
        this.setAmount(newAmount);
    }

    //constructor used for ViewTransactionController
    public Transaction(int newCurrencyType, double newAmount, String newOtherParty, long newDate, String newTransactionType){
        this();
        this.setCurrencyType(newCurrencyType);
        this.setAmount(newAmount);
        this.setOtherParty(newOtherParty);
        this.setDate(newDate);
        this.setTransactionType(newTransactionType);
    }

    public Transaction(int newId, int newUserId, int newCurrencyType, double newAmount, long newDate, long newEndDate,
                       int newFrequency, String newOtherParty) {
        this();
        this.id = newId;
        this.setUserId(newUserId);
        this.setCurrencyType(newCurrencyType);
        this.setAmount(newAmount);
        this.setDate(newDate);
        this.setEndDate(newEndDate);
        this.setFrequency(newFrequency);
        this.setOtherParty(newOtherParty);
    }

    /**
     * Returns the Transaction's assigned identifier.
     * @return The value of the Transaction's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the Transaction's assigned currency type.
     * @return The value representing the currency used for the Transaction.
     */
    public int getCurrencyType() {
        return currencyType;
    }

    /**
     * Returns the Transaction's assigned amount.
     * @return The value of currency exchanged in Transaction.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the User which made the Transaction.
     * @return The identifier of the User which made the Transaction.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns how frequently the Transaction is made.
     * @return The value representing how frequency of the Transaction.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Returns the other party associated with the Transaction
     * @return A String representing the other party in the Transaction.
     */
    public String getOtherParty() {
        return otherParty;
    }

    /**
     * Returns the date of when the Transaction occurred.
     * @return The value representing the date of the Transaction.
     */
    public long getDate() {
        return date;
    }

    /**
     * Returns the end date of the Transaction. This only applies if the Transaction is recurring.
     * @return The value reprenting the end date of the recurring Transaction.
     */
    public long getEndDate() {
        return endDate;
    }

    public String getTransactionType() { return transactionType; }

    public String getCurrencyAbbreviation() {
        return currencyAbbreviation;
    }

    public String getDateString() {
        Format f = new SimpleDateFormat("MM/dd");
        return f.format(new Date(date));
    }

    /**
     * Assign a new currency type for Transaction.
     * @param currencyTypeToSet The new value for the currency type.
     */
    public void setCurrencyType(int currencyTypeToSet) {
        // TODO Verify that it is a valid currency from the currency table?
        currencyType = currencyTypeToSet;
        currencyAbbreviation = Currency.idToAbbr(currencyTypeToSet);
    }

    /**
     * Assign a new amount for the Transaction.
     * @param amountToSet The new value for the amount.
     */
    public void setAmount(double amountToSet) {
        if (amountToSet > 0.0) {
            amount = amountToSet;
        }
    }

    /**
     * Assign a new identifier for the User who owns the Transaction.
     * @param userIdToSet The new value for the User's id.
     */
    public void setUserId(int userIdToSet) {
        if (userIdToSet >= 0) {
            userId = userIdToSet;
        }
    }

    /**
     * Assign a new frequency for the Transaction.
     * @param frequencyToSet The new value for the frequency.
     */
    public void setFrequency(int frequencyToSet) {
        if (frequencyToSet >= 0) {
            frequency = frequencyToSet;
            recurring = (frequency != 0);
        }
    }

    /**
     * Assign a new other party involved within the Transaction.
     * @param otherPartyToSet The new value for the otehr party.
     */
    public void setOtherParty(String otherPartyToSet) {
        otherParty = otherPartyToSet;
    }

    /**
     * Assign a new value for the date of the Transaction.
     * @param dateToSet The new value for the date.
     */
    public void setDate(long dateToSet) {
        date = dateToSet;
    }

    /**
     * Assign a new value for the end date of a recurring Transaction.
     * @param endDateToSet Thew new value for the end date.
     */
    public void setEndDate(long endDateToSet) {
        endDate = endDateToSet;
    }

    public void setTransactionType(String type) { transactionType = type; }

    /**
     * Concatenate the contents of the Transaction into a String.
     * @return A String containing fields of the Transaction.
     */
    public String toString() {
        return String.format("%d. %.2f %s, recurring: %b", this.id, this.amount, this.currencyType, this.recurring);
    }
}
