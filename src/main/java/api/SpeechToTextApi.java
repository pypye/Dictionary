package api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class SpeechToTextApi  {
    public static String uploadFileSpeech(String wordForm) {
        try {
            URL url = new URL("https://api.assemblyai.com/v2/upload");
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            String authHeaderKey = "16ee3d9d2ee040aa9a63af9ca005a55e";
            request.setRequestProperty("authorization", authHeaderKey);
            request.setRequestProperty("Transfer-Encoding", "chunked");
            request.setRequestMethod("POST");
            request.setDoOutput(true);

            File file = new File(wordForm);
            byte[] bytes = new byte[(int) file.length()];
            try (OutputStream os = request.getOutputStream()) {
                FileInputStream fis = new FileInputStream(file);
                fis.read(bytes);
                //byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(bytes, 0, bytes.length);
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

            inputStream.close();

            JSONObject url_path = new JSONObject(
                    StringEscapeUtils.unescapeHtml4(response.toString()));
            //System.out.println(url_path.toString());
            return url_path.getString("upload_url");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return "error_upload_file";
    }
    public static String postSpeechToTextApi(String wordForm) {
        try {
            URL url = new URL("https://api.assemblyai.com/v2/transcript");
            HttpURLConnection request = (HttpURLConnection) url.openConnection();

            String authHeaderKey = "16ee3d9d2ee040aa9a63af9ca005a55e";
            request.setRequestProperty("authorization", authHeaderKey);
            request.setRequestProperty("content-type", "application/json; utf-8");
            request.setRequestMethod("POST");
            request.setDoOutput(true);
            String jsonInputString = "{\"audio_url\": " + "\"" + wordForm + "\"}";
            //System.out.println(jsonInputString);
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

            inputStream.close();

            JSONObject id = new JSONObject(StringEscapeUtils.unescapeHtml4(response.toString()));
            //System.out.println(id.getString("id"));
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
            if(textTranslated.get("status").equals("error")) {
                return "error";
            }
            return textTranslated.get("text").toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }
    public static String getSpeechToText(){
        //String IdStoredVoice = postSpeechToTextApi("C:/Users/hoang/Downloads/hello.wav");
        //System.out.println(IdStoredVoice);
        String url_path = uploadFileSpeech("src/main/java/data/temp.wav");
        String idApiServer = postSpeechToTextApi(url_path);
        String WordTranslate = "";
        while (true) {
            WordTranslate = getSpeechToTextApi(idApiServer);
            if (Objects.equals(WordTranslate, "error") || Objects.equals(WordTranslate, "null")) {
                continue;
            }
            break;
        }

        //System.out.println(WordTranslate);

        return WordTranslate;
    }
    public static void main(String[] args) throws Exception {

        System.out.println(getSpeechToText());
    }

}