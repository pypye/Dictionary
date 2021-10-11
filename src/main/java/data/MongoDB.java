package data;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDB {
    private final MongoCollection<Document> Vietnamese_English;
    private final MongoCollection<Document> English_Vietnamese;

    public MongoDB() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://pypye22:22062002duc@dictionary.0darc.mongodb.net/Dictionary?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Dictionary");
        Vietnamese_English = database.getCollection("Vietnamese_English");
        English_Vietnamese = database.getCollection("English_Vietnamese");
    }

    public MongoCollection<Document> getVietnamese_English() {
        return Vietnamese_English;
    }

    public MongoCollection<Document> getEnglish_Vietnamese() {
        return English_Vietnamese;
    }
}
