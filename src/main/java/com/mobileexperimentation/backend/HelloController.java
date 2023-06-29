package com.mobileexperimentation.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("/")
    public String index() {
        WriteToMongo writeToMongo = new WriteToMongo();
        writeToMongo.createAccount("s", "a", "d");
        return "Greetings from Spring Boot!";


    }





}
