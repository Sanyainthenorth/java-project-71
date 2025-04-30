package hexlet.code;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DiffStatus {
    ADDED("added"),
    REMOVED("removed"),
    CHANGED("changed"),
    UNCHANGED("unchanged");

    private final String value;

    DiffStatus(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }
}
