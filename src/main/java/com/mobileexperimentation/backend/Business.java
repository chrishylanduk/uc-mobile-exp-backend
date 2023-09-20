package com.mobileexperimentation.backend;

import com.mobileexperimentation.backend.pojo.AccountInfo;
import com.mobileexperimentation.backend.pojo.Journal;
import com.mobileexperimentation.backend.pojo.PersonalInfo;
import com.mobileexperimentation.backend.pojo.UserAccount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mobileexperimentation.backend.mongo.MongoHelpers.getAccountById;
import static com.mobileexperimentation.backend.mongo.MongoHelpers.getMongoData;
import static com.mobileexperimentation.backend.mongo.MongoHelpers.insertMongoData;
import static com.mobileexperimentation.backend.mongo.MongoHelpers.upsertMongoData;


@Service
public class Business {

    private static final String UC_INFO = "ucInfo";
    private static final String ACCOUNT = "Account";
    private static final String PERSONAL_INFO = "personalInfo";
    private static final String JOURNALS = "journals";


    public void createAccount(UserAccount userAccount) {
        insertMongoData(UserAccount.class, "Account", userAccount);
    }

    public Optional<String> getAccountNumber(String email, String password) {
        UserAccount userAccount = getUserAccount(email);
        return userAccount.accountNumberIfValidPassword(password);
    }

    public Optional<String> getAccountNumberById(String deviceId) {
        UserAccount userAccount = getUserAccountById(deviceId);
        return Optional.of(userAccount.accountNumber());
    }

    public void addJournal(String accountNumber, Journal journal) {
        AccountInfo accountInfo = getAccountInfo(accountNumber);
        accountInfo.addJournal(journal);
        upsertJournals(accountInfo);
    }

    public void addOrUpdatePersonalInformation(String accountNumber, PersonalInfo personalInfo) {
        upsertPersonalInformation(accountNumber, personalInfo);
    }

    public List<Journal> getJournalList(String accountNumber) {
        AccountInfo document = getAccountInfo(accountNumber);
        return document.journals();
    }

    public PersonalInfo getPersonalInfo(String accountNumber) {
        AccountInfo document = getAccountInfo(accountNumber);
        return document.personalInfo();
    }

    private void upsertJournals(AccountInfo accountInfo) {
        upsertMongoData(AccountInfo.class, UC_INFO, accountInfo.journals(), JOURNALS, accountInfo.accountNumber());
    }

    private void upsertPersonalInformation(String accountNumber, PersonalInfo personalInfo) {
        upsertMongoData(AccountInfo.class, UC_INFO, personalInfo, PERSONAL_INFO, accountNumber);
    }

    private AccountInfo getAccountInfo(String accountNumber) {
        return getMongoData(AccountInfo.class, UC_INFO, accountNumber).orElse(new AccountInfo(accountNumber));
    }

    private UserAccount getUserAccount(String email) {
        return getMongoData(UserAccount.class, ACCOUNT, email).orElse(new UserAccount(email));
    }

    private UserAccount getUserAccountById(String deviceId) {
        return getAccountById(UserAccount.class, ACCOUNT, deviceId).orElse(new UserAccount(""));
    }

}
