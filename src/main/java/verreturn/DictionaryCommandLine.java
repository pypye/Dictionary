package verreturn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandLine {

  private DictionaryManagement dictionaryCommandLine = new DictionaryManagement();

  void showAllWords() {
    ArrayList<Word> arrayList = dictionaryCommandLine.getDictionaryManagement()
        .getDictionaryArray();
    System.out.println("No      | English               | Vietnamese");
    for (int i = 0; i < arrayList.size(); i++) {
      System.out.print(i + 1);
      int cnt = Math.max(0, (int) Math.log10(i));
      for (int j = 1; j < 8 - cnt; j++) {
        System.out.print(" ");
      }
      System.out.print("| ");
      String target = arrayList.get(i).getWord_target();
      String explain = arrayList.get(i).getWord_explain();
      System.out.print(target);
      for (int j = 1; j <= 22 - target.length(); j++) {
        System.out.print(" ");
      }
      System.out.println("| " + explain);
    }
  }

  public String dictionarySearcher(String find) {
    String ans = "";
    ArrayList<Word> arrayList = dictionaryCommandLine.getDictionaryManagement().getDictionaryArray();
    for (Word word : arrayList) {
      String key = word.getWord_target();
      int found = key.indexOf(find);
      if (found == 0) {
        if (!ans.equals("")) ans = ans.concat("\n");
        ans = ans.concat(key) ;
      }
    }
    if (ans.equals("")) {
      return "Can not find";
    }
    return ans;
  }

  public void dictionaryAdvanced() throws IOException {
    System.out.println("Type -help for more details");
    dictionaryCommandLine.insertFromFile();
    Scanner sc = new Scanner(System.in);
    while (true) {
      String cmd = sc.nextLine();
      if (cmd.equals("-help")) {
        System.out.println("-insert, -delete, -translate, -export, -show, -search, -exit");
      }
      if (cmd.equals("-insert")) {
        System.out.printf("Type target: ");
        String target = sc.nextLine();
        System.out.printf("Type explain: ");
        String explain = sc.nextLine();
        dictionaryCommandLine.insertFromCommandline(target, explain);
      }
      if (cmd.equals("-delete")) {
        System.out.printf("Type word to delete");
        String find = sc.nextLine();
        dictionaryCommandLine.dictionaryDelete(find);
      }
      if (cmd.equals("-translate")) {
        System.out.printf("Type word to translate: ");
        String find = sc.nextLine().trim();
        String ans = dictionaryCommandLine.dictionaryLookup(find);
        System.out.println(ans);
      }
      if (cmd.equals("-export")) {
        dictionaryCommandLine.dictionaryExportToFile();
      }
      if (cmd.equals("-show")) {
        this.showAllWords();
      }

      if (cmd.equals("-search")) {
        System.out.printf("Type word to Search: ");
        String find = sc.nextLine();
        String ans = this.dictionarySearcher(find);
        System.out.println(ans);
      }
      if (cmd.equals("-exit")) {
        break;
      }
    }


  }
}
