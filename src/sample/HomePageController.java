package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomePageController implements Initializable, ControlledScreen{

    ScreensController myController;
    @FXML
    private TableView<Goal> goalsTable;
    @FXML
    private TableColumn<?, ?> dateCol;
    @FXML
    private TableColumn<?, ?> amountCol;
    @FXML
    private TableColumn<?, ?> nameCol;
    @FXML
    private TableColumn<?, ?> progressCol;
    private ObservableList<Goal> data;
    private PreparedStatement ps;
    private ResultSet rs;
    Currency currency;
    int[] userCurrencies;
    double[] currencyValues;
    double[] amounts;
    double progress;
    double amount;
    double usdAmount;
    String currencyAbv;
    @FXML
    Label name;
    @FXML
    Label accountBalance;
    @FXML
    Label curAbv;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**setName();
        setAccountBalance();
        convertedBalance = convert(usdAmount);
        setAmountLabels();
        data = FXCollections.observableArrayList();
        setCells();
        loadDataFromDatabase();
        goalsTable.setItems(data);**/
    }

    @FXML
    private void refresh(){
        currency = new Currency(Main.currentUser.getUserId(), CryptoBudgetDatabase.connection);
        currency.updateCurrencyAmount(1, 100);
        setName();
        //setAccountBalance();
        //convert(usdAmount);
        setAmountLabels();
        data = FXCollections.observableArrayList();
        setCells();
        loadDataFromDatabase();
        goalsTable.setItems(data);
    }

    private void setName(){
        if(!Main.currentUser.getFirstName().isEmpty() && !Main.currentUser.getLastName().isEmpty()){
            name.setText(Main.currentUser.getFirstName()+ " " + Main.currentUser.getLastName());
        }
        else{
            name.setText(Main.currentUser.getUserName());
        }
     }

     private void setCells(){
     dateCol.setCellValueFactory(new PropertyValueFactory<>("goalDate"));
     amountCol.setCellValueFactory(new PropertyValueFactory<>("finalGoal"));
     nameCol.setCellValueFactory(new PropertyValueFactory<>("goalName"));
     progressCol.setCellValueFactory(new PropertyValueFactory<>("progressBar"));
     }

     private void loadDataFromDatabase(){
     String sql = "SELECT * FROM GOALS WHERE USERID = " + Main.currentUser.getUserId() + ";";
     try {
     ps = CryptoBudgetDatabase.connection.prepareStatement(sql);
     rs = ps.executeQuery();
     while(rs.next()){
     //new Goal(name, amount, date, description, progress)
     double finalAmount = rs.getDouble(4);
     progress = rs.getDouble(8)/finalAmount;
     data.add(new Goal(rs.getString(3),
     finalAmount,
     rs.getInt(5),
     rs.getString(6),
     progress));

     }
     } catch (SQLException e) {
     e.printStackTrace();
     }
     goalsTable.setItems(data);
     }

     private void setAccountBalance(){
     userCurrencies = currency.getUserCurrencies();
     currencyValues = new double[userCurrencies.length];
     amounts = new double[userCurrencies.length];
     for(int i = 0; i < userCurrencies.length; i++){
     currencyValues[i] = currency.getCurrencyValue(userCurrencies[i]);
     amounts[i] = getAmount(userCurrencies[i]);
     usdAmount = usdAmount + (currencyValues[i] * amounts[i]);
     }

     }

     private double getAmount(int currencyId){
     String getCurrencyAmount = "SELECT AMOUNTOFCURRENCY FROM ACCOUNTCURRENCIES WHERE USERID = "+ Main.currentUser.getUserId() + " AND CURRENCYID = ?;";
     try{
     ps = CryptoBudgetDatabase.connection.prepareStatement(getCurrencyAmount);
     ps.setInt(1, currencyId);
     rs = ps.executeQuery();
     amount = rs.getDouble("AMOUNTOFCURRENCY");
     } catch (SQLException e){

     }
     return amount;
     }

     private void convert(double usdAmount){
     int primId = Main.currentUser.getPrimaryCurrency();
     double primValue = currency.getCurrencyValue(primId);
     accountBalance.setText(""+usdAmount/primValue);
     }

     private void setAmountLabels(){
     String selectSQL = "SELECT CURRENCYNAME FROM CURRENCYVALUE WHERE CURRENCYID = ?;";
     try{
     ps = CryptoBudgetDatabase.connection.prepareStatement(selectSQL);
     ps.setInt(1, Main.currentUser.getPrimaryCurrency());
     rs = ps.executeQuery();
     currencyAbv = rs.getString("CURRENCYNAME");
     System.out.println(currencyAbv);
     } catch (SQLException e){

     }
     curAbv.setText(currencyAbv);
     }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToHomePage(ActionEvent event){ myController.setScreen(Main.HomePageID);}

    @FXML
    private void goToCrypto(ActionEvent event){
        myController.setScreen(Main.CryptoCurrenciesID);
    }

    @FXML
    private void goToUpdateAccount(ActionEvent event){
        myController.setScreen(Main.UpdateAccountID);
    }

    @FXML
    private void goToViewTransactions(ActionEvent event){
        myController.setScreen(Main.ViewTransactionsID);
    }

    @FXML
    private void goToAddTransaction(ActionEvent event){
        myController.setScreen(Main.AddTransactionID);
    }

    @FXML
    private void goToLoginPage(ActionEvent event){
        myController.setScreen(Main.LoginID);
    }

    @FXML
    private void goToEditGoalsPage(ActionEvent event){ myController.setScreen(Main.EditGoalsID);
    }




}
