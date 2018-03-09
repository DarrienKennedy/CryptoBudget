package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable, ControlledScreen{

    private ScreensController myController;
    @FXML
    private JFXTextArea username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    private int userID = 1;
    private User cUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToHomePage(ActionEvent event){
        cUser = new User(username.getText(), firstName.getText(), lastName.getText(), password.getText());
        cUser.setUserId(userID);
        cUser.create();
        myController.setScreen(Main.HomePageID);

    }

    @FXML
    private void goToLogin(ActionEvent event){
        myController.setScreen(Main.LoginID);
    }

}
