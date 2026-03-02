package com.amdose.base.devportal.models;

import lombok.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alaa Jawhar
 */
@Data
public class ApiModel {

    private String id;
    private String httpMethod;
    private String fullPath;
    private String endpoint;
    private String curl;

    private String requestDate;
    private Object requestBody;

    private String responseDate;
    private Object responseBody;
    private String responseStatusCode = "Error";
    private String exceptionStackTrace;
    private List<JdbcApiModel> jdbcApiModelList;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    private StringBuilder logs = new StringBuilder("");

    public String getLogs() {
        return this.logs.toString();
    }

    private List<ApiModel> thirdPartyApiModelList;

    public void addThirdPartyApiModel(ApiModel thirdPartyApiModel) {
        if (this.thirdPartyApiModelList == null) {
            this.thirdPartyApiModelList = new ArrayList<>();
        }
        this.thirdPartyApiModelList.add(thirdPartyApiModel);
    }

    public void addJdbcApiModel(JdbcApiModel jdbcApiModel) {
        if (this.jdbcApiModelList == null) {
            this.jdbcApiModelList = new ArrayList<>();
        }
        this.jdbcApiModelList.add(jdbcApiModel);
    }

    public void setExceptionStackTrace(final Throwable throwable) {
        this.exceptionStackTrace = stackTraceAsString(throwable);
    }

    private static String stackTraceAsString(final Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public void appendLog(String log) {
        this.logs.append(log);
    }
}
