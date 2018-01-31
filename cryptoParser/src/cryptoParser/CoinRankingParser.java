package cryptoParser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.*;


public class CoinRankingParser {
    private static final String DB_URL = getUrl();

    private static ArrayList<String> parseSite(String siteURL, ArrayList<String> userCoins, boolean searchAll){
        try {
            Document doc = Jsoup.connect(siteURL).get();
            Elements coins = doc.select(".coin-list__body a .wrapper .grid");

            for (Element coin : coins) {
                String coinName = coin.select(".coin-name").text();
                if(searchAll){
                    System.out.println(coin.select(".coin-name").text());
                    System.out.println(coin.select(".coin-list__body__row__price__value").text());

                    String upOrDown = coin.select(".coin-list__body__row__change img").attr("alt");
                    boolean isUp = upOrDown.equals("24h change gone up");

                    String percentChange = coin.select(".coin-list__body__row__change").text();

                    if (isUp) {
                        System.out.println("+" + percentChange);
                    } else {
                        System.out.println("-" + percentChange);
                    }
                } else if(userCoins.contains(coinName)) {
                    //put this into the database
                    String name = coin.select(".coin-name").text();
                    String value = coin.select(".coin-list__body__row__price__value").text();
                    String percentChange = coin.select(".coin-list__body__row__change").text();
                    String upOrDown = coin.select(".coin-list__body__row__change img").attr("alt");
                    System.out.println(name);
                    System.out.println(value);
                    boolean isUp = upOrDown.equals("24h change gone up");

                    if (isUp) {
                        System.out.println("+" + percentChange);
                    } else {
                        System.out.println("-" + percentChange);
                    }
                    insertData(coinName, value, percentChange);
                    userCoins.remove(coinName);
                }
                if(userCoins.isEmpty() && !searchAll){
                    break;
                }
            }
        } catch (IOException e){

        }
        return userCoins;
    }


    private static void getCoins(ArrayList<String> userCoins, boolean searchAll){
        long start = System.currentTimeMillis();
        userCoins = parseSite("https://coinranking.com/?", userCoins, searchAll);
        try {
            Document doc = Jsoup.connect("https://coinranking.com/?").get();
            //Get the total pages
            Elements totalPages = doc.select(".coin-list__footer .pagination__info b + b");

            int currentPage = 2;
            while (currentPage <= Integer.parseInt(totalPages.text())) {
                String newPageURL = "https://coinranking.com/?page=" + String.valueOf(currentPage);
                userCoins = parseSite(newPageURL, userCoins, searchAll);
                if(userCoins.isEmpty() && !searchAll){
                    break;
                }
                currentPage++;
            }
        } catch (IOException e) {

        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static String getUrl(){
        //saving the database in the src folder
        String dir = (System.getProperty("user.dir"));
        String url = "jdbc:sqlite:" + dir + "\\src\\Database.db";
        return url;
    }

    public static String generateKey(String name){
        //generate a unique primary key for cryptovalue table
        Random rand = new Random();
        int key = rand.nextInt(100000);
        String formatted = String.format("%05d", key);
        String primaryKey = name.substring(0, 3) + key;
        return primaryKey;
    }

    public static void selectAll(){
        //select all and display table
        //TODO: will need more specific SQL select queries depending on needs of application
        String sql = "SELECT * FROM cryptoValue";
        java.sql.Connection cn = connect();
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("Executing query: " + sql);
            while(rs.next()){
                System.out.println(rs.getString("currencyId") + "\t"
                        + rs.getString("currencyName") + "\t"
                        + rs.getString("currencyValue") + "\t"
                        + rs.getString("percentChange") + "\t"
                        + rs.getString("lastUpdated") + "\t");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertData(String name, String value, String percentChange){
        //populating a row in cryptoValue table
        //TODO: will need a lot more query methods.  update, delete, etc
        String currentTime = new SimpleDateFormat("HH.mm.ss").format(new java.util.Date());
        String primaryKey = generateKey(name);
        String sql = "INSERT INTO cryptoValue VALUES(?,?,?,?,?)";
        try{
            java.sql.Connection cn = connect();
            PreparedStatement pstmt = cn.prepareStatement(sql);
            pstmt.setString(1, primaryKey);
            pstmt.setString(2, name);
            pstmt.setString(3, value);
            pstmt.setString(4, percentChange);
            pstmt.setString(5, currentTime);
            pstmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void createTable(){
        //TODO: can make some columns NOT NULL or make a better primary key, foreign key to be added later
        //creates a table to store the crypto values
        java.sql.Connection dbConnect = connect();
        String sql = "CREATE TABLE IF NOT EXISTS cryptoValue (\n"
                + " currencyId text PRIMARY KEY,\n"
                + " currencyName text,\n"
                + " currencyValue text,\n"
                + " percentChange text,\n"
                + " lastUpdated text\n"
                + ");";
        try{
            Statement stmt = dbConnect.createStatement();
            stmt.execute(sql);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static java.sql.Connection connect(){
        /*
            JDBC Driver:
            https://bitbucket.org/xerial/sqlite-jdbc/downloads/
            https://github.com/xerial/sqlite-jdbc
         */
        //Connect/create database
        java.sql.Connection cn = null;
        try{
            cn = DriverManager.getConnection(DB_URL);
            if(cn != null) {
                //just some testing code to display the connection and make sure it completed, can remove this
                DatabaseMetaData dbInfo = cn.getMetaData();
                //System.out.print("Connected to: " + dbInfo.getDriverName());
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return cn;
    }

    public static void main(String [] args) {
        //TODO: make sure we can add all values to database if searchAll=true
        ArrayList<String> test = new ArrayList();
        test.add("Bitcoin");
        test.add("Ethereum");
        test.add("Ripple");
        test.add("Cardano");
        connect();
        createTable();
        getCoins(test, false);
        selectAll();
    }
}
