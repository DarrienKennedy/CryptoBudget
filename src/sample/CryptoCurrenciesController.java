package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CryptoCurrenciesController implements Initializable, ControlledScreen{

    ScreensController myController;
    @FXML
    private TextField textCurrencyName;
    @FXML
    private TextField textCurrencyValue;
    @FXML
    private TextField textCurrencyAbbr;
    @FXML
    private TextField getTextCurrencyPercentChange;
    @FXML
    private TableView<CurrencyObj> CurrencyTable;
    @FXML
    private TableColumn<?, ?> currencyName;
    @FXML
    private TableColumn<?, ?> currencyValue;
    @FXML
    private TableColumn<?, ?> currencyAbbr;
    @FXML
    private TableColumn<?, ?> currencyPercentChange;


    //private ArrayList<CurrencyObj> currencyList = new ArrayList();
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ObservableList<CurrencyObj> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        //setTextBoxes();
        setCells();
        loadDataFromDatabase();
    }


    private void setCells(){
        currencyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        currencyValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        currencyAbbr.setCellValueFactory(new PropertyValueFactory<>("abbr"));
        currencyPercentChange.setCellValueFactory(new PropertyValueFactory<>("percentChange"));

    }

    private void loadDataFromDatabase(){
        String sql = "SELECT * FROM CURRENCYVALUE";
        try {
            ps = CryptoBudgetDatabase.connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                //new Goal(name, amount, date, description)
                //System.out.println(rs.getString(3) + " " + rs.getDouble(4) + " " + rs.getInt(5) + " " + rs.getString(6));
                data.add(new CurrencyObj(rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(6),
                        rs.getString(5)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CurrencyTable.setItems(data);
    }

    private void setTextBoxes(){
        textCurrencyName.clear();
        textCurrencyValue.clear();
        textCurrencyAbbr.clear();
        getTextCurrencyPercentChange.clear();
        textCurrencyName.setPromptText("Name");
        textCurrencyValue.setPromptText("Value");
        textCurrencyAbbr.setPromptText("Abbreviation");
        getTextCurrencyPercentChange.setPromptText("24-hour % Change");
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToHomePage(ActionEvent event){
        myController.unloadScreen(Main.HomePageID);
        myController.loadScreen(Main.HomePageID, Main.HomePageFile);
        myController.setScreen(Main.HomePageID);
    }

    @FXML
    private void goToCrypto(ActionEvent event){
        myController.unloadScreen(Main.CryptoCurrenciesID);
        myController.loadScreen(Main.CryptoCurrenciesID, Main.CryptoCurrenciesFile);
        myController.setScreen(Main.CryptoCurrenciesID);
    }

    @FXML
    private void goToUpdateAccount(ActionEvent event){
        myController.unloadScreen(Main.UpdateAccountID);
        myController.loadScreen(Main.UpdateAccountID, Main.UpdateAccountFile);
        myController.setScreen(Main.UpdateAccountID);
    }

    @FXML
    private void goToViewTransactions(ActionEvent event) throws IOException {
        myController.unloadScreen(Main.ViewTransactionsID);
        myController.loadScreen(Main.ViewTransactionsID, Main.ViewTransactionsFile);
        myController.setScreen(Main.ViewTransactionsID);
    }

    @FXML
    private void goToAddTransaction(ActionEvent event){
        myController.unloadScreen(Main.AddTransactionID);
        myController.loadScreen(Main.AddTransactionID, Main.AddTransactionFile);
        myController.setScreen(Main.AddTransactionID);
    }

    @FXML
    private void goToLoginPage(ActionEvent event){
        myController.unloadScreen(Main.LoginID);
        myController.loadScreen(Main.LoginID, Main.LoginFile);
        myController.setScreen(Main.LoginID);
    }

    @FXML
    private void goToEditGoalsPage(ActionEvent event){
        myController.unloadScreen(Main.EditGoalsID);
        myController.loadScreen(Main.EditGoalsID, Main.EditGoalsFile);
        myController.setScreen(Main.EditGoalsID);
    }

}
