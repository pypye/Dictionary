package base.advanced;

import java.util.ArrayList;

public class Dictionary {
    ArrayList<Word> dictionaryArray = new ArrayList<>();

    public ArrayList<Word> getDictionaryArray() {
        return dictionaryArray;
    }

    public void add(Word wordInput) {
        dictionaryArray.add(wordInput);
    }

    public void remove(int index) {
        dictionaryArray.remove(index);
    }

    public Word get(int index) {
        return dictionaryArray.get(index);
    }

    public int size() {
        return dictionaryArray.size();
    }
}
