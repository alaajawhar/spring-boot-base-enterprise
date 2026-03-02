package com.amdose.base.devportal.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alaa Jawhar
 */
public class UrlConstants {

    public static final String GET_API_LIST = "/dev-portal/api/list";
    public static final String GET_API_BY_ID = "/dev-portal/api/id";
    public static final String CLEAR_API_LIST = "/dev-portal/api/clear";

    public static final List<String> IGNORED_API_LIST = new ArrayList<>();

    static {
        IGNORED_API_LIST.add(GET_API_LIST);
        IGNORED_API_LIST.add(GET_API_BY_ID);
        IGNORED_API_LIST.add(CLEAR_API_LIST);
    }
}
