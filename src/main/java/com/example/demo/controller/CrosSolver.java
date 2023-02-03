package com.example.demo.controller;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CrosSolver implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry reg) {
        reg.addMapping("/**")
                .allowedOrigins("https://containers-us-west-145.railway.app:6046/")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);

    }
}
