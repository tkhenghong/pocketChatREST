package com.pocketchat.server.configurations.object_conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This Configuration is used to set up Converter from JSON string to Object(or Object to JSON string).
 * You can call it with @Autowired or set up some configuration in here.
 */
@Configuration
public class GlobalJSONObjectConverter {

    // TODO: this is causing return LocalDateTIme in server response object to become Maps<String, Object> in frontend.
    @Bean
    @Qualifier("generalOM")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
