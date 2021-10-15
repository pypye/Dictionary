package data;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.logging.Logger;
import java.util.logging.Level;

public class MongoDB {
    private static MongoCollection<Document> dictionaryEV;
    private static MongoCollection<Document> dictionaryVE;
    private static MongoClient mongoClient;

    public static void initialize() {
        mongoClient = MongoClients.create(connect());
        MongoDatabase database = mongoClient.getDatabase("Dictionary");
        dictionaryEV = database.getCollection("English_Vietnamese");
        dictionaryVE = database.getCollection("Vietnamese_English");
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
    }

    public static MongoCollection<Document> getDictionaryEV() {
        return dictionaryEV;
    }

    public static MongoCollection<Document> getDictionaryVE() {
        return dictionaryVE;
    }

    public static void close() {
        mongoClient.close();
    }

    private static MongoClientSettings connect() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://pypye22:22062002duc@dictionary.0darc.mongodb.net/Dictionary?retryWrites=true&w=majority");
        return MongoClientSettings.builder().applyConnectionString(connectionString).build();
    }
}
