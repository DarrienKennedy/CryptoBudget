package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
    private JFXDatePicker datePicker;
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
    @FXML
    private ImageView receiptImage;

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
    private void goToAddGoalsPage(ActionEvent event){
        myController.unloadScreen(Main.AddGoalsID);
        myController.loadScreen(Main.AddGoalsID, Main.AddGoalsFile);
        myController.setScreen(Main.AddGoalsID);
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
        long epoch;
        try {
            LocalDate date = datePicker.getValue();
            ZoneId zoneId = ZoneId.systemDefault();
            epoch = date.atStartOfDay(zoneId).toEpochSecond() * 1000;
        } catch (Exception e) {
            epoch = -1;
            missingRequired = true;
            System.out.println("e: date bad");
        }

        // Convert date to millisecond test
//        Date tst = new Date(epoch);
//        Format f = new SimpleDateFormat("MM dd HH:mm:ss");
//        System.out.printf("ms:%d d:%s\n", epoch, f.format(tst));

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
            newPayment.setDate(epoch);
            newPayment.setOtherParty(otherParty);
            newPayment.setFrequency(frequency);
            newPayment.setUserId(Main.currentUser.getUserId());
            if (!missingRequired) {
                newPayment.create();
                myController.unloadScreen(Main.ViewTransactionsID);
                myController.loadScreen(Main.ViewTransactionsID, Main.ViewTransactionsFile);
                myController.setScreen(Main.ViewTransactionsID);
            }
        } else if (typeIncome) {
            if (newIncome == null) {
                newIncome = new Income();
            }
            newIncome.setAmount(amount);
            newIncome.setCurrencyType(currencyId);
            newIncome.setDate(epoch);
            newIncome.setOtherParty(otherParty);
            newIncome.setFrequency(frequency);
            newIncome.setUserId(Main.currentUser.getUserId());
            if (!missingRequired) {
                newIncome.create();
                myController.unloadScreen(Main.ViewTransactionsID);
                myController.loadScreen(Main.ViewTransactionsID, Main.ViewTransactionsFile);
                myController.setScreen(Main.ViewTransactionsID);
            }
        } else {
            // TODO highlight or color the check boxes to signify that it is required
        }
    }

    @FXML
    private void useOCR(ActionEvent event) {
        String imagePath = imagePathField.getText();
        receiptImage.setImage(new Image(new File(imagePath).toURI().toString()));
        if (Main.currentUser.getOCR() == 1) {
            amountField.setText(OCR.getTotalReceiptPrice(imagePath));
        }
        imagePathField.setText("");
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
        otherPartyField.setText("");
    }

}
