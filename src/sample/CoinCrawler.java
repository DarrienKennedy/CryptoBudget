package sample;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.*;


public class CoinCrawler {
    private static final OkHttpClient client = new OkHttpClient.Builder().build();
    private static java.sql.Connection db = CryptoBudgetDatabase.connection;
    private static ArrayList<String> userCoins;
    private static boolean searchAll;

    public CoinCrawler(ArrayList<String> userCoins, boolean searchAll, Connection db){
        this.userCoins = userCoins;
        this.searchAll = searchAll;
        this.db = db;
    }

    public void setUserCoins(ArrayList<String> userCoins){
        this.userCoins = userCoins;
    }

    public void setSearchAll(boolean searchAll){
        this.searchAll = searchAll;
    }

    //add the row in cryptoValue if it is not already there
    private static void insertData(String name, String value, String percentChange){
        //check if it was already there
        String selectSQL = "SELECT * " +
                "FROM CURRENCYVALUE " +
                "WHERE currencyName = ?";

        boolean coinIsThere = false;

        try {
            PreparedStatement pstmt = db.prepareStatement(selectSQL);
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
            PreparedStatement pstmt = db.prepareStatement(sql);
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

    public static void updateCoins(){
        try{
            doGetRequest("https://coinranking.com/", true);
        } catch (IOException e){

        }
    }

    private static void doGetRequest(String url, boolean isFirst) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String value;
                        String percentChange;
                        String upOrDown;
                        boolean isUp;
                        String html = response.body().string();

                        Elements coins = Jsoup.parse(html).select(".coin-list__body a .wrapper .grid");
                        for (Element coin : coins) {
                            String coinName = coin.select(".coin-name").text();
                            if(searchAll) {
                                value = coin.select(".coin-list__body__row__price__value").text();

                                upOrDown = coin.select(".coin-list__body__row__change img").attr("alt");
                                isUp = upOrDown.equals("24h change gone up");

                                percentChange = coin.select(".coin-list__body__row__change").text();

                                if (isUp) {
                                    percentChange = "+" + percentChange;
                                } else {
                                    percentChange = "-" + percentChange;
                                }
                                System.out.println(coinName + " " + value + " " + percentChange);
                                insertData(coinName, value, percentChange);
                            } else if(userCoins.contains(coinName)) {
                                //put this into the database
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

                                insertData(coinName, value, percentChange);
                            }
                            if(userCoins.isEmpty() && !searchAll){
                                break;
                            }
                        }
                        if(isFirst){
                            Elements totalPages = Jsoup.parse(html).select(".coin-list__footer .pagination__info b + b");
                            int currentPage = 2;
                            while (currentPage <= Integer.parseInt(totalPages.text())) {
                                String newPageURL = "https://coinranking.com/?page=" + String.valueOf(currentPage);
                                doGetRequest(newPageURL, false);
                                if(userCoins.isEmpty() && !searchAll){
                                    break;
                                }
                                currentPage++;
                            }
                        }
                    }
                });
    }
}

