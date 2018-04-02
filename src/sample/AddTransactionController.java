package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTransactionController implements Initializable, ControlledScreen{

    ScreensController myController;
    ObservableList<String> frequencyList = FXCollections.observableArrayList("One Time", "Weekly","Monthly", "Yearly");

    private Payment newPayment;
    private Income newIncome;

    @FXML
    private JFXTextField imagePathField;
    @FXML
    private JFXTextField amountField;
    @FXML
    private JFXTextField currencyField;
    @FXML
    private JFXComboBox frequencyComboBox;
    @FXML
    private CheckBox paymentOption;
    @FXML
    private CheckBox incomeOption;
    @FXML
    private JFXTextField dateField;
    @FXML
    private JFXTextField otherPartyField;
    @FXML
    private RadioButton payment;
    @FXML
    private RadioButton income;
    @FXML
    private AnchorPane ac;
    @FXML
    private AnchorPane ac2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        frequencyComboBox.setItems(frequencyList);
        frequencyComboBox.setValue("One Time");
        AnchorPane.setTopAnchor(ac, 0.0);
        AnchorPane.setLeftAnchor(ac, 0.0);
        AnchorPane.setRightAnchor(ac, 0.0);
        AnchorPane.setBottomAnchor(ac, 0.0);

        AnchorPane.setTopAnchor(ac2, 0.0);
        AnchorPane.setLeftAnchor(ac2, 0.0);
        AnchorPane.setRightAnchor(ac2, 0.0);
        AnchorPane.setBottomAnchor(ac2, 0.0);
//        ToggleGroup group = new ToggleGroup();
//
//        RadioButton rb1 = new RadioButton("Payment");
//        rb1.setUserData("Payment");
//        rb1.setToggleGroup(group);
//        rb1.setSelected(true);
//
//        RadioButton rb2 = new RadioButton("Income");
//        rb2.setUserData("Income");
//        rb2.setToggleGroup(group);
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
    private void manualLog(ActionEvent event) {
        boolean missingRequired = false;
        double amount = -1;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException invalidAmountE) {
            // TODO change the color of the amount field or something
            System.out.println("e: amount bad");
            missingRequired = true;
        }
        String currencyAbbr = currencyField.getText().trim().toUpperCase();
        int currencyId = Currency.abbrToId(currencyAbbr);
        if (currencyId == -1) {
            // Default to user's primary default currency
            currencyId = Main.currentUser.getPrimaryCurrency();
        } else {
            currencyId = currencyId;
        }
        // TODO implement date parsing from string
        // String date = dateField.getText();

        String otherParty = otherPartyField.getText();
        boolean typePayment = payment.isSelected();
        boolean typeIncome = income.isSelected();

        int frequency = 0;
        String frequencySelected = frequencyComboBox.getValue().toString();
        if (frequencySelected.equals(frequencyList.get(0))) {
            frequency = 0;
        } else if (frequencySelected.equals(frequencyList.get(1))) {
            frequency = 52;
        } else if (frequencySelected.equals(frequencyList.get(2))) {
            frequency = 12;
        } else if (frequencySelected.equals(frequencyList.get(3))) {
            frequency = 1;
        }

        if (typePayment) {
            if (newPayment == null) {
                newPayment = new Payment();
            }
            newPayment.setAmount(amount);
            newPayment.setCurrencyType(currencyId);
            //newPayment.setDate(100); // TODO Fix date here
            newPayment.setOtherParty(otherParty);
            newPayment.setFrequency(frequency);
            newPayment.setUserId(0);
            newPayment.setUserId(Main.currentUser.getUserId());
            if (!missingRequired) {
                newPayment.create();
                goToScreen(Main.ViewTransactionsID);
            }
        } else if (typeIncome) {
            System.out.println("isincome");
            if (newIncome == null) {
                newIncome = new Income();
            }
            // TODO make same changes as above.
            newIncome.setAmount(amount);
            newIncome.setCurrencyType(currencyId);
            //newIncome.setDate(100);
            newIncome.setOtherParty(otherParty);
            newIncome.setFrequency(frequency);
            newIncome.setUserId(0);
            newIncome.setUserId(Main.currentUser.getUserId());
            if (!missingRequired) {
                newIncome.create();
                goToScreen(Main.ViewTransactionsID);
            }
        } else {
            // TODO highlight or color the check boxes to signify that it is required
        }
    }

    @FXML
    private void useOCR(ActionEvent event) {
        String imagePath = imagePathField.getText();
        if (Main.currentUser.getOCR() == 1) {
            amountField.setText(OCR.getTotalReceiptPrice(imagePath));
        }
    }

    private void goToScreen(String screenId) {
        myController.setScreen(screenId);
        resetFields();
    }

    private void resetFields() {
        newPayment = new Payment();
        newIncome = new Income();
        imagePathField.setText("");
        amountField.setText("");
        currencyField.setText("");
        frequencyComboBox.setValue(frequencyList.get(0));
        payment.setSelected(true);
        dateField.setText("");
        otherPartyField.setText("");
    }

}
