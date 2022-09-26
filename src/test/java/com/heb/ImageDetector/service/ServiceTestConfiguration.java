package com.heb.ImageDetector.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceTestConfiguration {
    @Bean
    public ImageDetectorService imageDetectorService(){
        return new ImageDetectorService();
    }
}
