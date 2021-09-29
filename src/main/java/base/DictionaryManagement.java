package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement extends Dictionary {

  public void insertFromCommandline() {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    String remove = sc.nextLine();
    for (int i = 0; i < n; i++) {
      String target = sc.nextLine();
      String explain = sc.nextLine();
      word wordInput = new word(target, explain);
      arrayDictionary.add(wordInput);
    }
  }

  public void insertFromFile() throws FileNotFoundException {
    String url = "src/main/java/base/dictionaries.txt";
    FileInputStream fileInputStream = new FileInputStream(url);
    Scanner sc = new Scanner(fileInputStream);
    while (sc.hasNextLine()) {
      String WordInput = sc.nextLine();
      String target = WordInput.split("\t")[0];
      String explain = WordInput.split("\t")[1];
      word wordInput = new word(target, explain);
      arrayDictionary.add(wordInput);
    }
  }

  public void dictionaryLookup() {
    Scanner sc = new Scanner(System.in);
    String find = sc.nextLine();
    for (word wordInput : arrayDictionary) {
      String key = wordInput.getWord_target();
      if (key.equals(find)) {
        wordInput.writeWordd();
        break;
      }
    }
  }

  public void dictionaryDelete() {
    Scanner sc = new Scanner(System.in);
    String find = sc.nextLine();
    for (int i = 0; i < arrayDictionary.size(); i++) {
      String key = arrayDictionary.get(i).getWord_target();
      if (key.equals(find)) {
        arrayDictionary.remove(i);
        break;
      }
    }
  }


  public void dictionaryExportToFile() throws IOException {
    String url = "src/main/java/base/dictionaries.txt";
    File file = new File(url);
    FileOutputStream outputStream = new FileOutputStream(file);
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
    for (base.word word : arrayDictionary) {
      String target = word.getWord_target();
      String explain = word.getWord_explain();
      String fullKey = target + "\t" + explain + "\n";
      outputStreamWriter.write(fullKey);
    }
    outputStreamWriter.flush();
  }
}
