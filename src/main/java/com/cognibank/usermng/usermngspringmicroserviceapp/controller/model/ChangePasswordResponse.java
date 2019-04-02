package com.cognibank.usermng.usermngspringmicroserviceapp.controller.model;

public class ChangePasswordResponse {
    private boolean changed;

    public boolean isChanged() {
        return changed;
    }

    public ChangePasswordResponse withChanged(boolean changed) {
        this.changed = changed;
        return this;
    }
}
