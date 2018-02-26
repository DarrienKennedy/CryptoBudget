package sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.*;


public class CoinCrawler {
    private static Connection db;
    private static ArrayList<String> userCoins;
    private static boolean searchAll;

    public CoinCrawler(Connection db, ArrayList<String> userCoins, boolean searchAll){
        this.db = db;
        this.userCoins = userCoins;
        this.searchAll = searchAll;
    }

    public void setUserCoins(ArrayList<String> userCoins){
        this.userCoins = userCoins;
    }

    public void setSearchAll(boolean searchAll){
        this.searchAll = searchAll;
    }

    private static ArrayList<String> parseSite(String siteURL){
        String name;
        String value;
        String percentChange;
        String upOrDown;
        boolean isUp;

        try {
            Document doc = Jsoup.connect(siteURL).get();
            Elements coins = doc.select(".coin-list__body a .wrapper .grid");

            for (Element coin : coins) {
                String coinName = coin.select(".coin-name").text();
                if(searchAll){
                    name = coin.select(".coin-name").text();
                    value = coin.select(".coin-list__body__row__price__value").text();

                    upOrDown = coin.select(".coin-list__body__row__change img").attr("alt");
                    isUp = upOrDown.equals("24h change gone up");

                    percentChange = coin.select(".coin-list__body__row__change").text();

                    if (isUp) {
                        percentChange = "+" + percentChange;
                    } else {
                        percentChange = "-" + percentChange;
                    }

                    insertData(coinName, value, percentChange, db);
                } else if(userCoins.contains(coinName)) {
                    //put this into the database
                    name = coin.select(".coin-name").text();
                    value = coin.select(".coin-list__body__row__price__value").text();
                    percentChange = coin.select(".coin-list__body__row__change").text();
                    upOrDown = coin.select(".coin-list__body__row__change img").attr("alt");
                    isUp = upOrDown.equals("24h change gone up");

                    if (isUp) {
                        percentChange = "+" + percentChange;
                    } else {
                        percentChange = "-" + percentChange;
                    }
                    userCoins.remove(coinName);

                    insertData(coinName, value, percentChange, db);
                }
                if(userCoins.isEmpty() && !searchAll){
                    break;
                }
            }
        } catch (IOException e){

        }
        return userCoins;
    }


    public void getCoins(){
        userCoins = parseSite("https://coinranking.com/?");
        try {
            Document doc = Jsoup.connect("https://coinranking.com/?").get();
            //Get the total pages
            Elements totalPages = doc.select(".coin-list__footer .pagination__info b + b");

            int currentPage = 2;
            while (currentPage <= Integer.parseInt(totalPages.text())) {
                String newPageURL = "https://coinranking.com/?page=" + String.valueOf(currentPage);
                userCoins = parseSite(newPageURL);
                if(userCoins.isEmpty() && !searchAll){
                    break;
                }
                currentPage++;
            }
        } catch (IOException e) {

        }
    }

    //add the row in cryptoValue if it is not already there
    public static void insertData(String name, String value, String percentChange, java.sql.Connection cn){
        //check if it was already there
        String selectSQL = "SELECT * " +
                "FROM CURRENCYVALUE " +
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

        Date date = new Date();
        long timeMilli = date.getTime();
        String sql = "INSERT INTO CURRENCYVALUE (CURRENCYNAME, CURRENCYVALUE, LASTUPDATED, PERCENTCHANGE) VALUES ( ? , ? , ? , ? )";

        if(coinIsThere){
            sql = "UPDATE CURRENCYVALUE SET currencyValue = ? , percentChange = ?, lastUpdated = ? WHERE currencyName = ?";
        }
        try{
            PreparedStatement pstmt = cn.prepareStatement(sql);
            if(coinIsThere){
                pstmt.setString(1, value);
                pstmt.setString(2, percentChange);
                pstmt.setLong(3, timeMilli);
                pstmt.setString(4, name);
            }
            else {
                pstmt.setString(1, name);
                pstmt.setString(2, value);
                pstmt.setLong(3, timeMilli);
                pstmt.setString(4, percentChange);
            }
            pstmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}

