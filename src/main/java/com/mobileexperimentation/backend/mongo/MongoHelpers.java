package com.mobileexperimentation.backend.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.Optional;

import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoHelpers {

    private static final String MONGO_PASSWORD = System.getenv("MONGO_PASSWORD");
    private static final ConnectionString MONGO_STRING = new ConnectionString("mongodb+srv://New-Admin:" + MONGO_PASSWORD + "@mobileexperimentation.fn7qbxp.mongodb.net/?retryWrites=true&w=majority");
    private static final CodecRegistry POJO_CODEC_REGISTRY = fromProviders(PojoCodecProvider.builder().automatic(true).build());
    private static final CodecRegistry CODEC_REGISTRY = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), POJO_CODEC_REGISTRY);
    private static final MongoClientSettings CLIENT_SETTINGS = MongoClientSettings.builder()
            .applyConnectionString(MONGO_STRING)
            .codecRegistry(CODEC_REGISTRY)
            .build();
    private static final String MOBILE_EXPERIMENTATION = "MobileExperimentation";
    private static final String ID = "_id";

    public static <T> Optional<T> getAccountById(Class<T> pojoClass, String collection, String id) {
        try (MongoClient mongoClient = MongoClients.create(CLIENT_SETTINGS)) {
            MongoDatabase mobExpDB = mongoClient.getDatabase(MOBILE_EXPERIMENTATION);
            MongoCollection<T> ucInfoCollection = mobExpDB.getCollection(collection, pojoClass);
            return Optional.ofNullable(ucInfoCollection.find(eq("deviceId", id)).first());
        }
    }

    public static <T> Optional<T> getMongoData(Class<T> pojoClass, String collection, String id) {
        try (MongoClient mongoClient = MongoClients.create(CLIENT_SETTINGS)) {
            MongoDatabase mobExpDB = mongoClient.getDatabase(MOBILE_EXPERIMENTATION);
            MongoCollection<T> ucInfoCollection = mobExpDB.getCollection(collection, pojoClass);
            return Optional.ofNullable(ucInfoCollection.find(eq(ID, id)).first());
        }
    }

    public static <T, S> void upsertMongoData(Class<T> pojoClass, String collection, S data, String entity, String id) {
        try (MongoClient mongoClient = MongoClients.create(CLIENT_SETTINGS)) {
            MongoDatabase mobExpDB = mongoClient.getDatabase(MOBILE_EXPERIMENTATION);
            MongoCollection<T> ucInfoCollection = mobExpDB.getCollection(collection, pojoClass);
            Bson filter = Filters.eq(ID, id);
            Bson update = Updates.set(entity, data);
            UpdateOptions options = new UpdateOptions().upsert(true);
            ucInfoCollection.updateOne(filter, update, options);
        }
    }

    public static <T> void insertMongoData(Class<T> pojoClass, String collection, T data) {
        try (MongoClient mongoClient = MongoClients.create(CLIENT_SETTINGS)) {
            MongoDatabase mobExpDB = mongoClient.getDatabase(MOBILE_EXPERIMENTATION);
            MongoCollection<T> ucInfoCollection = mobExpDB.getCollection(collection, pojoClass);
            ucInfoCollection.insertOne(data);
        }
    }
}
