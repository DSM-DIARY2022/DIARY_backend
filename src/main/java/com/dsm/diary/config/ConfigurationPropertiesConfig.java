package com.dsm.diary.config;

import com.dsm.diary.security.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(value = {JwtProperties.class})
@Configuration
public class ConfigurationPropertiesConfig {
}
