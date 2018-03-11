package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateAccountController implements Initializable, ControlledScreen{

    ScreensController myController;
    ObservableList<String> refreshList = FXCollections.observableArrayList("1 Hour","12 Hours", "24 Hours", "On Login");

    @FXML
    private JFXComboBox refreshComboBox;
    @FXML
    private JFXPasswordField oldPassword;
    @FXML
    private JFXPasswordField newPassword1;
    @FXML
    private JFXPasswordField newPassword2;
    @FXML
    private JFXTextField newPrimaryCurrency;
    @FXML
    private JFXToggleButton toggleOCR;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newPrimaryCurrency.setText("USD");

        refreshComboBox.setItems(refreshList);
        refreshComboBox.setValue("1 Hour");
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToHomePage(ActionEvent event){
        goToPage(Main.HomePageID);
    }

    @FXML
    private void goToCrypto(ActionEvent event){
        goToPage(Main.CryptoCurrenciesID);
    }

    @FXML
    private void goToUpdateAccount(ActionEvent event){
        goToPage(Main.UpdateAccountID);
    }

    @FXML
    private void goToViewTransactions(ActionEvent event){
        goToPage(Main.ViewTransactionsID);
    }

    @FXML
    private void goToAddTransaction(ActionEvent event){
        goToPage(Main.AddTransactionID);
    }

    @FXML
    private void goToLoginPage(ActionEvent event){
        goToPage(Main.LoginID);
    }

    @FXML
    private void goToEditGoalsPage(ActionEvent event){
        goToPage(Main.EditGoalsID);
    }

    @FXML
    private void changePassword(ActionEvent event){
        //if(oldPassword.getText() =)

    }

    @FXML
    private void changePCurrency(ActionEvent event){


    }

    @FXML
    private void changeRefreshRate(ActionEvent event){
        String refreshRate = refreshComboBox.getValue().toString();

    }

    private void goToPage(String pageId) {
        myController.setScreen(pageId);
        resetFields();
    }

    private void resetFields() {
        oldPassword.setText("");
        newPassword1.setText("");
        newPassword2.setText("");

        if (Main.currentUser.getOCR() == 1) {
            toggleOCR.setSelected(true);
        } else {
            toggleOCR.setSelected(false);
        }

    }
}
