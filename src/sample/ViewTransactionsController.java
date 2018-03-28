package sample;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ViewTransactionsController implements Initializable, ControlledScreen{

    ScreensController myController;
    ObservableList<String> categoryList = FXCollections.observableArrayList( "Amount","Currency","Other Party");

    @FXML
    private JFXComboBox categoryComboBox;
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<?, ?> col1;
    @FXML
    private TableColumn<?, ?> col2;
    @FXML
    private TableColumn<?, ?> col3;
    @FXML
    private TableColumn<?, ?> col4;
    @FXML
    private RadioButton payment;
    @FXML
    private RadioButton income;
    @FXML
    private RadioButton both;
    @FXML
    private AnchorPane ac;

    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ObservableList<Transaction> payData;
    private Payment[] allPayments;
    private Income[] allIncome;

    public ViewTransactionsController getController(){
        return this;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Main.currentUser!=null) {
            allIncome = Income.getAllIncome();
            allPayments = Payment.getAllPayments();
            categoryComboBox.setItems(categoryList);
            categoryComboBox.setValue(categoryList.get(0));
            displayItems();
        }
        AnchorPane.setTopAnchor(ac, 0.0);
        AnchorPane.setLeftAnchor(ac, 0.0);
        AnchorPane.setRightAnchor(ac, 0.0);
        AnchorPane.setBottomAnchor(ac, 0.0);
    }

    private void setCells(){
        col1.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        col2.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col3.setCellValueFactory(new PropertyValueFactory<>("currencyAbbreviation"));
        col4.setCellValueFactory(new PropertyValueFactory<>("otherParty"));
    }

    @FXML
    public void displayItems() {
        payData = FXCollections.observableArrayList();
        setCells();
        //TODO: use transaction/payment/income sql methods
        loadPaymentData();
        loadIncomeData();
        transactionTable.setItems(payData);
    }

    public void getPaymentDataAfterDate(int dateInMS){
        Payment[] payments = allPayments;
        for (Payment p : payments) {
            p.setTransactionType("-");
            if(p.date>dateInMS){
                payData.add(p);
            }
        }
    }

    public void getPaymentDataBeforeDate(int dateInMS){
        Payment[] payments = allPayments;
        for (Payment p : payments) {
            p.setTransactionType("-");
            if(p.date<dateInMS){
                payData.add(p);
            }
        }
    }

    public void getIncomeDataAfterDate(int dateInMS){
        Income[] income = allIncome;
        for (Income i : income) {
            i.setTransactionType("+");
            if(i.date>dateInMS){
                payData.add(i);
            }
        }
    }

    public void getIncomeDataOverAmount(String currencyAmount){
        Income[] income = allIncome;
        String[] inputStrings = currencyAmount.split(" ");
    }


    public void getIncomeDataBeforeDate(int dateInMS){
        Income[] income = allIncome;
        for (Income i : income) {
            i.setTransactionType("+");
            if(i.date<dateInMS){
                payData.add(i);
            }
        }
    }

    private void loadPaymentData(){
        Payment[] payments = allPayments;
        for (Payment p : payments) {
            p.setTransactionType("-");
            payData.add(p);
        }
    }

    private void loadIncomeData(){
        Income[] income = allIncome;
        for (Income i : income) {
            i.setTransactionType("+");
            payData.add(i);
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
