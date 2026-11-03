package com.etitc.medirecordatorio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login",
                        "/registro",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/error",
                        "/error/**",
                        "/probar-error-500",
                        "/probar-error-400",
                        "/api/**" // Permite consumir la API REST desde Postman sin redirecciones
                );
    }
}