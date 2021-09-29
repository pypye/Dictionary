package base;

import java.util.ArrayList;

public class Dictionary {

  protected ArrayList<word> arrayDictionary = new ArrayList<>();

  public ArrayList<word> getArrayDictionary() {
    return arrayDictionary;
  }

  public void setArrayDictionary(ArrayList<word> arrayDictionary) {
    this.arrayDictionary = arrayDictionary;
  }
}
