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
    private TextField textCurrencyLastUpdate;
    @FXML
    private TextField getTextCurrencyPercentChange;
    @FXML
    private TableView<CurrencyObj> CurrencyTable;
    @FXML
    private TableColumn<?, ?> currencyName;
    @FXML
    private TableColumn<?, ?> currencyValue;
    @FXML
    private TableColumn<?, ?> currencyLastUpdate;
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
        currencyLastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
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
                        rs.getLong(4),
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
        textCurrencyLastUpdate.clear();
        getTextCurrencyPercentChange.clear();
        textCurrencyName.setPromptText("Name");
        textCurrencyValue.setPromptText("Value");
        textCurrencyLastUpdate.setPromptText("Last Update");
        getTextCurrencyPercentChange.setPromptText("24-hour % Change");
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToHomePage(ActionEvent event){ myController.setScreen(Main.HomePageID);}

    @FXML
    private void goToCrypto(ActionEvent event){
        myController.setScreen(Main.CryptoCurrenciesID);
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

    @FXML
    private void goToEditGoalsPage(ActionEvent event){ myController.setScreen(Main.EditGoalsID);
    }

}
