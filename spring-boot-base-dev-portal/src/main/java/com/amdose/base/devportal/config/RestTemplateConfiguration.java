package com.amdose.base.devportal.config;

import com.amdose.base.devportal.restTemplateSaver.RestTemplateInterceptor;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alaa Jawhar
 */
@Configuration
public class RestTemplateConfiguration {

    @Autowired(required = false)
    private RestTemplate restTemplate;
    @Autowired
    private RestTemplateInterceptor restTemplateInterceptor;

    @PostConstruct
    public void setRestTemplate() {
        System.out.println("Setting Rest Template Interceptor...");
        if (restTemplate == null) {
            System.out.println("Rest Template is null");
            return;
        }

        List<ClientHttpRequestInterceptor> interceptors
                = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(restTemplateInterceptor);
        restTemplate.setInterceptors(interceptors);
    }

}
