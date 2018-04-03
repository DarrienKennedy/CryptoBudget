package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EditGoalsController implements Initializable, ControlledScreen{

    ScreensController myController;
    private Goal newGoal;
    private Goal[] allGoals;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ObservableList<Goal> data;

    @FXML
    private Button addButton;
    @FXML
    private JFXButton logGoalButton;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField amountField;
    @FXML
    private JFXTextField descriptionField;
    @FXML
    private JFXTextField amountDisplay;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private ListView<Goal> listView;
    @FXML
    private AnchorPane ac;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Main.currentUser!=null){
            data = FXCollections.observableArrayList();
            getAllGoals();
            setCells();
            //clearTextBoxes();

            listView.setOnMouseClicked(e -> {
                Goal g = listView.getSelectionModel().getSelectedItem();
                amountDisplay.setText(Double.toString(g.getFinalGoal()));
            });
        }
        AnchorPane.setTopAnchor(ac, 0.0);
        AnchorPane.setLeftAnchor(ac, 0.0);
        AnchorPane.setRightAnchor(ac, 0.0);
        AnchorPane.setBottomAnchor(ac, 0.0);
    }

    @FXML
    public void handleAddButton(ActionEvent e) throws SQLException {
        //TODO: real dates, and ability to select goals from table to update isdone and current amount
        //data.clear();
        //getAllGoals();
        int currentUserId;
        double amount = Double.valueOf(amountField.getText());
        String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String name = nameField.getText();
        String description = descriptionField.getText();
        if(Main.currentUser != null){
            currentUserId = Main.currentUser.getUserId();
        } else {
            currentUserId = 0001;
        }

        if(newGoal == null){
            newGoal = new Goal();
        }
        newGoal.setUserId(currentUserId);
        newGoal.setGoalName(name);
        newGoal.setFinalGoal(amount);
        newGoal.setGoalDate(date);
        newGoal.setGoalDescription(description);
        //newGoal.setDone(false);
        //newGoal.setCurrentAmount(0);
        newGoal.create();
        getAllGoals();
        //data.add(newGoal);
        clearTextBoxes();
        //setCells();

    }

    private void getAllGoals(){
        allGoals = Goal.getAllGoals();
        data.clear();
        for(Goal g : allGoals){
            data.add(g);
        }
    }

    private void setCells(){
        listView.setItems(data);
        listView.setCellFactory(new Callback<ListView<Goal>, ListCell<Goal>>() {
            @Override
            public ListCell<Goal> call(ListView<Goal> param) {
                ListCell<Goal> cell = new ListCell<Goal>(){
                    @Override
                    protected void updateItem(Goal g, boolean bln){
                        super.updateItem(g, bln);
                        if (g != null){
                            setText(g.toString());
                        }
                    }
                };
                return cell;
            }
        });
    }
    private void clearTextBoxes(){
        if(datePicker.getValue() != null){
            datePicker.setValue(null);
        }
        nameField.clear();
        amountField.clear();
        descriptionField.clear();
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
