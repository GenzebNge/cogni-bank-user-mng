package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

import io.swagger.annotations.ApiModelProperty;

public class GetVersionResponse {
    @ApiModelProperty(notes = "Current API version")
    private String version;

    public String getVersion() {
        return version;
    }

    public GetVersionResponse withVersion(String version) {
        this.version = version;
        return this;
    }
}
