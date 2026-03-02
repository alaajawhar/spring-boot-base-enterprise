package com.amdose.base.devportal.restTemplateSaver;

import com.amdose.base.devportal.cachdata.ApiModelCircularMap;
import com.amdose.base.devportal.models.ApiModel;
import com.amdose.base.devportal.utils.DateUtil;
import com.amdose.base.devportal.utils.StreamUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author Alaa Jawhar
 */
@Slf4j
@Service
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    @SneakyThrows
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ApiModel thirdPartyApiModel = new ApiModel();

        // Log the request
        thirdPartyApiModel.setEndpoint(request.getURI().getPath());
        thirdPartyApiModel.setFullPath(request.getURI().toString());
        thirdPartyApiModel.setHttpMethod(request.getMethodValue());
        thirdPartyApiModel.setId(String.valueOf(new Date().getTime()));
        thirdPartyApiModel.setCurl("Under Development");
        thirdPartyApiModel.setRequestBody(new String(body, StandardCharsets.UTF_8));
        thirdPartyApiModel.setRequestDate(DateUtil.format(new Date()));

        // Execute http request
        ClientHttpResponse response = execution.execute(request, body);

        // Read the response body into a byte array
        byte[] responseBodyAsBytes = StreamUtil.inputStreamToBytes(response.getBody());

        // Log the response
        thirdPartyApiModel.setResponseBody(new String(responseBodyAsBytes, StandardCharsets.UTF_8));
        thirdPartyApiModel.setResponseStatusCode(String.valueOf(response.getStatusCode().value()));
        thirdPartyApiModel.setResponseDate(DateUtil.format(new Date()));

        ApiModelCircularMap.getCurrentApiModel().addThirdPartyApiModel(thirdPartyApiModel);
        return new ClientHttpResponse() {
            @Override
            public HttpStatusCode getStatusCode() throws IOException {
                return response.getStatusCode();
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return response.getRawStatusCode();
            }

            @Override
            public String getStatusText() throws IOException {
                return response.getStatusText();
            }

            @Override
            public void close() {
                response.close();
            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(responseBodyAsBytes);
            }

            @Override
            public HttpHeaders getHeaders() {
                return response.getHeaders();
            }
        };
    }
}
