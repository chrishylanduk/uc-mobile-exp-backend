package com.mobileexperimentation.backend.pojo;

import org.bson.codecs.pojo.annotations.BsonId;

import java.util.ArrayList;
import java.util.List;

public record AccountInfo(@BsonId String accountNumber, PersonalInfo personalInfo, List<Journal> journals) {
    public AccountInfo(String accountNumber) {
        this(accountNumber, new PersonalInfo(), new ArrayList<>());
    }

    public AccountInfo withAdditionalJournal(Journal journal) {
        if (journals == null) {
            return new AccountInfo(accountNumber, personalInfo, new ArrayList<>(List.of(journal)));
        }
        journals.add(journal);
        return this;
    }
}
