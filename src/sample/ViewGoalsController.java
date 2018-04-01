package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ViewGoalsController implements Initializable, ControlledScreen {
    ScreensController myController;
    private Goal[] allGoals;
    private ObservableList<Goal> goalData;

    @FXML
    private TableView<Goal> goalsTable;
    @FXML
    private TableColumn<?, ?> dateCol;
    @FXML
    private TableColumn<?, ?> amountCol;
    @FXML
    private TableColumn<?, ?> nameCol;
    @FXML
    private TableColumn<?, ?> descriptionCol;
    @FXML
    private AnchorPane ac;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        if(Main.currentUser != null){
            allGoals = Goal.getAllGoals();
            goalData = FXCollections.observableArrayList();;
            setCells();
            loadData();
        }

        AnchorPane.setTopAnchor(ac, 0.0);
        AnchorPane.setLeftAnchor(ac, 0.0);
        AnchorPane.setRightAnchor(ac, 0.0);
        AnchorPane.setBottomAnchor(ac, 0.0);

    }

    private void loadData(){
        for(Goal g : allGoals){
            goalData.add(g);
        }
        goalsTable.setItems(goalData);
    }

    private void setCells(){
        dateCol.setCellValueFactory(new PropertyValueFactory<>("goalDate"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("finalGoal"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("goalName"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("goalDescription"));

    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void setLabel( EventType<MouseEvent> mouseClicked){

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

}
