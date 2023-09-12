package com.mobileexperimentation.backend.pojo;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.Optional;

public record UserAccount(@BsonId String email, String accountNumber, String password) {

    public UserAccount(String email) {
        this(email, ObjectId.get().toString(), null);
    }

    public Optional<String> accountNumberIfValidPassword(String inputtedPassword) {
        return password != null && password.equals(inputtedPassword) ? Optional.of(accountNumber) : Optional.empty();
    }

    public UserAccount newAccountNumber() {
        return(new UserAccount(email, ObjectId.get().toString(), password));
    }

}
