package base.advanced;

import base.basic.Trie;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.util.Collections.sort;

public class Dictionary {
    private static JSONObject dictionary;

    public static void initialize() {
        try {
            String content = new String(Files.readAllBytes(Path.of("src/main/java/data/output/english-vietnamese.json")));
            dictionary = new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> dictionarySearcher(String input) {
        ArrayList<String> ans = new ArrayList<>();
        Trie findTrie = new Trie();
        dictionary.keys().forEachRemaining(findTrie::add);
        return findTrie.findAllWord(input);
    }

    public static JSONObject dictionaryLookup(String input) {
        return dictionary.getJSONObject(input);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(dictionaryLookup("transact"));
    }
}
