package com.mobileexperimentation.backend;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class WriteToMongo {

    private final String mongoPassword = System.getenv("MONGO_PASSWORD");

    public void createAccount(String accountNumber, String email, String password){
        String mongoString = "mongodb+srv://MobileExperiementation:" + mongoPassword + "@mobileexperimentation.fn7qbxp.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(mongoString)) {

            MongoDatabase mobExpDB = mongoClient.getDatabase("MobileExperimentation");
            MongoCollection<Document> accountCollection = mobExpDB.getCollection("Account");
            Document account = new Document("_id", email);
            account.append("account_number", accountNumber)
                    .append("password", password);
            accountCollection.insertOne(account);
        }
    }

    public void addAccountInformation(String accountNumber, String givenName, String familyName){
        String mongoString = "mongodb+srv://MobileExperiementation:" + mongoPassword + "@mobileexperimentation.fn7qbxp.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(mongoString)) {

            MongoDatabase mobExpDB = mongoClient.getDatabase("MobileExperimentation");
            MongoCollection<Document> personalCollection = mobExpDB.getCollection("personal");
            Document personal = new Document("_id", accountNumber);
            personal.append("given_name", givenName)
                    .append("family_name", familyName);
            personalCollection.insertOne(personal);
        }
    }

    public void addUniversalCreditInformation(String accountNumber, double nextPaymentAmount){
        String mongoString = "mongodb+srv://MobileExperiementation:" + mongoPassword + "@mobileexperimentation.fn7qbxp.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(mongoString)) {

            MongoDatabase mobExpDB = mongoClient.getDatabase("MobileExperimentation");
            MongoCollection<Document> ucInfoCollection = mobExpDB.getCollection("ucInfo");
            Document ucInfo = new Document("_id", accountNumber);
            ucInfo.append("next_payment_amount", nextPaymentAmount);
            ucInfoCollection.insertOne(ucInfo);
        }
    }
}
