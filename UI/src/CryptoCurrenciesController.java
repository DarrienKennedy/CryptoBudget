package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class CryptoCurrenciesController implements Initializable, ControlledScreen{

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


}
