package base.advanced;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import data.MongoDB;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DictionaryOnline {
    public static ArrayList<String> dictionarySearcher(String input, int offset) {
        ArrayList<String> ans = new ArrayList<>();
        BasicDBObject query = new BasicDBObject();
        BasicDBObject select = new BasicDBObject();
        FindIterable<Document> document;
        if (input.equals("")) {
            document = MongoDB.getDictionaryEV().find().projection(select).limit(50).skip(offset);
        } else {
            query.put("word", new BasicDBObject("$regex", "^" + input));
            select.put("word", 1);
            document = MongoDB.getDictionaryEV().find(query).projection(select).limit(50).skip(offset);
        }
        for (Document v : document) {
            ans.add((String) v.get("word"));
        }
        return ans;
    }

    public static JSONObject dictionaryLookup(String input) {
        BasicDBObject query = new BasicDBObject();
        query.put("word", input);
        Document document = MongoDB.getDictionaryEV().find(query).first();
        JSONObject ans = new JSONObject(document.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build()));
        JSONArray typeJSON = new JSONArray(ans.getString("type"));
        ans.put("type", typeJSON);
        return ans;
    }

    public static void main(String[] args) {
        MongoDB.initialize();
        ArrayList<String> ans = dictionarySearcher("a", 50);
        for (String v : ans) {
            System.out.println(v);
        }
        dictionaryLookup("translate");

    }
}
