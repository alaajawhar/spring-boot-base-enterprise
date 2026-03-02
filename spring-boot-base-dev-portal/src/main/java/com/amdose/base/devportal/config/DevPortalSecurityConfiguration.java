package com.amdose.base.devportal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Dev Portal Security Configuration
 *
 * This configuration provides security rules for dev-portal and common documentation endpoints
 * (Swagger UI, OpenAPI docs) that should be accessible without authentication.
 *
 * Key Features:
 * - Runs with high priority (@Order(1)) to ensure dev-portal paths are permitted early
 * - Only activates when Spring Security is present (@ConditionalOnClass)
 * - Uses AntPathRequestMatcher for static resources
 * - Permits access to: /dev-portal/**, /swagger-ui/**, /v3/api-docs/**, /webjars/**
 *
 * Microservices can still define their own SecurityFilterChain beans for other paths.
 * This configuration only handles dev-portal and documentation paths.
 *
 * @author Alaa Jawhar
 */
@Configuration
@ConditionalOnClass(name = "org.springframework.security.config.annotation.web.builders.HttpSecurity")
@ConditionalOnWebApplication
public class DevPortalSecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DevPortalSecurityConfiguration.class);

    /**
     * Security filter chain for dev-portal and documentation endpoints
     * Runs with HIGHEST priority (Order 0) to permit these paths before ANY other security rules
     * This ensures dev-portal works without requiring microservices to configure anything
     */
    @Bean
    @Order(0) // Highest priority - runs BEFORE any microservice security configs
    public SecurityFilterChain devPortalSecurityFilterChain(HttpSecurity http) throws Exception {
        logger.info("=================================================================");
        logger.info("Configuring Dev Portal Security Filter Chain (Order 0 - Highest Priority)");
        logger.info("=================================================================");

        http
                // Only apply this security configuration to dev-portal and documentation paths
                .securityMatcher(
                        "/dev-portal/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                )
                // Disable CSRF for dev-portal API endpoints (they are stateless REST APIs)
                .csrf(csrf -> csrf.disable())
                // Disable session creation for dev-portal (stateless)
                .sessionManagement(session ->
                    session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS)
                )
                // Permit all requests matching the security matchers above
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        logger.info("✅ Dev Portal Security Configuration Active");
        logger.info("   The following paths are now PUBLIC (no authentication required):");
        logger.info("   • /dev-portal/** (Dev Portal UI and REST API endpoints)");
        logger.info("   • /swagger-ui/** (Swagger UI resources)");
        logger.info("   • /swagger-ui.html (Swagger UI main page)");
        logger.info("   • /v3/api-docs/** (OpenAPI documentation)");
        logger.info("   • /swagger-resources/** (Swagger resources)");
        logger.info("   • /webjars/** (WebJar static resources)");
        logger.info("=================================================================");

        return http.build();
    }
}
