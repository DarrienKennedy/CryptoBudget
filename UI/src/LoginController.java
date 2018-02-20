package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, ControlledScreen{

    ScreensController myController;

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
    private void goToCrypto(ActionEvent event){
        myController.setScreen(Main.CryptoCurrenciesID);
    }

}
