package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class EditTransactionController implements Initializable, ControlledScreen {
    ScreensController myController;
    ObservableList<String> frequencyList = FXCollections.observableArrayList("One Time", "Weekly","Monthly", "Yearly");

    @FXML
    private AnchorPane ac;
    @FXML
    private JFXTextField amountField;
    @FXML
    private JFXTextField currencyField;
    @FXML
    private JFXComboBox frequencyComboBox;
    @FXML
    private JFXTextField otherPartyField;
    @FXML
    private DatePicker datePicker;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        frequencyComboBox.setItems(frequencyList);
        if(Main.currentUser != null) {
            Transaction transaction = ViewTransactionsController.currentlyEditting;
            amountField.setText(String.format("%.2f", transaction.getAmount()));
            currencyField.setText(Currency.idToAbbr(transaction.getCurrencyType()));
            //frequencyComboBox.setValue(transaction.getFrequency());
            otherPartyField.setText(transaction.getOtherParty());
            //datePicker.setValue(transaction.getDate());
        }
        AnchorPane.setTopAnchor(ac, 0.0);
        AnchorPane.setLeftAnchor(ac, 0.0);
        AnchorPane.setRightAnchor(ac, 0.0);
        AnchorPane.setBottomAnchor(ac, 0.0);
    }

    @FXML
    public void update() {
        System.out.println("done");
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

    @FXML
    private void goToViewGoals(ActionEvent event){
        myController.unloadScreen(Main.ViewGoalsID);
        myController.loadScreen(Main.ViewGoalsID, Main.ViewGoalsFile);
        myController.setScreen(Main.ViewGoalsID);
    }

}
