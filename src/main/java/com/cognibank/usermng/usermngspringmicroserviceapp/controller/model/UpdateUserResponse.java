package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

public class UpdateUserResponse {
    private boolean updated;

    public boolean isUpdated() {
        return updated;
    }

    public UpdateUserResponse withUpdated(boolean updated) {
        this.updated = updated;
        return this;
    }
}
