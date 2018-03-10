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
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXPasswordField confirmationField;
    @FXML
    private JFXTextField firstNameField;
    @FXML
    private JFXTextField lastNameField;

    private int userID = 1;
    private User cUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void attemptCreateAccount(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmation = confirmationField.getText();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        boolean valid, match, unique;
        match = password.equals(confirmation);
        valid = (!password.equals("") && !username.equals(""));
        unique = (User.getUser(username) == null);

        if (valid && match && unique) {
            cUser = new User();
            cUser.setUserName(username);
            // TODO Salted password
            cUser.setPassword(password);
            cUser.setFirstName(firstName);
            cUser.setLastName(lastName);
            cUser.create();
            // Have to do a lookup to ensure that the user id is not set to 0.
            Main.currentUser = User.getUser(username);
            myController.setScreen(Main.HomePageID);
        }
    }

    @FXML
    private void goToLogin(ActionEvent event){
        myController.setScreen(Main.LoginID);
    }

}
