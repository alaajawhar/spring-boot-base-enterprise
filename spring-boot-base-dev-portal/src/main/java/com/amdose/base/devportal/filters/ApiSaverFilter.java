package com.amdose.base.devportal.filters;

import com.amdose.base.devportal.cachdata.ApiModelCircularMap;
import com.amdose.base.devportal.constants.AppConstants;
import com.amdose.base.devportal.constants.UrlConstants;
import com.amdose.base.devportal.models.ApiModel;
import com.amdose.base.devportal.utils.DateUtil;
import com.amdose.base.devportal.utils.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Enumeration;

/**
 * @author Alaa Jawhar
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiSaverFilter implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Skip ignored URLs
        if (UrlConstants.IGNORED_API_LIST.contains(httpRequest.getServletPath()) ||
            httpRequest.getServletPath().contains("/dev-portal")) {
            chain.doFilter(request, response);
            return;
        }

        // Wrap request and response to cache their bodies
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpResponse);

        // Generate transaction ID
        String transactionId = String.valueOf(new Date().getTime());
        MDC.put(AppConstants.TRX_ID, transactionId);
        // Create ApiModel
        ApiModel apiModel = new ApiModel();
        apiModel.setId(transactionId);
        // Store in circular map
        ApiModelCircularMap.add(apiModel);

        try {
            // Execute the filter chain
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            // Capture request and response data
            captureRequestResponse(requestWrapper, responseWrapper);

            // Copy response body back to the original response
            responseWrapper.copyBodyToResponse();
        }
    }

    private void captureRequestResponse(ContentCachingRequestWrapper requestWrapper,
                                         ContentCachingResponseWrapper responseWrapper) {
        try {
            String transactionId = MDC.get(AppConstants.TRX_ID);

            // Parse request body
            Object requestBody = parseRequestBody(requestWrapper);
            ApiModel apiModel = ApiModelCircularMap.get(transactionId);

            // Parse request body
            apiModel.setHttpMethod(requestWrapper.getMethod());
            apiModel.setEndpoint(requestWrapper.getRequestURI());
            apiModel.setCurl(requestToCurl(requestWrapper, requestBody));
            apiModel.setRequestBody(requestBody);
            apiModel.setRequestDate(DateUtil.format(new Date()));

            // Parse response body
            Object responseBody = parseResponseBody(responseWrapper);
            apiModel.setResponseBody(responseBody);
            apiModel.setResponseDate(DateUtil.format(new Date()));
            apiModel.setResponseStatusCode(String.valueOf(responseWrapper.getStatus()));
        } catch (Exception e) {
            log.error("Error capturing request/response", e);
        } finally {
            MDC.remove(AppConstants.TRX_ID);
        }
    }

    private Object parseRequestBody(ContentCachingRequestWrapper requestWrapper) {
        byte[] content = requestWrapper.getContentAsByteArray();
        if (content.length > 0) {
            try {
                String bodyString = new String(content, StandardCharsets.UTF_8);
                return objectMapper.readValue(bodyString, Object.class);
            } catch (Exception e) {
                // If parsing fails, return raw string
                return new String(content, StandardCharsets.UTF_8);
            }
        }
        return null;
    }

    private Object parseResponseBody(ContentCachingResponseWrapper responseWrapper) {
        byte[] content = responseWrapper.getContentAsByteArray();
        if (content.length > 0) {
            try {
                String bodyString = new String(content, StandardCharsets.UTF_8);
                return objectMapper.readValue(bodyString, Object.class);
            } catch (Exception e) {
                // If parsing fails, return raw string
                return new String(content, StandardCharsets.UTF_8);
            }
        }
        return null;
    }

    private static String requestToCurl(HttpServletRequest request, Object requestBody) {
        StringBuilder result = new StringBuilder();

        result.append("curl --location --request ");

        // output method
        result.append(request.getMethod()).append(" ");

        // output url
        result.append("\"").append(request.getRequestURL().toString()).append("\"");

        // output headers
        for (Enumeration<String> headerNames = request.getHeaderNames(); headerNames.hasMoreElements(); ) {
            String headerName = headerNames.nextElement();
            result.append(" -H \"").append(headerName).append(": ").append(request.getHeader(headerName)).append("\"");
        }

        // output parameters
        for (Enumeration<String> parameterNames = request.getParameterNames(); parameterNames.hasMoreElements(); ) {
            String parameterName = parameterNames.nextElement();
            result.append(" -d \"").append(parameterName).append("=").append(request.getParameter(parameterName)).append("\"");
        }

        // output body
        if (RequestMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
            String body = JsonUtil.convertToString(requestBody);
            if (body.length() > 0) {
                result.append(" -d \'").append(body).append("\'");
            }
        }

        return result.toString();
    }
}