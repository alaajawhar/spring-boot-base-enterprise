package com.amdose.base.devportal.cachdata;

import com.amdose.base.devportal.constants.AppConstants;
import com.amdose.base.devportal.models.ApiModel;
import org.slf4j.MDC;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Alaa Jawhar
 */
public class ApiModelCircularMap {

    private static LinkedHashMap<String, ApiModel> map = new CircularLinkedMap(AppConstants.MAX_TRX);

    public static ApiModel getCurrentApiModel() {
        String transactionId = MDC.get(AppConstants.TRX_ID);
        return get(transactionId);
    }

    public static ApiModel get(String apiModelKey) {
        synchronized (map) {
            return map.get(apiModelKey);
        }
    }

    public static boolean isExists(String apiModelKey) {
        synchronized (map) {
            return map.get(apiModelKey) != null;
        }
    }

    public static void add(ApiModel apiModel) {
        String transactionId = MDC.get(AppConstants.TRX_ID);

        synchronized (map) {
            if (isExists(transactionId)) {
                return;
            }
            map.put(transactionId, apiModel);
        }
    }

    public static Map<String, ApiModel> getAllMap() {
        synchronized (map) {
            return map;
        }
    }

    public static void clear() {
        synchronized (map) {
            map.clear();
        }
    }
}
