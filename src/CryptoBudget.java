import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CryptoBudget {

    private static Map<String, String> settings;

    public static void main(String[] args) {
        if (!CryptoBudget.checkDependencies()) {
            System.exit(1);
        }

        CryptoBudget.readSettingsFile();

        if (settings.get("enableOCR").equals("true")) {
            String filePath = "./img/sketch.png";
            String total = getTotalReceiptPrice(filePath, settings.get("tesseractBinary"));
            System.out.printf("Total Price: \"%s\"", total);
        }

        System.out.println("bye");
    }

    private static void readSettingsFile() {
        settings = new HashMap<>();
        String settingsFilePath = "./settings.conf";

        // Read from the file
        try {
            BufferedReader settingsReader = new BufferedReader(new FileReader(settingsFilePath));
            String settingsLine;
            while ((settingsLine = settingsReader.readLine()) != null) {
                // Skip lines beginning with brackets or with a comment
                char firstChar = settingsLine.charAt(0);
                if (firstChar == '[' || firstChar == '/') {
                    continue;
                }

                String[] currentSetting = settingsLine.split("=");
                String key = currentSetting[0], value = currentSetting[1];
                key = key.replace('"', ' ').trim();
                value = value.replace('"',' ').trim();
                settings.put(key, value);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Could not locate settings.conf within CryptoBudget directory.");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getTotalReceiptPrice(String imagePath, String tesseractBinaryPath ) {
        String outputFile = "./txt/latestImage";
        String total = "";

        try {
            System.out.println("~\tExecuting Tesseract:");
            Process tess = Runtime.getRuntime().exec(new String[] { tesseractBinaryPath, imagePath, outputFile });
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
            Runtime.getRuntime().exec("tesseract");
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
