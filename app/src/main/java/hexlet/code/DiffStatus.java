package hexlet.code;

public enum DiffStatus {
    ADDED("added"),
    REMOVED("removed"),
    CHANGED("changed"),
    UNCHANGED("unchanged");

    private final String value;

    DiffStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
