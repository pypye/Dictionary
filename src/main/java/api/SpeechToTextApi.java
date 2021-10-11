package api;

import java.io.OutputStream;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class SpeechToTextApi {
    public static String postSpeechToTextApi(String wordForm) {
        try {
            URL url = new URL("https://api.assemblyai.com/v2/transcript");
            HttpURLConnection request = (HttpURLConnection) url.openConnection();

            String authHeaderKey = "16ee3d9d2ee040aa9a63af9ca005a55e";
            request.setRequestProperty("authorization", authHeaderKey);
            request.setRequestProperty("content-type", "application/json; utf-8");
            request.setRequestMethod("POST");
            request.setDoOutput(true);
            String jsonInputString = "{\"audio_url\": \"https://s3-us-west-2.amazonaws.com/blog.assemblyai.com/audio/8-7-2018-post/7510.mp3\"}";

            try (OutputStream os = request.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int status = request.getResponseCode();
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = inputStream.readLine()) != null) {
                response.append(inputLine.trim());
            }
            //System.out.print(response.toString());
            inputStream.close();

            JSONObject id = new JSONObject(StringEscapeUtils.unescapeHtml4(response.toString()));
            return id.getString("id");
        } catch (IOException e) {
            return "error: ";
        }
    }

    public static String getSpeechToTextApi(String wordForm) {
        try {
            String stringURL = "https://api.assemblyai.com/v2/transcript/" + wordForm;
            URL url = new URL(stringURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();

            String authHeaderKey = "16ee3d9d2ee040aa9a63af9ca005a55e";
            request.setRequestProperty("authorization", authHeaderKey);
            request.setRequestMethod("GET");

            BufferedReader inputStream = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = inputStream.readLine()) != null) {
                response.append(inputLine);
            }
            inputStream.close();

            JSONObject textTranslated = new JSONObject(StringEscapeUtils.unescapeHtml4(response.toString()));
            //System.out.println(textTranslated);

            return textTranslated.get("text").toString();

        } catch (IOException e) {
            return "error2\n";
        }
    }

    public static void main(String[] args) throws IOException {
        String IdStoredVoice = postSpeechToTextApi("C:/Users/hoang/Downloads/hello.wav");
        System.out.println(IdStoredVoice);
        while (true) {
            String WordTranslate = getSpeechToTextApi(IdStoredVoice);
            if (Objects.equals(WordTranslate, "error2") || Objects.equals(WordTranslate, "null")) {
                continue;
            }
            break;
        }
        System.out.println(getSpeechToTextApi(IdStoredVoice));
    }

}