package api;

import org.apache.commons.text.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleAPI {

    public static String translate(String langFrom, String langTo, String text) throws IOException {
        String APIKEY = "AKfycbzxtNpZD2Ogs4oeUnj8nTaCmPlKwgwsLWPasyIsLQPB_WXvKdKU";
        String urlStr =
                        "https://script.google.com/macros/s/" + APIKEY + "/exec" +
                        "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
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
        return StringEscapeUtils.unescapeHtml4(response.toString());
    }
    public static void main(String[] args) throws IOException {
        String text = "Hello world";
        System.out.println("Translated text: " + translate("", "vi", text));
    }
}