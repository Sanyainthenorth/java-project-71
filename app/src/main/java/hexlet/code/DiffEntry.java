package hexlet.code;

import java.util.Comparator;

public class DiffEntry {
    private String status;    // Статус (added, removed, changed, unchanged)
    private Object oldValue;  // Старое значение
    private Object newValue;  // Новое значение
    private String key;       // Ключ из внешней мапы

    public DiffEntry(String status, Object oldValue, Object newValue, String key) {
        this.status = status;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.key = key;
    }

    // Геттеры и сеттеры
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
        return "DiffEntry{" +
            "status='" + status + '\'' +
            ", oldValue=" + oldValue +
            ", newValue=" + newValue +
            ", key='" + key + '\'' +
            '}';
    }

    // Компаратор для сортировки по ключу
    public static Comparator<DiffEntry> keyComparator = new Comparator<DiffEntry>() {
        @Override
        public int compare(DiffEntry o1, DiffEntry o2) {
            return o1.getKey().compareTo(o2.getKey());
        }
    };
}
