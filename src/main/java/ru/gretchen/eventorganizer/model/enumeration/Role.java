package ru.gretchen.eventorganizer.model.enumeration;

public enum Role {
    PARTICIPANT("PARTICIPANT"),
    SPEAKER("SPEAKER"),
    ORGANIZER("ORGANIZER"),
    ADMIN("ADMIN");

    private final String role;

    Role(final String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
