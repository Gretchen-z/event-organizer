package ru.gretchen.eventorganizer.model.enumeration;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String gender;

    Gender(final String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return gender;
    }
}
