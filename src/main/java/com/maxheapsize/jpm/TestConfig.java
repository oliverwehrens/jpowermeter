package com.maxheapsize.jpm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfig {

    @Bean
    public EhzSmlReader ehzSmlReader() {
        return new MockEhzSmlReader();
    }

}
