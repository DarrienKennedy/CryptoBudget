import java.util.Date;
public class Goal {
    protected int goalId;
    protected int userId;   //type = user...
    protected String goalName;
    protected double goalAmount;
    protected Date goalDate;
    protected String goalDescription;
    protected boolean isDone;
    protected double currentAmount;

    /*
     * Default constructor for a Goal
     */
    public Goal(){
        this.setGoalId(0);
        this.setUserId(0);
        this.setGoalName("DEFAULT NAME");
        this.setGoalAmount(0.00);
        this.setGoalDate(new Date());
        this.setGoalDescription("DEFAULT DESCRIPTION");
        this.setDone(false);
        this.setCurrentAmount(0.00);
    }

    /*
     * Constructor which takes a name, amount, and description
     * @param name -- name for the goal
     * @param description -- describes the nature of the goal
     */
    public Goal(String name, double amount, String description){
        new Goal();
        this.setGoalName(name);
        this.setGoalAmount(amount);
        this.setGoalDescription(description);
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
    public double getGoalAmount(){
        return goalAmount;
    }

    /*
     * @return the value for the end date of the goal
     */
    public Date getGoalDate(){
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

    /*
     * @return value for whether or not goal is completed
     */
    public boolean isDone(){
        return isDone;
    }

    /*
     * assign a goal ID to the goal
     * @param goalIdToSet -- value for the new ID
     */
    public void setGoalId(int goalIdToSet){
        if(goalId >= 0){
            goalId = goalIdToSet;
        }
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
    public void setGoalAmount(double amountToSet){
        goalAmount = amountToSet;
    }

    /*
     * sets the date for the goal to be completed
     * @param dateToSet -- end date for goal
     */
    public void setGoalDate(Date dateToSet){
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
}
