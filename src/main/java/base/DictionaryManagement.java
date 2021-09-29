package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {

  ArrayList<Wordd> arrayDictionary = new ArrayList<>();

  public void insertFromCommandline() {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    String remove = sc.nextLine();
    for (int i = 0; i < n; i++) {
      String target = sc.nextLine();
      String explain = sc.nextLine();
      Wordd wordd = new Wordd(target, explain);
      arrayDictionary.add(wordd);
    }
  }

  public void insertFromFile() throws FileNotFoundException {
    String url = "E:\\Tudien\\src\\Dis\\dictionaries.txt";
    FileInputStream fileInputStream = new FileInputStream(url);
    Scanner sc = new Scanner(fileInputStream);
    while (sc.hasNextLine()) {
      String Wordd = sc.nextLine();
      String target = Wordd.split("\t")[0];
      String explain = Wordd.split("\t")[1];
      Wordd wordd = new Wordd(target, explain);
      arrayDictionary.add(wordd);
    }
  }

  public void dictionaryLookup() {
    Scanner sc = new Scanner(System.in);
    String find = sc.nextLine();
    for (Wordd wordd : arrayDictionary) {
      String key = wordd.getWord_target();
      if (key.equals(find)) {
        wordd.writeWordd();
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
    String url = "E:\\Tudien\\src\\Dis\\dictionaries.txt";
    File file = new File(url);
    FileOutputStream outputStream = new FileOutputStream(file);
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
    for(int i = 0; i < arrayDictionary.size(); i++) {
      String target = arrayDictionary.get(i).getWord_target();
      String explain = arrayDictionary.get(i).getWord_explain();
      String fullkey = target + "\t" + explain + "\n";
      outputStreamWriter.write(fullkey);
    }
    outputStreamWriter.flush();
  }
}
