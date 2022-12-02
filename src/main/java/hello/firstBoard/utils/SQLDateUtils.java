package hello.firstBoard.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class SQLDateUtils{

    public static LocalDateTime SQLDateStrToLocalDateTime(String dateStr) {
        final String sqlDatePattern = "yyyy-MM-dd HH:mm:ss.nnnnnn";
        log.info(dateStr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(sqlDatePattern);

        return LocalDateTime.parse(dateStr, formatter);
    }

    public static boolean isToday(LocalDateTime paramLDT) {
        LocalDate paramLD = paramLDT.toLocalDate();
        LocalDate today = LocalDate.now();

        return paramLD.isEqual(today);
    }

}

