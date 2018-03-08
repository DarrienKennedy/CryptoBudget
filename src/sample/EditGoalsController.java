package sample;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javax.swing.text.html.ListView;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class EditGoalsController implements Initializable, ControlledScreen{

    ScreensController myController;

    @FXML
    private JFXButton add;
    public javafx.scene.control.ListView<JFXButton> buttons;

    //Initialize once user class is done
    //public ObservableList<Goal> goals;

    private ObservableList<JFXButton> list = FXCollections.observableArrayList();
    int index = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
