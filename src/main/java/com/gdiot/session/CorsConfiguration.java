package com.gdiot.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ZhouHR
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    public CorsConfiguration() {
        System.out.println("===============================CorsConfiguration=========================");
    }

    /**
     * 关键代码
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**").allowCredentials(false).allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE").allowedOrigins("*");

    }

}
