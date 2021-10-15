package base.advanced;

import base.algorithms.Trie;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Dictionary {
    private static JSONObject dictionary;
    private static final Trie findTrie = new Trie();

    public static void initialize() {
        try {
            String content = new String(Files.readAllBytes(Path.of("src/main/java/data/output/english-vietnamese.json")));
            dictionary = new JSONObject(content);
            dictionary.keys().forEachRemaining(findTrie::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dictionaryInsert(String word, JSONObject object) {
        dictionary.put(word, object);
        findTrie.add(word);
    }

    public static ArrayList<String> dictionarySearcher(String input) {
        return findTrie.findAllWord(input);
    }

    public static JSONObject dictionaryLookup(String input) {
        return dictionary.getJSONObject(input);
    }

    public static void dictionaryDelete(String input) {
        dictionary.remove(input);
        findTrie.delete(input);
    }

    public static void save() {
        try {
            FileWriter file = new FileWriter("src/main/java/data/output/english-vietnamese.json");
            file.write(String.valueOf(dictionary));
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dictionary = null;
        System.gc();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(dictionaryLookup("transact"));
    }
}