package sample;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.text.html.ListView;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.*;

public class EditGoalsController implements Initializable, ControlledScreen{

    ScreensController myController;

    @FXML
    private JFXButton add;
    public Button addButton;
    public TextField textDate;
    public TextField textAmount;
    public TextField textName;
    public TextField textDescription;

    public TextField getTextAmount() {
        return textAmount;
    }

    public javafx.scene.control.ListView<JFXButton> buttons;

    //Initialize once user class is done
    //public ObservableList<Goal> goals;
    public ArrayList<Goal> goalList = new ArrayList();

    private ObservableList<JFXButton> list = FXCollections.observableArrayList();
    int index = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textDate.setPromptText("Date");
        textAmount.setPromptText("Amount");
        textName.setPromptText("Name");
        textDescription.setPromptText("Description");
        /*
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        };
        */

        buttons.setItems(list);
        while (index < 4) {
            JFXButton i = new JFXButton("Update");
            i.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent event) {
                    //update by set new values for goal
                    //goals[index].setGoalName();

                }
            });
            list.add(i);
            index++;
        }
    }

    @FXML
    public void handleAddButton(ActionEvent e){
        goalList.add(new Goal(new Date(),
                textName.getText(),
                Double.valueOf(textAmount.getText()),
                textDescription.getText()));
        textDate.clear();
        textAmount.clear();
        textName.clear();
        textDescription.clear();

    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void setLabel( EventType<javafx.scene.input.MouseEvent> mouseClicked){

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
    private void goToAddTransaction(ActionEvent event){
        myController.setScreen(Main.AddTransactionID);
    }

    @FXML
    private void goToLoginPage(ActionEvent event){
        myController.setScreen(Main.LoginID);
    }

    @FXML
    private void goToHomePage(ActionEvent event){ myController.setScreen(Main.HomePageID);
    }

    @FXML
    private void goToEditGoalsPage(ActionEvent event){ myController.setScreen(Main.EditGoalsID);
    }





}
