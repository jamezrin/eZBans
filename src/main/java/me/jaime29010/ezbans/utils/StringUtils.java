package me.jaime29010.ezbans.utils;

import java.util.List;

public class StringUtils {
    public static final char ARRAY_SEPARATOR = '\u0020';
    public static final char LIST_SEPARATOR = '\n';

    public static String joinArray(String[] array, int start, int end, Joiner joiner) {
        if (joiner == null) {
            joiner = new Joiner() {
                @Override
                public String addEntry(String string) {
                    return string + ARRAY_SEPARATOR;
                }
            };
        }
        final StringBuilder builder = new StringBuilder();
        int index = start;
        while (index < end) {
            builder.append(joiner.addEntry(array[index++]));
        }
        builder.append(joiner.addLast(array[index]));
        return builder.toString();
    }

    public static String joinArray(String[] array, int start, Joiner joiner) {
        return joinArray(array, start, array.length - 1, joiner);
    }

    public static String joinArray(String[] array, Joiner joiner) {
        return joinArray(array, 0, array.length - 1, joiner);
    }

    public static String joinList(List<String> list, int start, int end, Joiner joiner) {
        if (joiner == null) {
            joiner = new Joiner() {
                @Override
                public String addEntry(String string) {
                    return string + LIST_SEPARATOR;
                }
            };
        }

        StringBuilder builder = new StringBuilder();
        int index = start;
        while (index < end) {
            builder.append(joiner.addEntry(list.get(index++)));
        }
        builder.append(joiner.addLast(list.get(index)));
        return builder.toString();
    }

    public static String joinList(List<String> list, int start, Joiner joiner) {
        return joinList(list, start, list.size() - 1, joiner);
    }

    public static String joinList(List<String> list, Joiner joiner) {
        return joinList(list, 0, list.size() - 1, joiner);
    }

    public abstract static class Joiner {
        public abstract String addEntry(String string);

        public String addLast(String string) {
            return string;
        }
    }
}
