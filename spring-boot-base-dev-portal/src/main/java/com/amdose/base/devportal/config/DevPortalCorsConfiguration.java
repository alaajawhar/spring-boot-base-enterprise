package com.amdose.base.devportal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Optional CORS Configuration for Dev Portal
 *
 * This provides a permissive default CORS configuration that microservices can use.
 * It only activates if:
 * 1. No other CorsConfigurationSource bean is defined (via @ConditionalOnMissingBean)
 * 2. The property 'dev-portal.cors.enabled' is set to true (optional, defaults to false)
 *
 * To enable this default CORS in your microservice, add to application.yaml:
 *   dev-portal:
 *     cors:
 *       enabled: true
 *
 * To use custom CORS, simply define your own CorsConfigurationSource bean in your microservice,
 * and this bean will not be created.
 *
 * @author Alaa Jawhar
 */
@Configuration
public class DevPortalCorsConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DevPortalCorsConfiguration.class);

    /**
     * Provides a default permissive CORS configuration
     * Only created if no other CorsConfigurationSource exists and dev-portal.cors.enabled=true
     */
    @Bean
    @ConditionalOnMissingBean(CorsConfigurationSource.class)
    @ConditionalOnProperty(name = "dev-portal.cors.enabled", havingValue = "true", matchIfMissing = false)
    public CorsConfigurationSource corsConfigurationSource() {
        logger.info("Creating default Dev Portal CORS configuration (permissive)");
        logger.warn("Dev Portal default CORS allows all origins - consider configuring specific origins for production");

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // Allow all origins
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
