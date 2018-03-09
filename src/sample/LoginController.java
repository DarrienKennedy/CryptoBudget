package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable, ControlledScreen{

    ScreensController myController;
    @FXML
    private JFXTextArea username;
    @FXML
    private JFXPasswordField password;
    private String pass;
    private String user;
    private User tempUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }


    @FXML
    private void validateLogin(ActionEvent event){
        pass = password.getText();
        user = username.getText();
        loadUserFromDatabase();
        if(!user.isEmpty()){
            if(pass.equals(tempUser.getPassword())){
                Main.currentUser = tempUser;
                myController.setScreen(Main.HomePageID);
                username.setText("");
                password.setText("");
            }
        }
        else{
            myController.setScreen(Main.LoginID);
        }

    }

    @FXML
    private void goToCreateAccount(ActionEvent event){
        myController.setScreen(Main.CreateAccountID);
    }


    private void loadUserFromDatabase(){
        tempUser = User.getUser(user);
    }
}
