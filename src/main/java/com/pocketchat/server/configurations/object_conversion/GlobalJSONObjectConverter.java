package com.pocketchat.server.configurations.object_conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This Configuration is used to set up Converter from JSON string to Object(or Object to JSON string).
 * You can call it with @Autowired or set up some configuration in here.
 */
@Configuration
public class GlobalJSONObjectConverter {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
