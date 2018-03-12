package sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OCR {

    private static final String tessBin = "C:/Program Files (x86)/Tesseract-OCR/tesseract";

    public static String getTotalReceiptPrice(String imagePath) {
        String outputFile = "./txt/latestImage";
        String total = "";

        try {
            Process tess = Runtime.getRuntime().exec(new String[] { tessBin, imagePath, outputFile });
            while (tess.isAlive()) {
                continue;
            }

            BufferedReader br = new BufferedReader(new FileReader(outputFile + ".txt"));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Total")) {
                    total = line.toLowerCase().replace("total","").replace("$","").trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

}
