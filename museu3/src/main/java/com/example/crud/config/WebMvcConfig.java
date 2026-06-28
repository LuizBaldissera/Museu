package com.example.crud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final VisitaInterceptor visitaInterceptor;
    public WebMvcConfig(VisitaInterceptor visitaInterceptor) { this.visitaInterceptor = visitaInterceptor; }
    @Override
    public void addInterceptors(InterceptorRegistry registry) { registry.addInterceptor(visitaInterceptor); }
}
