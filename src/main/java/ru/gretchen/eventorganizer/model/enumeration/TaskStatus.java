package ru.gretchen.eventorganizer.model.enumeration;

public enum TaskStatus {
    CREATED("CREATED"),
    ASSIGNED("ASSIGNED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String taskStatus;

    TaskStatus(final String taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return taskStatus;
    }
}
