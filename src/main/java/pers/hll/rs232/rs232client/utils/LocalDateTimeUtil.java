package pers.hll.rs232.rs232client.utils;


import lombok.experimental.UtilityClass;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author hll
 * @since 2024/03/21
 */
@UtilityClass
public class LocalDateTimeUtil {

    public final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public String formatNormal(LocalDateTime time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN, Locale.getDefault())
                .withZone(ZoneId.systemDefault());
        return format(time, dateTimeFormatter);
    }

    public String format(TemporalAccessor temporalAccessor, DateTimeFormatter formatter) {
        if (null == temporalAccessor) {
            return null;
        }

        if (temporalAccessor instanceof Month) {
            return temporalAccessor.toString();
        }

        if (null == formatter) {
            formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        }

        try {
            return formatter.format(temporalAccessor);
        } catch (UnsupportedTemporalTypeException e) {
            if (temporalAccessor instanceof LocalDate localDate && e.getMessage().contains("HourOfDay")) {
                // 用户传入LocalDate，但是要求格式化带有时间部分，转换为LocalDateTime重试
                return formatter.format(localDate.atStartOfDay());
            } else if (temporalAccessor instanceof LocalTime localTime && e.getMessage().contains("YearOfEra")) {
                // 用户传入LocalTime，但是要求格式化带有日期部分，转换为LocalDateTime重试
                return formatter.format(localTime.atDate(LocalDate.now()));
            } else if (temporalAccessor instanceof Instant instant) {
                // 时间戳没有时区信息，赋予默认时区
                return formatter.format(instant.atZone(ZoneId.systemDefault()));
            }
            throw e;
        }
    }
}
