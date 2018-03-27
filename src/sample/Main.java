package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


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
    public static Timer timer = new Timer();

    public static User currentUser;

    public static void updateTimer(int updatedTimer){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ArrayList<String> searchCoins = new ArrayList<>();
                CoinCrawler crawler = new CoinCrawler(searchCoins, true, CryptoBudgetDatabase.connection);
                crawler.updateCoins();
            }
        };
        timer.cancel();
        timer.purge();
        timer = new Timer();
        timer.schedule(timerTask, 0,updatedTimer);
    }

    public static void setTimer(int initTimer){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ArrayList<String> searchCoins = new ArrayList<>();
                CoinCrawler crawler = new CoinCrawler(searchCoins, true, CryptoBudgetDatabase.connection);
                crawler.updateCoins();
            }
        };
        timer.schedule(timerTask, 0, initTimer);
    }

    public static long dateToLong(String date){
        if(date == null) return 0;
        //check if real date if not return 0;
        if(!date.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")){
            return 0;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = sdf.parse(date);
            long epoch = dt.getTime();
            return epoch/1000;
        } catch(Exception e) {
            return 0;
        }
    }

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
        primaryStage.setTitle("CryptoBudget");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        CryptoBudgetDatabase db = new CryptoBudgetDatabase();
        db.createDatabase();
        if (User.getUser("admin") == null) {
            createAdmin();
        }

        ArrayList<String> searchCoins = new ArrayList<>();
        CoinCrawler crawler = new CoinCrawler(searchCoins, false, CryptoBudgetDatabase.connection);
        crawler.updateCoins();
        launch(args);
    }

    public static void createAdmin() {
        User admin = new User();
        admin.setUserName("admin");
        admin.setPassword("password");
        admin.create();
    }
}
