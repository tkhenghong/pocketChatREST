package com.pocketchat.utils.date_time_conversion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DateTimeConversionUtilTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @InjectMocks
    DateTimeConversionUtil dateTimeConversionUtil;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * To test whether the utility is able to convert Joda LocalDateTime to Java Time LocalDateTime correctly or not.
     */
    @Test
    void testConvertJavaTimeToJodaLocalDateTime() {
        org.joda.time.LocalDateTime jodaLocalDateTime = org.joda.time.LocalDateTime.now();

        logger.info("jodaLocalDateTime: {}", jodaLocalDateTime.toString());

        LocalDateTime localDateTime = dateTimeConversionUtil.convertJodaToJavaTimeLocalDateTime(jodaLocalDateTime);

        logger.info("localDateTime: {}", localDateTime.toString());

        assertNotNull(localDateTime);
        assertEquals(jodaLocalDateTime.toString(), localDateTime.toString());
    }

    /**
     * To test whether the utility is able to convert Joda LocalDateTime to Java Time LocalDateTime correctly or not.
     */
    @Test
    void testConvertJodaToJavaTimeLocalDateTime() {
        LocalDateTime javaLocalDateTime = LocalDateTime.now();

        logger.info("javaLocalDateTime: {}", javaLocalDateTime.toString());

        org.joda.time.LocalDateTime jodaLocalDateTime = dateTimeConversionUtil.convertJavaTimeToJodaLocalDateTime(javaLocalDateTime);

        logger.info("jodaLocalDateTime: {}", jodaLocalDateTime.toString());

        // Joda DateTime doesn't have nanosecond precision
        // NOTE: java.time library--Level of precision:
        // Year -> Month -> Date -> Hour -> Minute -> Second -> Millisecond(Joda time only reaches here) -> Microsecond -> Nanosecond
        int javaLocalDateTimeLength = javaLocalDateTime.toString().length();
        int jodaDateTimeLength = jodaLocalDateTime.toString().length();

        assertNotNull(jodaLocalDateTime);
        assertEquals(jodaDateTimeLength, javaLocalDateTimeLength - 6);
        assertEquals(jodaLocalDateTime.toString(), javaLocalDateTime.toString().substring(0, jodaDateTimeLength));
    }
}
