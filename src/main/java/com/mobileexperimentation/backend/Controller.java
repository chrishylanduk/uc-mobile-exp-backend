package com.mobileexperimentation.backend;

import com.mobileexperimentation.backend.pojo.Journal;
import com.mobileexperimentation.backend.pojo.PersonalInfo;
import com.mobileexperimentation.backend.pojo.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class Controller {

    private final Business business;

    @Autowired
    public Controller(Business business) {
        this.business = business;
    }

    @PostMapping("account")
    public String createAccount(@RequestBody UserAccount userAccount) {
        business.createAccount(userAccount.newAccountNumber());
        return userAccount.accountNumber();
    }

    @GetMapping("accountNumber")
    public Optional<String> getAccountNumber(@RequestBody UserAccount userAccount) {
        return business.getAccountNumber(userAccount);
    }

    @GetMapping("accountNumber/byId")
    public Optional<String> getAccountNumberById(@RequestBody UserAccount userAccount) {
        return business.getAccountNumberById(userAccount.deviceId());
    }

    @GetMapping("/journal/{accountNumber}")
    public List<Journal> getJournals(@PathVariable String accountNumber) {
        return business.getJournalList(accountNumber);
    }

    @PostMapping("/journal/{accountNumber}")
    public void addJournal(@PathVariable String accountNumber,
                           @RequestBody Journal journal) {
        business.addJournal(accountNumber, journal.newEntryTime());
    }

    @GetMapping("/personal/{accountNumber}")
    public PersonalInfo getPersonal(@PathVariable String accountNumber) {
        return business.getPersonalInfo(accountNumber);
    }

    @PostMapping("/personal/{accountNumber}")
    public void addPersonal(@PathVariable String accountNumber,
                            @RequestBody PersonalInfo personalInfo) {
        business.addOrUpdatePersonalInformation(accountNumber, personalInfo);
    }

}
