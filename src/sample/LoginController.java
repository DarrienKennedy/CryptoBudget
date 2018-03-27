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
import java.util.Timer;
import java.util.TimerTask;

public class LoginController implements Initializable, ControlledScreen{

    ScreensController myController;
    @FXML
    private JFXTextField username;
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
        if(tempUser != null){
            if(pass.equals(tempUser.getPassword())){
                Main.currentUser = tempUser;
                myController.unloadScreen(Main.HomePageID);
                myController.loadScreen(Main.HomePageID, Main.HomePageFile);
                myController.setScreen(Main.HomePageID);
                username.setText("");
                password.setText("");
            }
        }
        String currentRefreshRate = Main.currentUser.getRefreshRate();
        int refreshRateMS = 0;
        if(currentRefreshRate.equals("1 Hour")){
            refreshRateMS = 60 * 60 * 1000;
        } else if( currentRefreshRate.equals("5 minutes")){
            refreshRateMS = 5 * 1000;
        } else if( currentRefreshRate.equals("10 minutes")){
            refreshRateMS = 10 * 1000;
        }
        if(refreshRateMS>0){
            Main.setTimer(refreshRateMS);
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
