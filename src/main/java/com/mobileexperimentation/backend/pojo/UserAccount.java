package com.mobileexperimentation.backend.pojo;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record UserAccount(@BsonId String email, String accountNumber, String password, String deviceId) {

    public UserAccount(String email) {
        this(email, ObjectId.get().toString(), null, null);
    }

    public Optional<String> accountNumberIfValidPassword(String inputtedPassword) {
        return password != null && password.equals(inputtedPassword) ? Optional.of(accountNumber) : Optional.empty();
    }

    public UserAccount newAccountNumber() {
        return(new UserAccount(email, ObjectId.get().toString(), password, deviceId));
    }

}
