package base.advanced;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.util.Collections.sort;

public class Dictionary {
    private JSONObject dictionary;

    public Dictionary(String file) throws IOException {
        String content = new String(Files.readAllBytes(Path.of(file)));
        dictionary = new JSONObject(content);
    }

    public ArrayList<String> dictionarySearcher(String input) {
        ArrayList<String> ans = new ArrayList<>();
        dictionary.keys().forEachRemaining(key -> {
            int found = key.indexOf(input);
            if (found == 0) {
                ans.add(key.trim());
            }
        });
        sort(ans);
        return ans;
    }

    public JSONObject dictionaryLookup(String input) {
        return dictionary.getJSONObject(input);
    }

    public static void main(String[] args) throws IOException {
        Dictionary arr = new Dictionary("src/main/java/data/output/english-vietnamese.json");
        System.out.println(arr.dictionaryLookup("transact"));
    }
}
