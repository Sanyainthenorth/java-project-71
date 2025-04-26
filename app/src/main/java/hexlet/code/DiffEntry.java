package hexlet.code;

import java.util.Comparator;

public final class DiffEntry {
    private DiffStatus status;    // Статус (added, removed, changed, unchanged)
    private Object oldValue;
    private Object newValue;
    private String key;

    public DiffEntry(DiffStatus status, Object oldValue, Object newValue, String key) {
        this.status = status;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.key = key;
    }
    private static final Comparator<DiffEntry> KEY_COMPARATOR = Comparator.comparing(DiffEntry::getKey);
    // Геттеры и сеттеры
    public DiffStatus getStatus() {
        return status;
    }


    public void setStatus(DiffStatus status) {
        this.status = status;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "DiffEntry{"
            + "status='" + status.getValue() + '\''
            + ", oldValue=" + oldValue
            + ", newValue=" + newValue
            + ", key='" + key + '\''
            + '}';
    }

    public static Comparator<DiffEntry> getKeyComparator() {
        return KEY_COMPARATOR;
    }
}
