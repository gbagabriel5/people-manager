package com.gba.people.manager.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final String DDMMYYYY = "dd/MM/yyyy";

    public static LocalDate parseStringToLocalDateDDMMYYYY(String date) {
        return date != null
                ? LocalDate.parse(date, DateTimeFormatter.ofPattern(DDMMYYYY))
                : null;
    }
}


