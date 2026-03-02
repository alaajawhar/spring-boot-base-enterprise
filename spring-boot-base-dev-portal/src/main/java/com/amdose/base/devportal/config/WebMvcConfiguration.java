package com.amdose.base.devportal.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Dev Portal Web MVC Configuration
 *
 * This configuration adds dev-portal specific resource handlers and view controllers
 * without interfering with Spring Boot's auto-configuration or existing WebMvcConfigurer beans.
 *
 * Key Features:
 * - Only configures /dev-portal/** paths
 * - Does not use @EnableWebMvc (preserves Spring Boot auto-config)
 * - Does not override global CORS (microservices should handle their own CORS)
 * - Chains with existing WebMvcConfigurer beans via Spring's composition
 *
 * @author Alaa Jawhar
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);

    /**
     * Add resource handlers for dev-portal static files
     * This only adds the /dev-portal/** handler and preserves all other resource handlers
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Add dev-portal static resources
        // These are served from classpath:/dev-portal-static/
        registry.addResourceHandler("/dev-portal/**")
                .addResourceLocations("classpath:/dev-portal-static/")
                .setCachePeriod(0); // No caching for development

        logger.debug("Dev Portal resource handler registered for /dev-portal/**");
    }

    /**
     * Add view controllers for dev-portal Angular SPA routing
     * Redirects root dev-portal paths to index.html for client-side routing
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirect /dev-portal to /dev-portal/index.html for Angular SPA
        registry.addRedirectViewController("/dev-portal", "/dev-portal/index.html");
        registry.addRedirectViewController("/dev-portal/", "/dev-portal/index.html");

        // Redirect Angular routes to index.html (Angular handles client-side routing)
        registry.addRedirectViewController("/dev-portal/api-list", "/dev-portal/index.html");

        logger.debug("Dev Portal view controllers registered");
    }

    @PostConstruct
    public void webConfigInit() {
        logger.info("Dev Portal WebMvcConfiguration initialized");
        logger.info("Dev Portal UI will be available at: /dev-portal/");
    }
}
