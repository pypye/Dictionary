package base.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class DictionaryManagement {
    private final Dictionary dictionaryManagement = new Dictionary();

    public Dictionary getDictionaryManagement() {
        return dictionaryManagement;
    }

    public void insertFromCommandline(String target, String explain) {
        Word wordInput = new Word(target, explain);
        dictionaryManagement.add(wordInput);
    }

    public void insertFromFile() throws FileNotFoundException {
        String url = "src/main/java/base/basic/dictionaries.txt";
        FileInputStream fileInputStream = new FileInputStream(url);
        Scanner sc = new Scanner(fileInputStream);
        while (sc.hasNextLine()) {
            String WordInput = sc.nextLine();
            String target = WordInput.split("\t")[0];
            String explain = WordInput.split("\t")[1];
            Word wordInput = new Word(target, explain);
            dictionaryManagement.add(wordInput);
        }
    }

    public String dictionaryLookup(String find) {
        find = find.toLowerCase();
        for (Word wordInput : dictionaryManagement.getDictionaryArray()) {
            String key = wordInput.getWord_target().trim().toLowerCase();
            if (key.equals(find)) {
                return wordInput.getWord_explain();
            }
        }
        return "Cant find any words with %s\n";
    }

    public boolean dictionaryDelete(String find) {
        find = find.toLowerCase();
        for (int i = 0; i < dictionaryManagement.size(); i++) {
            String key = dictionaryManagement.get(i).getWord_target().toLowerCase();
            if (key.equals(find)) {
                dictionaryManagement.remove(i);
                return true;
            }
        }
        return false;
    }

    public void dictionaryExportToFile() throws IOException {
        String url = "src/main/java/base/basic/dictionaries.txt";
        File file = new File(url);
        FileOutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        for (int i = 0; i < dictionaryManagement.size(); i++) {
            String target = dictionaryManagement.get(i).getWord_target();
            String explain = dictionaryManagement.get(i).getWord_explain();
            String fullKey = target + "\t" + explain + "\n";
            outputStreamWriter.write(fullKey);
        }
        outputStreamWriter.flush();
    }
}
