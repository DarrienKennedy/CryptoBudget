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

import java.net.URL;
import java.util.ResourceBundle;

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
