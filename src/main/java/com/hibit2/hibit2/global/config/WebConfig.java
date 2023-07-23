package com.hibit2.hibit2.global.config;

import java.util.List;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final List<String> allowOriginUrlPatterns;
    private final HandlerMethodArgumentResolver authenticationPrincipalArgumentResolver;

    public WebConfig(@Value("${cors.allow-origin.urls}") final List<String> allowOriginUrlPatterns,
        final HandlerMethodArgumentResolver authenticationPrincipalArgumentResolver) {
        this.allowOriginUrlPatterns = allowOriginUrlPatterns;
        this.authenticationPrincipalArgumentResolver = authenticationPrincipalArgumentResolver;
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String [] patterns = allowOriginUrlPatterns.stream()
                .toArray(String[]::new);

        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOriginPatterns(patterns);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> {
            builder.modulesToInstall(new Hibernate5Module());
        };
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authenticationPrincipalArgumentResolver);
    }
}