package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomePageController implements Initializable, ControlledScreen{

    ScreensController myController;
    private ObservableList<Goal> data;
    private PreparedStatement ps;
    private ResultSet rs;
    private int index =0;
    private int goalIndex=0;
    private double maxProgress;
    int[] userCurrencies;
    double[] currencyValues;
    double[] amounts;
    double progress;
    double amount;
    double usdAmount;
    private Goal homepageGoal;
    private double primAmount;
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    @FXML
    Label name;
    @FXML
    Label accountBalance;
    @FXML
    AnchorPane ac;
    @FXML
    Label gn_label;
    @FXML
    Label ed_label;
    @FXML
    Label ga_label;
    @FXML
    Label as_label;
    @FXML
    ProgressBar g_progress;
    @FXML
    PieChart cur_piechart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Main.currentUser!=null) {
            setName();
            setAccountBalance();
            setAmountLabels();
            data = FXCollections.observableArrayList();
            loadDataFromDatabase();
            if(!data.isEmpty()){
                homepageGoal = data.remove(goalIndex);
            }
            if(homepageGoal!=null) {
                gn_label.setText("" + homepageGoal.getGoalName());
                ed_label.setText("" + homepageGoal.getGoalDate());
                ga_label.setText("" + homepageGoal.getFinalGoal());
                as_label.setText("" + homepageGoal.getCurrentAmount());
                if(homepageGoal.currentAmount!=0.0){
                    g_progress.setProgress(homepageGoal.currentAmount / homepageGoal.getFinalGoal());
                }
                else{
                    g_progress.setProgress(0.0);
                }

            }
        }
        AnchorPane.setTopAnchor(ac, 0.0);
        AnchorPane.setLeftAnchor(ac, 0.0);
        AnchorPane.setRightAnchor(ac, 0.0);
        AnchorPane.setBottomAnchor(ac, 0.0);
    }

    private void setName(){
        if(!Main.currentUser.getFirstName().isEmpty() && !Main.currentUser.getLastName().isEmpty()){
            name.setText(Main.currentUser.getFirstName()+ " " + Main.currentUser.getLastName());
        }
        else{
            name.setText(Main.currentUser.getUserName());
        }
     }

     private void loadDataFromDatabase(){
        String sql = "SELECT * FROM GOALS WHERE USERID = ?;";
        try {
            ps = CryptoBudgetDatabase.connection.prepareStatement(sql);
            ps.setInt(1, Main.currentUser.getUserId());
            rs = ps.executeQuery();
            while(rs.next()){
                //new Goal(name, amount, date, description, progress)
                String name = rs.getString(3);
                double finalAmount = rs.getDouble(4);
                int date = rs.getInt(5);
                String description = rs.getString(6);
                progress = rs.getDouble(8);
                progress = progress/finalAmount;
                if(maxProgress < progress && index < 0){
                    maxProgress = progress;
                    goalIndex = index;
                }
                else {
                    maxProgress = progress;
                    goalIndex = index;
                }
                data.add(new Goal(name, finalAmount, date, description, progress));
                }
            index++;
            }catch (SQLException e) {
                e.printStackTrace();
            }
     }

     private void setAccountBalance(){
        if(Currency.getUserCurrencies()!=null) {
            int primId = Main.currentUser.getPrimaryCurrency();
            double primValue = Currency.getCurrencyValue(primId);
            String name = null;
            userCurrencies = Currency.getUserCurrencies();
            currencyValues = new double[userCurrencies.length];
            amounts = new double[userCurrencies.length];
            String[] names = Currency.getUserCurrencyNames();
            for (int i = 0; i < userCurrencies.length; i++) {
                currencyValues[i] = Currency.getCurrencyValue(userCurrencies[i]);
                amounts[i] = getAmount(userCurrencies[i]);
                double tempUSDAmount = currencyValues[i] * amounts[i];
                usdAmount = usdAmount + (tempUSDAmount);
                primAmount = tempUSDAmount/primValue;
                String selectSQL = "SELECT CURRENCYNAME FROM CURRENCYVALUE WHERE CURRENCYVALUE = ?;";
                try{
                    ps = CryptoBudgetDatabase.connection.prepareStatement(selectSQL);
                    ps.setDouble(1, currencyValues[i]);
                    rs = ps.executeQuery();
                    name = rs.getString("CURRENCYNAME");
                } catch (SQLException e){

                }
                pieChartData.add(new PieChart.Data(name, primAmount));
            }
            cur_piechart.setData(pieChartData);

            accountBalance.setText(""+usdAmount/primValue);
        }
    }

     private double getAmount(int currencyId){
        String getCurrencyAmount = "SELECT AMOUNTOFCURRENCY FROM ACCOUNTCURRENCIES WHERE USERID = ? AND CURRENCYID = ?;";
        try{
            ps = CryptoBudgetDatabase.connection.prepareStatement(getCurrencyAmount);
            ps.setInt(1, Main.currentUser.getUserId());
            ps.setInt(2, currencyId);
            rs = ps.executeQuery();
            amount = rs.getDouble("AMOUNTOFCURRENCY");
        } catch (SQLException e){

        }
        return amount;
     }

     private void setAmountLabels(){
        String selectSQL = "SELECT CURRENCYNAME FROM CURRENCYVALUE WHERE CURRENCYID = ?;";
        try{
            ps = CryptoBudgetDatabase.connection.prepareStatement(selectSQL);
            ps.setInt(1, Main.currentUser.getPrimaryCurrency());
            rs = ps.executeQuery();
            accountBalance.setText(accountBalance.getText() + "   " +rs.getString("CURRENCYNAME") + "s");
     } catch (SQLException e){

     }
     }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToHomePage(ActionEvent event){
        myController.unloadScreen(Main.HomePageID);
        myController.loadScreen(Main.HomePageID, Main.HomePageFile);
        myController.setScreen(Main.HomePageID);
    }

    @FXML
    private void goToCrypto(ActionEvent event){
        myController.unloadScreen(Main.CryptoCurrenciesID);
        myController.loadScreen(Main.CryptoCurrenciesID, Main.CryptoCurrenciesFile);
        myController.setScreen(Main.CryptoCurrenciesID);
    }

    @FXML
    private void goToUpdateAccount(ActionEvent event){
        myController.unloadScreen(Main.UpdateAccountID);
        myController.loadScreen(Main.UpdateAccountID, Main.UpdateAccountFile);
        myController.setScreen(Main.UpdateAccountID);
    }

    @FXML
    private void goToViewTransactions(ActionEvent event) throws IOException {
        myController.unloadScreen(Main.ViewTransactionsID);
        myController.loadScreen(Main.ViewTransactionsID, Main.ViewTransactionsFile);
        myController.setScreen(Main.ViewTransactionsID);
    }

    @FXML
    private void goToAddTransaction(ActionEvent event){
        myController.unloadScreen(Main.AddTransactionID);
        myController.loadScreen(Main.AddTransactionID, Main.AddTransactionFile);
        myController.setScreen(Main.AddTransactionID);
    }

    @FXML
    private void goToLoginPage(ActionEvent event){
        myController.unloadScreen(Main.LoginID);
        myController.loadScreen(Main.LoginID, Main.LoginFile);
        myController.setScreen(Main.LoginID);
    }

    @FXML
    private void goToEditGoalsPage(ActionEvent event){
        myController.unloadScreen(Main.EditGoalsID);
        myController.loadScreen(Main.EditGoalsID, Main.EditGoalsFile);
        myController.setScreen(Main.EditGoalsID);
    }




}
