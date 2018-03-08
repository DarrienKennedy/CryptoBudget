package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;


public class Main extends Application {

    public static String LoginID = "Login";
    public static String LoginFile = "Login.fxml";
    public static String HomePageID = "HomePage";
    public static String HomePageFile = "HomePage.fxml";
    public static String CryptoCurrenciesID = "CryptoCurrencies";
    public static String CryptoCurrenciesFile = "CryptoCurrencies.fxml";
    public static String UpdateAccountID = "UpdateAccount";
    public static String UpdateAccountFile = "UpdateAccount.fxml";
    public static String ViewTransactionsID = "ViewTransactions";
    public static String ViewTransactionsFile = "ViewTransactions.fxml";
    public static String AddTransactionID = "AddTransaction";
    public static String AddTransactionFile = "AddTransaction.fxml";
    public static String EditGoalsID = "EditGoals";
    public static String EditGoalsFile = "EditGoals.fxml";
    public static String CreateAccountID = "CreateAccount";
    public static String CreateAccountFile = "CreateAccount.fxml";

    //User class currently not in sample
    //public static User currentUser;


    @Override
    public void start(Stage primaryStage) {

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.LoginID, Main.LoginFile);
        mainContainer.loadScreen(Main.HomePageID, Main.HomePageFile);
        mainContainer.loadScreen(Main.CryptoCurrenciesID, Main.CryptoCurrenciesFile);
        mainContainer.loadScreen(Main.UpdateAccountID, Main.UpdateAccountFile);
        mainContainer.loadScreen(Main.ViewTransactionsID, Main.ViewTransactionsFile);
        mainContainer.loadScreen(Main.AddTransactionID, Main.AddTransactionFile);
        mainContainer.loadScreen(Main.EditGoalsID, Main.EditGoalsFile);
        mainContainer.loadScreen(Main.CreateAccountID, Main.CreateAccountFile);

        mainContainer.setScreen(Main.LoginID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        //CryptoBudgetDatabase db = new CryptoBudgetDatabase();
        //db.createDatabase();
        //launch(args);
    }
}
