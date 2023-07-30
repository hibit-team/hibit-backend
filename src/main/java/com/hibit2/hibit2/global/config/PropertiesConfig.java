package com.hibit2.hibit2.global.config;

import com.hibit2.hibit2.global.config.properties.GoogleProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GoogleProperties.class)
public class PropertiesConfig {
}
