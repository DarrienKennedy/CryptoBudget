package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        frequencyComboBox.setItems(frequencyList);
        frequencyComboBox.setValue("One Time");
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
    private void goToViewTransactions(ActionEvent event){
        myController.setScreen(Main.ViewTransactionsID);
    }

    @FXML
    private void goToLoginPage(ActionEvent event){
        myController.setScreen(Main.LoginID);
    }

    @FXML
    private void goToEditGoalsPage(ActionEvent event){ myController.setScreen(Main.EditGoalsID);
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
        // TODO implement this once currency class is done
        // TODO required field, make it behave correctly
        //Currency currency = Currency.getCurrency(currency.getText());
        // TODO implement date parsing from string
        // String date = dateField.getText();

        String otherParty = otherPartyField.getText();
        boolean typePayment = paymentOption.isSelected();
        boolean typeIncome = incomeOption.isSelected();

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
            newPayment.setCurrencyType(0); // TODO fix this with the currency class object
            //newPayment.setDate(100); // TODO Fix date here
            newPayment.setOtherParty(otherParty);
            newPayment.setFrequency(frequency);
            newPayment.setUserId(0);
            newPayment.setUserId(Main.currentUser.getUserId());
            if (!missingRequired) {
                newPayment.create();
            }
            myController.setScreen(Main.ViewTransactionsID);
        } else if (typeIncome) {
            if (newIncome == null) {
                newIncome = new Income();
            }
            // TODO make same changes as above.
            newIncome.setAmount(amount);
            newIncome.setCurrencyType(0);
            //newIncome.setDate(100);
            newIncome.setOtherParty(otherParty);
            newIncome.setFrequency(frequency);
            newIncome.setUserId(0);
            newIncome.setUserId(Main.currentUser.getUserId());
            if (!missingRequired) {
                newIncome.create();
            }
            myController.setScreen(Main.ViewTransactionsID);
        } else {
            // TODO highlight or color the check boxes to signify that it is required
        }
    }

    @FXML
    private void useOCR(ActionEvent event) {
        String imagePath = imagePathField.getText();

        // TODO do ocr
    }



}
