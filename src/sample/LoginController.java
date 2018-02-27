package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, ControlledScreen{

    ScreensController myController;

    @FXML
    private JFXTextArea username;

    @FXML
    private JFXPasswordField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToHomePage(ActionEvent event){
        myController.setScreen(Main.HomePageID);
    }


    @FXML
    private void validateLogin(ActionEvent event){


    }

    @FXML
    private void goToCreateAccount(ActionEvent event){
        myController.setScreen(Main.CreateAccountID);
    }

    @FXML
    private void goToLogin(ActionEvent event){
        myController.setScreen(Main.LoginID);
    }

}
