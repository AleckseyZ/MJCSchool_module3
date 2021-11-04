package com.epam.esm.zotov.mjcschool.api;

public enum ApiProfile {
    PROD("prod"), DEV("dev");

    private String profileName;

    private ApiProfile(String profileName) {
        this.profileName = profileName;
    }

    @Override
    public String toString() {
        return profileName;
    }
}