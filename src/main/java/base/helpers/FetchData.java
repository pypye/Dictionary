package base.helpers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;

public class FetchData {
    private JSONObject dict = new JSONObject();
    private JSONObject word = new JSONObject();
    private JSONArray type = new JSONArray();
    private JSONArray explain = new JSONArray();
    private JSONArray example = new JSONArray();
    private String lastExplain = "";
    private String lastType = "";
    private String lastWord = "";
    private String lastPronoun = "";

    private void addExplain() {
        if (!lastExplain.equals("")) {
            JSONObject explainElement = new JSONObject();
            explainElement.put(lastExplain, example);
            explain.put(explainElement);
        }
    }

    private void addType() {
        if (!lastType.equals("")) {
            JSONObject typeElement = new JSONObject();
            typeElement.put(lastType, explain);
            type.put(typeElement);
        }
    }

    private void addWord() {
        if (!lastWord.equals("")) {
            if(dict.isNull(lastWord)){
                word.put("pronoun", lastPronoun);
                word.put("type", type);
                dict.put(lastWord, word);
            } else {
                dict.getJSONObject(lastWord).getJSONArray("type").put(type);
            }

        }
    }
    private void reset(boolean _example, boolean _explain, boolean _type, boolean _word){
        if(_example) example = new JSONArray();
        if(_explain) explain = new JSONArray();
        if(_type) type = new JSONArray();
        if(_word) word = new JSONObject();
    }
    private void resetLast(boolean _explain, boolean _type, boolean _word, boolean _pronoun) {
        if(_explain) lastExplain = "";
        if(_type) lastType = "";
        if(_word) lastWord = "";
        if(_pronoun) lastPronoun = "";
    }
    public void fetchData(String inFile, String outFile) throws IOException {
        dict = new JSONObject();
        FileInputStream fileInputStream = new FileInputStream(inFile);
        Scanner sc = new Scanner(fileInputStream);
        while (sc.hasNextLine()) {
            String WordInput = sc.nextLine();
            if (WordInput.equals("")) {
                continue;
            }
            if (WordInput.charAt(0) == '\uFEFF' || WordInput.charAt(0) == '@') {
                addExplain();
                addType();
                addWord();
                reset(true, true, true, true);
                resetLast(true, true, true, true);
                String[] data = WordInput.split("/");
                if (data[0].charAt(0) == '\uFEFF') {
                    lastWord = data[0].trim().substring(2);
                } else {
                    lastWord = data[0].trim().substring(1);
                }
                if (data.length > 1) {
                    lastPronoun = data[1].trim();
                }
            }
            if (WordInput.charAt(0) == '*') {
                addExplain();
                addType();
                reset(true, true, false, false);
                resetLast(true, true, false, false);
                lastType = WordInput.trim().substring(1);
            }
            if (WordInput.charAt(0) == '-') {
                addExplain();
                reset(true, false, false, false);
                resetLast(true, false, false, false);
                lastExplain = WordInput.trim().substring(1);
            }
            if (WordInput.charAt(0) == '=') {
                String[] data = WordInput.split("\\+");
                JSONObject exampleElement = new JSONObject();
                if (data.length > 1) {
                    exampleElement.put(data[0].trim().substring(1), data[1].trim());
                } else {
                    exampleElement.put(data[0].trim().substring(1), "");
                }
                example.put(exampleElement);
            }
        }
        addExplain();
        addType();
        addWord();
        reset(true, true, true, true);
        resetLast(true, true, true, true);
        FileWriter file = new FileWriter(outFile);
        file.write(String.valueOf(dict));
        file.flush();
    }
    public void convert(String inFile, String outFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(inFile);
        Scanner sc = new Scanner(fileInputStream);
        String lastStr = "";
        String url = "src/main/java/base/basic/dictionaries.txt";
        File file = new File(url);
        FileOutputStream outputStream = new FileOutputStream(outFile);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

        while (sc.hasNextLine()) {
            String checkStr = sc.nextLine();
            if(lastStr.equals("")) {
                lastStr = checkStr;
                outputStreamWriter.write(checkStr + '\n');
            } else {
                if(checkStr.equals("") || lastStr.equals("")){
                    outputStreamWriter.write(checkStr + '\n');
                    continue;
                }
                if(checkStr.charAt(0) == '-' && lastStr.charAt(0) == '@') {
                    outputStreamWriter.write("* word\n");
                    outputStreamWriter.write(checkStr + '\n');
                } else {
                    outputStreamWriter.write(checkStr + '\n');
                }
            }
            lastStr = checkStr;
        }
        outputStreamWriter.flush();
    }
    public static void main(String[] args) throws IOException {
        //v-e word counts: 23430
        //e-v word counts: 103871
        FetchData convertToJson = new FetchData();
        //convertToJson.convert("src/main/java/base/helpers/input/english-vietnamese.txt", "src/main/java/base/helpers/input_processed/english-vietnamese.txt");
        //convertToJson.convert("src/main/java/base/helpers/input/vietnamese-english.txt", "src/main/java/base/helpers/input_processed/vietnamese-english.txt" );
        convertToJson.fetchData("src/main/java/base/helpers/input_processed/english-vietnamese.txt", "src/main/java/base/helpers/output/english-vietnamese.json");
        convertToJson.fetchData("src/main/java/base/helpers/input_processed/vietnamese-english.txt", "src/main/java/base/helpers/output/vietnamese-english.json");
    }
}
