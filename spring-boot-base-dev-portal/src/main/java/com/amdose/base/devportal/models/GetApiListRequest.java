package com.amdose.base.devportal.models;

import lombok.Data;

/**
 * @author Alaa Jawhar
 */
@Data
public class GetApiListRequest {
    private Integer offset = 0;
    private Integer count = 10;
    private GetApiListFilter filter;
}
