package CryptoBudget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.*;


public class CryptoBudget {
    private static final String DB_URL = getUrl();

    private static ArrayList<String> parseSite(String siteURL, ArrayList<String> userCoins, boolean searchAll, java.sql.Connection cn){
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
                    insertData(coinName, value, percentChange, cn);
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


    private static void getCoins(ArrayList<String> userCoins, boolean searchAll, java.sql.Connection cn){
        long start = System.currentTimeMillis();
        userCoins = parseSite("https://coinranking.com/?", userCoins, searchAll, cn);
        try {
            Document doc = Jsoup.connect("https://coinranking.com/?").get();
            //Get the total pages
            Elements totalPages = doc.select(".coin-list__footer .pagination__info b + b");

            int currentPage = 2;
            while (currentPage <= Integer.parseInt(totalPages.text())) {
                String newPageURL = "https://coinranking.com/?page=" + String.valueOf(currentPage);
                userCoins = parseSite(newPageURL, userCoins, searchAll, cn);
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

    //delete later
    public static String generateKey(String name){
        //generate a unique primary key for cryptovalue table
        Random rand = new Random();
        int key = rand.nextInt(100000);
        String formatted = String.format("%05d", key);
        String primaryKey = name.substring(0, 3) + key;
        return primaryKey;
    }

    //delete later
    public static void selectAll(java.sql.Connection cn){
        //select all and display table
        //TODO: will need more specific SQL select queries depending on needs of application
        String sql = "SELECT * FROM cryptoValue";
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

    //add the row in cryptoValue if it is not already there
    public static void insertData(String name, String value, String percentChange, java.sql.Connection cn){
        //check if it was already there
        String selectSQL = "SELECT * " +
                            "FROM cryptoValue " +
                            "WHERE currencyName = ?";

        boolean coinIsThere = false;

        try {
            PreparedStatement pstmt = cn.prepareStatement(selectSQL);
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            if(rs.isBeforeFirst()){
                coinIsThere = true;
            } else {
                coinIsThere = false;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        //populating a row in cryptoValue table
        //TODO: will need a lot more query methods.  update, delete, etc
        //String currentTime = new SimpleDateFormat("HH.mm.ss").format(new java.util.Date());
        java.sql.Date currentTime = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String primaryKey = generateKey(name);
        String sql = "INSERT INTO cryptoValue VALUES(?,?,?,?,?)";

        if(coinIsThere){
            sql = "UPDATE cryptoValue SET currencyValue = ? , percentChange = ?, lastUpdated = ? WHERE currencyName = ?";
        }
        try{
            PreparedStatement pstmt = cn.prepareStatement(sql);
            if(coinIsThere){
                pstmt.setString(1, value);
                pstmt.setString(2, percentChange);
                pstmt.setDate(3, currentTime);
                pstmt.setString(4, name);
                System.out.println("is there");
            }
            else {
                pstmt.setString(1, primaryKey);
                pstmt.setString(2, name);
                pstmt.setString(3, value);
                pstmt.setString(4, percentChange);
                pstmt.setDate(5, currentTime);
                System.out.println("is not there");
            }
            pstmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //delete later
    public static void createTable(java.sql.Connection cn){
        //TODO: can make some columns NOT NULL or make a better primary key, foreign key to be added later
        //creates a table to store the crypto values

        String sql = "CREATE TABLE IF NOT EXISTS cryptoValue (\n"
                + " currencyId text PRIMARY KEY,\n"
                + " currencyName text,\n"
                + " currencyValue text,\n"
                + " percentChange text,\n"
                + " lastUpdated text\n"
                + ");";
        try{
            Statement stmt = cn.createStatement();
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
        test.add("IOTA");
        java.sql.Connection cn = connect();
        createTable(cn);
        getCoins(test, false, cn);
        selectAll(cn);
    }
}
