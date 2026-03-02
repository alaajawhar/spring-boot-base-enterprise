package com.amdose.base.devportal.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Alaa Jawhar
 */
@UtilityClass
@Slf4j
public class JsonUtil<T> {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String convertToString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("unable to parse it as json", e);
            return "{\"Error\": \"" + getStackTraceAsString(e) + "\"}";
        }
    }

    @SneakyThrows
    public static <T> T parseJsonToObject(String data, Class<?> target) {
        return (T) OBJECT_MAPPER.readValue(data, target);
    }

    public static String getStackTraceAsString(final Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

}
