package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T> boolean isBetween(Comparable<T> value, T from, T to) {
        return value.compareTo(from) >= 0 && value.compareTo(to) <= 0;
    }
    // как правильно сделать через дженерики?
    /*public static <T extends Comparable<T>> boolean isBetween(T value, T from, T to) {
        return value.compareTo(from) >= 0 && value.compareTo(to) <= 0;
    }*/

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
