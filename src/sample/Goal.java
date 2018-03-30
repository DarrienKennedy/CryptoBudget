package sample;

import com.jfoenix.controls.JFXProgressBar;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
public class Goal {
    //TODO: return goal closest to completion
    protected int goalId;
    protected int userId;   //type = user...
    protected String goalName;
    protected double finalGoal;
    protected int goalDate;
    protected String goalDescription;
    protected boolean isDone;
    protected double currentAmount;
    protected JFXProgressBar progressBar = new JFXProgressBar();

    /*
     * Default constructor for a sample.Goal
     */
    public Goal(){
        //this.setGoalId(0);
        this.setUserId(0);
        this.setGoalName("DEFAULT NAME");
        this.setFinalGoal(100.00);
        this.setGoalDate(000);
        this.setGoalDescription("DEFAULT DESCRIPTION");
        this.setDone(false);
        this.setCurrentAmount(0.00);
        this.setProgressBar(0);
    }

    /*
     * Constructor which takes a name, amount, and description
     * @param name -- name for the goal
     * @param description -- describes the nature of the goal
     */
    public Goal(String name, double amount, int date, String description){
        new Goal();
        this.setGoalDate(date);
        this.setGoalName(name);
        this.setFinalGoal(amount);
        this.setGoalDescription(description);
    }

    /*
     * Constructor which takes a name, amount, description, and progress bar
     * @param name -- name for the goal
     * @param description -- describes the nature of the goal
     */
    public Goal(String name, double amount, int date, String description, double progress){
        new Goal();
        this.setGoalDate(date);
        this.setGoalName(name);
        this.setFinalGoal(amount);
        this.setGoalDescription(description);
        this.setProgressBar(progress);
    }

    //result.add(new Goal(id, userId, name, amount, date, description, isDone, currentAmount))
    public Goal(int goalId, int userId, String name, double amount, int date, String description, boolean isDone, double currentAmount){
        new Goal();
        this.goalId = goalId;
        this.setUserId(userId);
        this.setGoalName(name);
        this.setFinalGoal(amount);
        this.setGoalDate(date);
        this.setGoalDescription(description);
        this.setDone(isDone);
        this.setCurrentAmount(currentAmount);
    }

    /*
     * @return the value of the primary key ID of the goal
     */
    public int getGoalId(){
        return goalId;
    }

    /*
     * @return the value of the foreign key user ID for the goal
     */
    public int getUserId(){
        return userId;
    }

    /*
     * @return the value for the name of the goal
     */
    public String getGoalName(){
        return goalName;
    }

    /*
     * @return the value of the amount the goal is for
     */
    public double getFinalGoal(){
        return finalGoal;
    }

    /*
     * @return the value for the end date of the goal
     */
    public int getGoalDate(){
        return goalDate;
    }

    /*
     * @return the value of the description of the goal
     */
    public String getGoalDescription(){
        return goalDescription;
    }

    /*
     * @return the value of the current amount of the goal amount
     */
    public double getCurrentAmount(){
        return currentAmount;
    }

    public JFXProgressBar getProgressBar() { return progressBar; }

    /*
     * @return value for whether or not goal is completed
     */
    public boolean isDone(){
        //TODO: if isDone then deleteGoal()
        if(currentAmount == finalGoal){
            isDone = true;
        }
        else{
            isDone = false;
        }
        return isDone;
    }

    /*
     * assign a user ID to the goal
     * @param userIdToSet -- user ID for the goal
     */
    public void setUserId(int userIdToSet){
        if(userId >= 0){
            userId = userIdToSet;
        }
    }

    /*
     * assign a name to the goal
     * @param nameToSet -- the name for the goal
     */
    public void setGoalName(String nameToSet){
        goalName = nameToSet;
    }

    /*
     * assign a currency amount for the goal
     * @param amountToSet -- the amount for the goal
     */
    public void setFinalGoal(double amountToSet){
        finalGoal = amountToSet;
    }

    /*
     * sets the date for the goal to be completed
     * @param dateToSet -- end date for goal
     */
    public void setGoalDate(int dateToSet){
        goalDate = dateToSet;
    }

    /*
     * sets a description for the goal
     * @param descriptionToSet -- value describing the goal
     */
    public void setGoalDescription(String descriptionToSet){
        goalDescription = descriptionToSet;
    }

    /*
     * setting value for whether or not goal is completed
     * @param status -- value representing completion status
     */
    public void setDone(boolean status){
        isDone = status;
    }

    /*
     * set the current amount of goal progress
     * @param currentAmountToSet -- value for the currency amount
     */
    public void setCurrentAmount(double currentAmountToSet){
        currentAmount = currentAmountToSet;
    }

    //add an amount of currency to the goal progress
    public void addToCurrentAmount(double amount){
        currentAmount += amount;
    }

    public void setProgressBar(double progress) { progressBar.setProgress(progress); }

    /*
     * database methods
     */
    public void create(){
        try{
            String sql = "INSERT INTO GOALS (USERID, GOALAMOUNT, GOALDATE, GOALDESCRIPTION, " +
                    "ISDONE, CURRENTAMOUNT)" + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = CryptoBudgetDatabase.connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setDouble(2, finalGoal);
            ps.setInt(3, 000); //change goalDate to type int, or to  type date in cbdb.java
            ps.setString(4, goalDescription);
            ps.setBoolean(5, isDone);
            ps.setDouble(6, currentAmount);
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    //TODO: make sure goalId is set to the PK so the correct goal object is updated
    //using the primary key to update the amount for a goal
    public void updateProgress(double amt){
        try {
            this.addToCurrentAmount(amt);
            this.isDone();
            String sql = "UPDATE GOALS SET GOALAMOUNT = ? WHERE GOALID = ?";
            PreparedStatement ps = CryptoBudgetDatabase.connection.prepareStatement(sql);
            ps.setDouble(1, currentAmount);
            ps.setInt(2, goalId);
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    //sets description for the goal given a primary key ID
    public void updateDescription(String note){
        try{
            String sql = "UPDATE GOALS SET GOALDESCRIPTION = ? WHERE GOALID = ?";
            PreparedStatement ps = CryptoBudgetDatabase.connection.prepareStatement(sql);
            ps.setString(1, note);
            ps.setInt(2, goalId);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //deletes a goal from the database
    public void deleteGoal(int deleteId){
        try{
            String sql = "DELETE FROM GOALS WHERE GOALID = ?";
            PreparedStatement ps = CryptoBudgetDatabase.connection.prepareStatement(sql);
            ps.setInt(1, deleteId);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static Goal[] getAllGoals() {
        try {
            String getAll = String.format("SELECT * FROM GOALS WHERE USERID = ?;");
            PreparedStatement prep = CryptoBudgetDatabase.connection.prepareStatement(getAll);
            prep.setInt(1, Main.currentUser.getUserId());
            ResultSet rs = prep.executeQuery();
            ArrayList<Goal> result = new ArrayList();
            while (rs.next()) {
                int id = rs.getInt("GOALID");
                int userId = rs.getInt("USERID");
                String name = rs.getString("GOALNAME");
                double amount = rs.getDouble("GOALAMOUNT");
                int date = rs.getInt("GOALDATE");
                String description = rs.getString("GOALDESCRIPTION");
                boolean isDone = rs.getBoolean("ISDONE");
                double currentAmount = rs.getDouble("CURRENTAMOUNT");
                result.add(new Goal(id, userId, name, amount, date, description, isDone, currentAmount));
            }
            return result.toArray(new Goal[0]);
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return null;
    }

}
