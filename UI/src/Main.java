package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


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

    @Override
    public void start(Stage primaryStage) {

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.LoginID, Main.LoginFile);
        mainContainer.loadScreen(Main.HomePageID, Main.HomePageFile);
        mainContainer.loadScreen(Main.CryptoCurrenciesID, Main.CryptoCurrenciesFile);
        mainContainer.loadScreen(Main.UpdateAccountID, Main.UpdateAccountFile);
        mainContainer.loadScreen(Main.ViewTransactionsID, Main.ViewTransactionsFile);
        mainContainer.loadScreen(Main.AddTransactionID, Main.AddTransactionFile);

        mainContainer.setScreen(Main.HomePageID);

        //Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}