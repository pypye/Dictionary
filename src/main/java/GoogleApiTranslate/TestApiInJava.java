package GoogleApiTranslate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TestApiInJava {

    public static void main(String[] args) throws IOException {
        String text = "Hello World!";
        //Translated text: Hallo Welt!
        System.out.println("Translated text: " + translate("", "vi", text));
    }

    private static String translate(String langFrom, String langTo, String text)
            throws IOException {
        // INSERT YOU URL HERE
        String urlStr =
                "https://script.google.com/macros/s/AKfycbzxtNpZD2Ogs4oeUnj8nTaCmPlKwgwsLWPasyIsLQPB_WXvKdKU/exec"
                        +
                        "?q=" + URLEncoder.encode(text, "UTF-8") +
                        "&target=" + langTo +
                        "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

}