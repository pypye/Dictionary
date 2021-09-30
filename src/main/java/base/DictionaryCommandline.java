package base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandline {

  public ArrayList<String> showAllWords(DictionaryManagement dictionaryManagement) {
    ArrayList<word> arrayList = dictionaryManagement.arrayDictionary;
    ArrayList<String> dictionary = new ArrayList<>();
    dictionary.add("No      | English                      | Vietnamese");
    for (int i = 0; i < arrayList.size(); i++) {
      String template = (Integer.toString(i+1));
      int cnt = Math.max(0, (int) Math.log10(i));
      for (int j = 1; j < 15 - cnt; j++) {
        template = template.concat(" ");
      }
      template = template.concat("| ");

      String target = arrayList.get(i).getWord_target();
      String explain = arrayList.get(i).getWord_explain();
      template = template.concat(target);
      for (int j = 1; j <= 22 - target.length(); j++) {
        template = template.concat(" ");
      }
      template = template.concat("| " + explain);
      dictionary.add(template);
    }
    return dictionary;
  }

  void dictionarySearcher(DictionaryManagement dictionaryManagement) {
    ArrayList<word> arrayList = dictionaryManagement.arrayDictionary;
    Scanner sc = new Scanner(System.in);
    String find = sc.nextLine();
    for (base.word word : arrayList) {
      String key = word.getWord_target();
      int found = key.indexOf(find);
      if (found == 0) {
        System.out.println(key);
      }
    }
  }

  public void dictionaryAdvanced() throws IOException {
    DictionaryManagement array = new DictionaryManagement();
    DictionaryCommandline writee = new DictionaryCommandline();
    array.insertFromFile();
    ArrayList<String> x = writee.showAllWords(array);
    for(int i = 0; i < x.size(); i++) System.out.println(x.get(i));
//    array.insertFromCommandline();
//    writee.showAllWords(array);
//    array.dictionaryExportToFile();
  }
}
