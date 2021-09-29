package base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandline {

    void showAllWords(DictionaryManagement dictionaryManagement) {
        ArrayList<Word> arrayList = dictionaryManagement.arrayDictionary;
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

    void dictionarySearcher(DictionaryManagement dictionaryManagement) {
        ArrayList<Word> arrayList = dictionaryManagement.arrayDictionary;
        Scanner sc = new Scanner(System.in);
        String find = sc.nextLine();
        for (int i = 0; i < arrayList.size(); i++) {
            String key = arrayList.get(i).getWord_target();
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
        writee.showAllWords(array);
        array.insertFromCommandline();
        writee.showAllWords(array);
        array.dictionaryExportToFile();
    }
}
