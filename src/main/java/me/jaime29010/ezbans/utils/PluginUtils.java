package me.jaime29010.ezbans.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class PluginUtils {
    public static char SEPARATOR = ' ';
    public static String joinArray(String[] array, int start, int end) {
        final StringBuilder builder = new StringBuilder();
        int index = start;
        while (index < end) {
            builder.append(array [index++]);
            builder.append(SEPARATOR);
        }
        builder.append(array [index]);
        return builder.toString();
    }

    public static String joinArray(String[] array, int start) {
        return joinArray(array, start, array.length - 1);
    }

    public static String joinArray(String[] array) {
        return joinArray(array, 0, array.length - 1);
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String decolor(String message) {
        return ChatColor.stripColor(message);
    }

    public static String joinList(List<String> list, Joiner joiner) {
        if (joiner == null) {
            joiner = new Joiner() {
                @Override
                public String add(String string) {
                    return string;
                }
            };
        }

        StringBuilder builder = new StringBuilder();
        int index = 0;
        while (index < list.size() - 1) {
            builder.append(joiner.add(list.get(index++)));
            builder.append("\n");
        }
        builder.append(joiner.add(list.get(index)));
        return builder.toString();
    }

    public abstract static class Joiner {
        public abstract String add(String string);
    }
}
