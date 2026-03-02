package com.amdose.base.devportal.models;

import lombok.Data;

/**
 * @author Alaa Jawhar
 */
@Data
public class GetApiListItem {
    private String id;
    private String endpoint;
    private String date;
    private String httpMethod;
    private String responseStatusCode;
}
