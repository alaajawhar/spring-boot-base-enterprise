package com.amdose.base.devportal.models;

import lombok.Data;

/**
 * @author Alaa Jawhar
 */
@Data
public class JdbcApiModel {
    private String packageName;
    private String jdbcTemplateFunctionName;
    private String jdbcTemplateArgs;
    private String jdbcTemplateResult;
}
