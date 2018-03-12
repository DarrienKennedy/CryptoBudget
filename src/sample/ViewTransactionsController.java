package sample;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ObservableList<Transaction> payData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryComboBox.setItems(categoryList);
        categoryComboBox.setValue(categoryList.get(0));
    }

    private void setCells(){
        col1.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        col2.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col3.setCellValueFactory(new PropertyValueFactory<>("currencyAbbreviation"));
        col4.setCellValueFactory(new PropertyValueFactory<>("otherParty"));
    }

    @FXML
    private void displayItems() {
        payData = FXCollections.observableArrayList();
        setCells();
        loadPaymentData();
        loadIncomeData();
        transactionTable.setItems(payData);
    }

    private void loadPaymentData(){
        Payment[] payments = Payment.getAllPayments();
        for (Payment p : payments) {
            p.setTransactionType("-");
            payData.add(p);
        }
    }

    private void loadIncomeData(){
        Income[] income = Income.getAllIncome();
        for (Income i : income) {
            i.setTransactionType("+");
            payData.add(i);
        }
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
