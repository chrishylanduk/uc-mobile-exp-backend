package com.mobileexperimentation.backend.pojo;

public record PersonalInfo(String givenName, String familyName) {
    public PersonalInfo() {
        this(null, null);
    }
}
