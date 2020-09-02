package com.pocketchat.server.configurations.date_time_conversion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import java.time.format.DateTimeFormatter;

// This is to handle global Date/Time and string conversion.
// https://www.baeldung.com/spring-date-parameters
// https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#format-configuring-formatting-globaldatetimeformat
@Configuration
public class DateTimeConversionConfiguration {
    @Bean
    public FormattingConversionService conversionService() {
        DefaultFormattingConversionService conversionService =
                new DefaultFormattingConversionService(false);

        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        registrar.registerFormatters(conversionService);

        // other desired formatters

        return conversionService;
    }
}
