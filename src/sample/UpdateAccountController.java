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
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateAccountController implements Initializable, ControlledScreen{

    ScreensController myController;
    ObservableList<String> refreshList = FXCollections.observableArrayList("1 Hour","4 Hours", "12 Hours", "On Login");

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
    @FXML
    private Button passwordButton;
    @FXML
    private Button currencyButton;
    @FXML
    private Button refreshRateButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Main.currentUser != null) {
            resetFields();
        }
        refreshComboBox.setItems(refreshList);

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
    private void changePassword(ActionEvent event){
        if (Main.currentUser.getPassword().equals(oldPassword.getText())) {
            String newPassword = newPassword1.getText();
            if (newPassword.equals(newPassword2.getText())) {
                Main.currentUser.setPassword(newPassword);
                Main.currentUser.update();

                oldPassword.setText("");
                newPassword1.setText("");
                newPassword2.setText("");
                passwordButton.setText("Changed Password");
                passwordButton.setDisable(true);
            }
        }
    }

    @FXML
    private void updatePrimaryCurrency(ActionEvent event){
        String accronym = newPrimaryCurrency.getText().trim().toUpperCase();
        int currencyId = Currency.abbrToId(accronym);
        if (currencyId != -1) {
            Main.currentUser.setPrimaryCurrency(currencyId);
            Main.currentUser.update();
            currencyButton.setText("Updated Primary Currency");
            currencyButton.setDisable(true);
        }
    }

    @FXML
    private void changeRefreshRate(ActionEvent event){
        String refreshRate = refreshComboBox.getValue().toString();
        Main.currentUser.setRefreshRate(refreshRate);
        Main.currentUser.update();
        refreshRateButton.setText("Updated Refresh Rate");
        refreshRateButton.setDisable(true);
        int refreshRateMS = 0;
        if(refreshRate.equals("1 Hour")){
            refreshRateMS = 60 * 60 * 1000;
        } else if( refreshRate.equals("5 minutes")){
            refreshRateMS = 5 * 60 * 1000;
        } else if( refreshRate.equals("10 minutes")){
            refreshRateMS = 10 * 1000;
        }
        if(refreshRateMS>0){
            Main.setTimer(refreshRateMS);
        }
    }

    @FXML
    private void updateValues(ActionEvent event) {
        resetFields();
    }

    @FXML
    private void toggleOCR(ActionEvent event) {
        if (toggleOCR.isSelected()) {
            Main.currentUser.setEnableOCR(1);
            Main.currentUser.update();
        } else {
            Main.currentUser.setEnableOCR(0);
            Main.currentUser.update();
        }
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

        passwordButton.setText("Change Password");
        passwordButton.setDisable(false);

        newPrimaryCurrency.setText(Currency.idToAbbr(Main.currentUser.getPrimaryCurrency()));
        currencyButton.setText("Update Primary Currency");
        currencyButton.setDisable(false);

        refreshComboBox.setValue(Main.currentUser.getRefreshRate());
        refreshRateButton.setText("Update Refresh Rate");
        refreshRateButton.setDisable(false);
    }
}
