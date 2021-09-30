package base.basic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandLine {
    private DictionaryManagement dictionaryCommandLine = new DictionaryManagement();

    void showAllWords() {
        ArrayList<Word> arrayList = dictionaryCommandLine.getDictionaryManagement().getDictionaryArray();
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

    void dictionarySearcher() {
        System.out.printf("Type word to Search: ");
        ArrayList<Word> arrayList = dictionaryCommandLine.getDictionaryManagement().getDictionaryArray();
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
        System.out.println("Type -help for more details");
        dictionaryCommandLine.insertFromFile();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String cmd = sc.nextLine();
            if (cmd.equals("-help")) {
                System.out.println("-insert, -delete, -translate, -export, -show, -search, -exit");
            }
            if (cmd.equals("-insert")) {
                dictionaryCommandLine.insertFromCommandline();
            }
            if (cmd.equals("-delete")) {
                dictionaryCommandLine.dictionaryDelete();
            }
            if (cmd.equals("-translate")) {
                dictionaryCommandLine.dictionaryLookup();
            }
            if (cmd.equals("-export")) {
                dictionaryCommandLine.dictionaryExportToFile();
            }
            if (cmd.equals("-show")) {
                this.showAllWords();
            }

            if (cmd.equals("-search")) {
                this.dictionarySearcher();
            }
            if (cmd.equals("-exit")) {
                break;
            }
        }


    }
}
