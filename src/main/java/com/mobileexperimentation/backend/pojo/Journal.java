package com.mobileexperimentation.backend.pojo;

import java.time.LocalDateTime;

public record Journal(String entry, LocalDateTime date, String person) {

    public Journal newEntryTime() {
        return new Journal(entry, LocalDateTime.now(), person);
    }

}
