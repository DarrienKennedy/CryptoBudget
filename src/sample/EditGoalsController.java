package sample;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.text.html.ListView;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EditGoalsController implements Initializable, ControlledScreen{

    ScreensController myController;
    @FXML
    private Button addButton;
    @FXML
    private TextField textDate;
    @FXML
    private TextField textAmount;
    @FXML
    private TextField textName;
    @FXML
    private TextField textDescription;
    @FXML
    private TableView<Goal> goalsTable;
    @FXML
    private TableColumn<?, ?> dateCol;
    @FXML
    private TableColumn<?, ?> amountCol;
    @FXML
    private TableColumn<?, ?> nameCol;
    @FXML
    private TableColumn<?, ?> descriptionCol;

    private ArrayList<Goal> goalList = new ArrayList();
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ObservableList<Goal> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        setTextBoxes();
        setCells();
        loadDataFromDatabase();
    }

    @FXML
    public void handleAddButton(ActionEvent e) throws SQLException {
        //TODO: real dates, and ability to select goals from table to update isdone and current amount
        //TODO: remove Goals.create() db method?
        data.clear();
        double amount = Double.valueOf(textAmount.getText());
        int date = Integer.valueOf(textDate.getText());
        String name = textName.getText();
        String description = textDescription.getText();
        String sql = "INSERT INTO GOALS (USERID, GOALNAME, GOALAMOUNT, GOALDATE, GOALDESCRIPTION, ISDONE, CURRENTAMOUNT)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = CryptoBudgetDatabase.connection.prepareStatement(sql);
            ps.setInt(1, 000); //TODO: valid user id
            ps.setString(2, name);
            ps.setDouble(3, amount);
            ps.setInt(4, date);
            ps.setString(5, description);
            ps.setBoolean(6, false);
            ps.setDouble(7, 0.00);
            int confirm = ps.executeUpdate();
            if(confirm == 1){
                System.out.println("DB Insert successful.");
                loadDataFromDatabase();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally{
            ps.close();
        }
        goalList.add(new Goal(name, amount, date, description));
        setTextBoxes();

    }

    private void setCells(){
        dateCol.setCellValueFactory(new PropertyValueFactory<>("goalDate"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("finalGoal"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("goalName"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("goalDescription"));

    }

    private void loadDataFromDatabase(){
        String sql = "SELECT * FROM GOALS";
        try {
            ps = CryptoBudgetDatabase.connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                //new Goal(name, amount, date, description)
                //System.out.println(rs.getString(3) + " " + rs.getDouble(4) + " " + rs.getInt(5) + " " + rs.getString(6));
                data.add(new Goal(rs.getString(3),
                        rs.getDouble(4),
                        rs.getInt(5),
                        rs.getString(6)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        goalsTable.setItems(data);
    }

    private void setTextBoxes(){
        textDate.clear();
        textAmount.clear();
        textName.clear();
        textDescription.clear();
        textDate.setPromptText("Date");
        textAmount.setPromptText("Amount");
        textName.setPromptText("Name");
        textDescription.setPromptText("Description");
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void setLabel( EventType<javafx.scene.input.MouseEvent> mouseClicked){

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
