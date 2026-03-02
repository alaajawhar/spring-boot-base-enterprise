package com.amdose.base.devportal.models;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Alaa Jawhar
 */
@Data
public class GetApiListFilter {
    private String id;
    private RequestMethod httpMethod;
    private String endpoint;
    private Integer responseCode;
}
