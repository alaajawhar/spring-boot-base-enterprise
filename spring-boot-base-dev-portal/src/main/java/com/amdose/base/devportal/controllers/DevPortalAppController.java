package com.amdose.base.devportal.controllers;

import com.amdose.base.devportal.constants.UrlConstants;
import com.amdose.base.devportal.models.*;
import com.amdose.base.devportal.services.DevPortalAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alaa Jawhar
 */
@RestController
@RequiredArgsConstructor
public class DevPortalAppController {

    private final DevPortalAppService devPortalAppService;

    @RequestMapping(value = UrlConstants.GET_API_LIST,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public GetApiListResponse getApiList(@RequestBody GetApiListRequest getApiListRequest) {
        return devPortalAppService.getApiList(getApiListRequest);
    }

    @RequestMapping(value = UrlConstants.GET_API_BY_ID,
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiModel getApiByID(@RequestBody GetApiByIdRequest getApiByIdRequest) {
        return devPortalAppService.getApiById(getApiByIdRequest);
    }

    @RequestMapping(value = UrlConstants.CLEAR_API_LIST,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ClearApiListResponse clearApiList(@RequestBody ClearApiListRequest clearApiListRequest) {
        return devPortalAppService.clearApiList(clearApiListRequest);
    }

}
