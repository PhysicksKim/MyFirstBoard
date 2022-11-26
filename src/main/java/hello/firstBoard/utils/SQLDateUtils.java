package hello.firstBoard.utils;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SQLDateUtils{

    public static LocalDateTime SQLDateStrToLocalDateTime(String dateStr) {
        final String sqlDatePattern = "yyyy-MM-dd hh:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(sqlDatePattern);

        return LocalDateTime.parse(dateStr, formatter);
    }

    public static boolean isToday(LocalDateTime paramLDT) {
        LocalDate paramLD = paramLDT.toLocalDate();
        LocalDate today = LocalDate.now();

        return paramLD.isEqual(today);
    }

}

