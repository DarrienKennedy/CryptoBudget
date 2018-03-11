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
    ObservableList<String> categoryList = FXCollections.observableArrayList("Date", "Amount","Category","Description");

    @FXML
    private JFXComboBox categoryComboBox;
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<?, ?> dateCol;
    @FXML
    private TableColumn<?, ?> amountCol;
    @FXML
    private TableColumn<?, ?> categoryCol;
    @FXML
    private TableColumn<?, ?> descriptionCol;

    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ObservableList<Transaction> payData;
    private ObservableList<Transaction> incomeData;
    //private ArrayList<Income> allIncome;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        payData = FXCollections.observableArrayList();
        incomeData = FXCollections.observableArrayList();
        categoryComboBox.setItems(categoryList);
        categoryComboBox.setValue("Date");
        setCells();
        loadPaymentData();
        loadIncomeDate();
    }

    private void setCells(){
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("currencyType"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
    }

    private void loadPaymentData(){
        String pay = "SELECT * FROM PAYMENT";
        try {
            ps = CryptoBudgetDatabase.connection.prepareStatement(pay);
            rs = ps.executeQuery();
            //constructor(tpye,amount,party,date)
            while(rs.next()){
                System.out.println("[p]from db " + rs.getInt(6) + " " + rs.getDouble(3) + " " + rs.getString(9) + " " + rs.getInt(4));
                //Payment p = new Payment(rs.getInt(6) + " " + rs.getDouble(3) + " " + rs.getString(9) + " " + rs.getInt(4)));
                payData.add((Transaction) new Payment(rs.getInt(6),
                        rs.getDouble(3),
                        rs.getString(9),
                        rs.getInt(4)));
                        //"Payment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        transactionTable.setItems(payData);

    }

    private void loadIncomeDate(){
        String income = "SELECT * FROM INCOME";
        //allIncome = new ArrayList<>(Arrays.asList(Income.getAllIncome()));
        try {
            ps = CryptoBudgetDatabase.connection.prepareStatement(income);
            rs = ps.executeQuery();
            //constructor(tpye,amount,party,date)
            while(rs.next()){
                System.out.println("[i]from db " + rs.getInt(6) + " " + rs.getDouble(3) + " " + rs.getString(9) + " " + rs.getInt(4));
                incomeData.add((Transaction) new Income(rs.getInt(6),
                        rs.getDouble(3),
                        rs.getString(9),
                        rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        transactionTable.setItems(incomeData);
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToHomePage(ActionEvent event){
        myController.setScreen(Main.HomePageID);
    }
    @FXML
    private void goToCrypto(ActionEvent event){
        myController.setScreen(Main.CryptoCurrenciesID);
    }

    @FXML
    private void goToUpdateAccount(ActionEvent event){
        myController.setScreen(Main.UpdateAccountID);
    }

    @FXML
    private void goToAddTransaction(ActionEvent event){
        myController.setScreen(Main.AddTransactionID);
    }

    @FXML
    private void goToLoginPage(javafx.event.ActionEvent event){
        myController.setScreen(Main.LoginID);
    }

    @FXML
    private void goToEditGoalsPage(ActionEvent event){ myController.setScreen(Main.EditGoalsID);
    }


}
