package cryptoParser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CoinRankingParser {

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


    public static void main(String [] args) {
        ArrayList<String> test = new ArrayList();
        test.add("Bitcoin");
        getCoins(test, false);
    }
}
