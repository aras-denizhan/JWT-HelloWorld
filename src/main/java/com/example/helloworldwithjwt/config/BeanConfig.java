package com.example.helloworldwithjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.logging.Logger;


@Configuration
public class BeanConfig {

    @Bean
    public BCryptPasswordEncoder getBcryptEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Logger getLogger(){
        return Logger.getLogger(this.getClass().getName());
    }
}
