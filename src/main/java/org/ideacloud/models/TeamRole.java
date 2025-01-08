package org.ideacloud.models;

public enum TeamRole {
    MANAGER("MANAGER"),
    MEMBER("MEMBER"),
    ;

    private final String value;

    TeamRole(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
