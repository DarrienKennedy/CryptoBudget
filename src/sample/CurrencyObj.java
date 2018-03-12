package sample;

import java.util.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

public class CurrencyObj {
    protected String name;
    protected double value;
    protected long lastUpdate;
    protected String percentChange;

    public CurrencyObj(String name, double value, long lastUpdate, String percentChange){
        this.name = name;
        this.value = value;
        this.lastUpdate = lastUpdate;
        this.percentChange = percentChange;
    }

    public String getName(){
        return name;
    }

    public double getValue(){
        return value;
    }

    public String getLastUpdate(){
        if(lastUpdate == 0){
            return "Never Updated";
        }

        Date date = new Date(lastUpdate);
        Format format = new SimpleDateFormat("MM dd HH:mm:ss");
        return format.format(date);
    }

    public String getPercentChange(){
        return percentChange;
    }

}
