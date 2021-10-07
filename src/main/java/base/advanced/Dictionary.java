package base.advanced;

import base.algorithms.Trie;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Dictionary {
    private static JSONObject dictionary;
    private static Trie findTrie = new Trie();

    public static void initialize() {
        try {
            String content = new String(Files.readAllBytes(Path.of("src/main/java/data/output/english-vietnamese.json")));
            dictionary = new JSONObject(content);
            dictionary.keys().forEachRemaining(findTrie::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> dictionarySearcher(String input) {
        return findTrie.findAllWord(input);
    }

    public static JSONObject dictionaryLookup(String input) {
        return dictionary.getJSONObject(input);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(dictionaryLookup("transact"));
    }
}
