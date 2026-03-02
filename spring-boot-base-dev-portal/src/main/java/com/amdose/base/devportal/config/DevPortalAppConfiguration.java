package com.amdose.base.devportal.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * @author Alaa Jawhar
 */
@Configuration
@EnableAspectJAutoProxy
@Slf4j
public class DevPortalAppConfiguration {

    @PostConstruct
    public void init() {
        System.out.println("Initializing Debugging Portal App....");
    }
}
