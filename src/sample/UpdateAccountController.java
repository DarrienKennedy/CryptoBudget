package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateAccountController implements Initializable, ControlledScreen{

    ScreensController myController;
    ObservableList<String> currencyList = FXCollections.observableArrayList("USD","Bitcoin", "RandomCoin");
    ObservableList<String> refreshList = FXCollections.observableArrayList("1 Min","5 Min", "10 Min");

    @FXML
    private JFXComboBox currencyComboBox;

    @FXML
    private JFXComboBox refreshComboBox;

    @FXML
    private JFXTextField oldPassword;

    @FXML
    private JFXTextField newPassword1;

    @FXML
    private JFXTextField newPassword2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currencyComboBox.setItems(currencyList);
        currencyComboBox.setValue("USD");



        refreshComboBox.setItems(refreshList);
        refreshComboBox.setValue("1 Min");


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

    @FXML
    private void changePassword(ActionEvent event){
        //if(oldPassword.getText() =)

    }

    @FXML
    private void changePCurrency(ActionEvent event){
        String pCurrency = currencyComboBox.getValue().toString();

    }

    @FXML
    private void changeRefreshRate(ActionEvent event){
        String refreshRate = refreshComboBox.getValue().toString();

    }


}
