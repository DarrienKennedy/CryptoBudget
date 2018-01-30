import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OCR {
    public static void main(String[] args) {
        if (!OCR.checkDependencies()) {
            System.exit(1);
        }

        String filePath = "./img/receipt.png";

        String total = getTotalReceiptPrice(filePath);

        System.out.printf("Total Price: \"%s\"", total);
    }

    private static String getTotalReceiptPrice(String imagePath) {
        String tessBin = "C:/Program Files (x86)/Tesseract-OCR/tesseract";
        String outputFile = "./txt/latestImage";
        String total = "";

        try {
            System.out.println("~\tExecuting Tesseract:");
            Process tess = Runtime.getRuntime().exec(new String[] { tessBin, imagePath, outputFile });
            while (tess.isAlive()) {
                continue;
            }

            System.out.println("~\tPrinting contents of file:");
            BufferedReader br = new BufferedReader(new FileReader(outputFile + ".txt"));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Total")) {
                    total = line;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

    private static boolean checkDependencies() {
        boolean depsInstalled = true;

        // Verify that tesseract is installed on the system.
        try {
            Runtime.getRuntime().exec(new String[] {"tesseract"});
        } catch (IOException e) {
            System.out.println("Missing dependency: Tesseract\n\tInstall: https://github.com/tesseract-OCR/tesseract/wiki/Downloads");
            depsInstalled = false;
        }

        // Verify that sqlite3 is installed.
//        try {
//            Runtime.getRuntime().exec("sqlite3");
//        } catch (IOException e) {
//            System.out.println("Missing dependency: SQLite3\n\tInstall: https://www.sqlite.org/download.html");
//            depsInstalled = false;
//        }

        return depsInstalled;
    }
}