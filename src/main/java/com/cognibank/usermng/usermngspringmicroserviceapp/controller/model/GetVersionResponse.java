package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

public class GetVersionResponse {
    private String version;

    public String getVersion() {
        return version;
    }

    public GetVersionResponse withVersion(String version) {
        this.version = version;
        return this;
    }
}
