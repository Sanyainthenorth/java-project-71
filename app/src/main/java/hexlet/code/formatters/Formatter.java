package hexlet.code.formatters;

import hexlet.code.DiffEntry;

import java.util.List;

public interface Formatter {
    String format(List<DiffEntry> diff);
}
