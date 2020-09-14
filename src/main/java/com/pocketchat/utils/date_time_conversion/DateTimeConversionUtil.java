package com.pocketchat.utils.date_time_conversion;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * This utility is used for convert any date or time object.
 */
@Service
public class DateTimeConversionUtil {

    /**
     * Convert Joda LocalDateTime to Java Time LocalDateTime.
     * @param jodaLocalDateTime: LocalDateTime object from org.joda.time.
     * @return LocalDateTime object from java.time.
     */
    public LocalDateTime convertJodaToJavaTimeLocalDateTime(org.joda.time.LocalDateTime jodaLocalDateTime) {
        return LocalDateTime.parse(jodaLocalDateTime.toString());
    }

    /**
     *
     * @param javaLocalDateTime: LocalDateTime object from java.time.
     * @return LocalDateTime object from org.joda.time.
     */
    public org.joda.time.LocalDateTime convertJavaTimeToJodaLocalDateTime(LocalDateTime javaLocalDateTime) {
        return org.joda.time.LocalDateTime.parse(javaLocalDateTime.toString());
    }
}
