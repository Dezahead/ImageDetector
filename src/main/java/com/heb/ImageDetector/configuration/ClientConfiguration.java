package com.heb.ImageDetector.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class ClientConfiguration {
    private final String credentialsToEncode = "&lt;acc_ed6f30435790021&gt;" + ":" + "&lt;daf70c3a62e991ac1ca77bb59da03e09&gt;";
    private final String basicAuth = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes(StandardCharsets.UTF_8));

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder()
                .defaultHeader("Authorization", "Basic " + basicAuth)
                .build();
    }
}
