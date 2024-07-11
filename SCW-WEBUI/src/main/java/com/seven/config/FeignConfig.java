package com.seven.config;

/**
 * @author: seven
 * @since: 2024/7/11 16:39
 */
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public Logger.Level getFeignlogger() {
        return Logger.Level.FULL;
    }
}