package com.hao.app.web.rest.util;

import com.google.common.base.Preconditions;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author dongh38@ziroom
 * @Date 16/10/3
 * @Time 下午3:15
 */
public class DateUtil {

    private DateUtil() {}

    public static Date toDate(LocalDate localDate) {
        Preconditions.checkNotNull(localDate,"localDate should't be null");
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDateTime localDateTime) {
        Preconditions.checkNotNull(localDateTime,"LocalDateTime should't be null");
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    public static LocalDate toLocalDate(Date date) {
        Preconditions.checkNotNull(date,"Date should't be null");
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        Preconditions.checkNotNull(date,"Date should't be null");
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
