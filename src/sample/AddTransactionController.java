package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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
    @FXML
    private Button ocrButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        frequencyComboBox.setItems(frequencyList);
        frequencyComboBox.setValue("One Time");
        ocrButton.setOnMouseEntered(e -> ocrButton.setDisable(Main.currentUser.getOCR() == 0));
    }


    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }



    @FXML
    private void goToHomePage(ActionEvent event){ goToScreen(Main.HomePageID);}

    @FXML
    private void goToCrypto(ActionEvent event){
        goToScreen(Main.CryptoCurrenciesID);
    }

    @FXML
    private void goToUpdateAccount(ActionEvent event){
        goToScreen(Main.UpdateAccountID);
    }

    @FXML
    private void goToViewTransactions(ActionEvent event){
        goToScreen(Main.ViewTransactionsID);
    }

    @FXML
    private void goToAddTransaction(ActionEvent event){
        goToScreen(Main.AddTransactionID);
    }

    @FXML
    private void goToLoginPage(ActionEvent event){
        goToScreen(Main.LoginID);
    }

    @FXML
    private void goToEditGoalsPage(ActionEvent event){ goToScreen(Main.EditGoalsID);
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
        amountField.setText(OCR.getTotalReceiptPrice(imagePath));
        ocrButton.setText("Scanned");
        ocrButton.setDisable(true);
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
        paymentOption.setSelected(false);
        incomeOption.setSelected(false);
        dateField.setText("");
        otherPartyField.setText("");
        ocrButton.setText("Scan Image");
        ocrButton.setDisable(false);
    }

}
