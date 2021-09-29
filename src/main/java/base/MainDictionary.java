package base;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainDictionary {

  public static void main(String[] args) throws IOException {
//    DictionaryManagement array = new DictionaryManagement();
//    array.insertFromFile();
//
    DictionaryCommandline writee = new DictionaryCommandline();
    writee.dictionaryAdvanced();
    //writee.dictionarySearcher(array);
  }
}
