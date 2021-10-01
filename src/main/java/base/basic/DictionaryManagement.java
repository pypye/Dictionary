package base.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionaryManagement = new Dictionary();

    public Dictionary getDictionaryManagement() {
        return dictionaryManagement;
    }

    public void insertFromCommandline() {
        System.out.printf("Type word amount: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.printf("Type target: ");
            String target = sc.nextLine();
            System.out.printf("Type explain: ");
            String explain = sc.nextLine();
            Word wordInput = new Word(target, explain);
            dictionaryManagement.add(wordInput);
            System.out.printf("added %s %s\n", target, explain);
        }

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

    public void dictionaryLookup() {
        System.out.printf("Type word to translate: ");
        Scanner sc = new Scanner(System.in);
        String find = sc.nextLine().trim();
        for (Word wordInput : dictionaryManagement.getDictionaryArray()) {
            String key = wordInput.getWord_target().trim();
            if (key.equals(find)) {
                wordInput.writeWord();
                return;
            }
        }
        System.out.printf("Cant find any words with %s\n", find);
    }

    public void dictionaryDelete() {
        System.out.printf("Type word to delete");
        Scanner sc = new Scanner(System.in);
        String find = sc.nextLine();
        for (int i = 0; i < dictionaryManagement.size(); i++) {
            String key = dictionaryManagement.get(i).getWord_target();
            if (key.equals(find)) {
                dictionaryManagement.remove(i);
                break;
            }
        }
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
        System.out.println("Exported to file");
    }
}
