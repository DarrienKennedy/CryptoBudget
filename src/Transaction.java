import java.util.Date;

public class Transaction {
    protected int id;
    protected String currencyType; // This will eventually be type CurrencyValue
    protected double amount;
    protected int userId; // This will eventually be type User
    protected int frequency;
    protected String otherParty;
    protected Date date;
    protected Date endDate;
    protected boolean recurring;

    /**
     * Default Constructor for a Transaction.
     */
    public Transaction() {
        this.setCurrencyType("USD");
        this.setAmount(0);
        this.setId(0);
        this.setUserId(0);
        this.setFrequency(0);
        this.setDate(new Date());
    }

    /**
     * Constructor for a Transaction which takes the currency type and value.
     * @param newCurrencyType The new value for the type of currency.
     * @param newAmount The new value for amount.
     */
    public Transaction(String newCurrencyType, double newAmount) {
        new Transaction();
        this.setCurrencyType(newCurrencyType);
        this.setAmount(newAmount);
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
    public String getCurrencyType() {
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
    public Date getDate() {
        return date;
    }

    /**
     * Returns the end date of the Transaction. This only applies if the Transaction is recurring.
     * @return The value reprenting the end date of the recurring Transaction.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Assign a new identifier for the Transaction.
     * @param idToSet The new value for the id.
     */
    public void setId(int idToSet) {
        if (idToSet >= 0) {
            id = idToSet;
        }
    }

    /**
     * Assign a new currency type for Transaction.
     * @param currencyTypeToSet The new value for the currency type.
     */
    public void setCurrencyType(String currencyTypeToSet) {
        // TODO Verify that it is a valid currency from the currency table?
        currencyType = currencyTypeToSet;
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
    public void setDate(Date dateToSet) {
        date = dateToSet;
    }

    /**
     * Assign a new value for the end date of a recurring Transaction.
     * @param endDateToSet Thew new value for the end date.
     */
    public void setEndDate(Date endDateToSet) {
        endDate = endDateToSet;
    }

    /**
     * Concatenate the contents of the Transaction into a String.
     * @return A String containing fields of the Transaction.
     */
    public String toString() {
        return String.format("%.2f %s, recurring: %b", this.amount, this.currencyType, this.recurring);
    }
}
