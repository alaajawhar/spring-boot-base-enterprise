package com.amdose.base.devportal.services;

import com.amdose.base.devportal.cachdata.ApiModelCircularMap;
import com.amdose.base.devportal.models.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alaa Jawhar
 */
@Service
@Slf4j
public class DevPortalAppService {

    public GetApiListResponse getApiList(GetApiListRequest getApiListRequest) {
        GetApiListResponse getApiListResponse = new GetApiListResponse();
        Stream<ApiModel> baseStream = ApiModelCircularMap.getAllMap().values().stream();

        if (getApiListRequest.getFilter() != null) {
            if (StringUtils.isEmpty(getApiListRequest.getFilter().getId()) == Boolean.FALSE) {
                baseStream = baseStream.filter(apiModel -> getApiListRequest.getFilter().getId().equalsIgnoreCase(apiModel.getId()));
            }

            if (StringUtils.isEmpty(getApiListRequest.getFilter().getEndpoint()) == Boolean.FALSE) {
                baseStream = baseStream.filter(apiModel -> apiModel.getEndpoint().contains(getApiListRequest.getFilter().getEndpoint()));
            }

            if (getApiListRequest.getFilter().getHttpMethod() != null) {
                baseStream = baseStream.filter(apiModel -> apiModel.getHttpMethod().equalsIgnoreCase(getApiListRequest.getFilter().getHttpMethod().name()));
            }

            if (getApiListRequest.getFilter().getResponseCode() != null) {
                baseStream = baseStream.filter(apiModel -> apiModel.getResponseStatusCode().equalsIgnoreCase(String.valueOf(getApiListRequest.getFilter().getResponseCode())));
            }
        }

        List<ApiModel> apiModelList = baseStream
                .sorted(Comparator.comparing(ApiModel::getRequestDate).reversed())
                .collect(Collectors.toList());

        getApiListResponse.setTotalCount(apiModelList.size());

        apiModelList = apiModelList.stream()
                .skip(getApiListRequest.getOffset())
                .limit(getApiListRequest.getCount())
                .collect(Collectors.toList());

        for (int i = 0; i < apiModelList.size(); i++) {
            GetApiListItem getApiListItem = new GetApiListItem();
            getApiListItem.setId(apiModelList.get(i).getId());
            getApiListItem.setEndpoint(apiModelList.get(i).getEndpoint());
            getApiListItem.setDate(apiModelList.get(i).getRequestDate());
            getApiListItem.setResponseStatusCode(apiModelList.get(i).getResponseStatusCode());
            getApiListItem.setHttpMethod(apiModelList.get(i).getHttpMethod());
            getApiListResponse.addApiItem(getApiListItem);
        }

        return getApiListResponse;
    }

    public ApiModel getApiById(GetApiByIdRequest getApiByIdRequest) {
        return ApiModelCircularMap.getAllMap().get(getApiByIdRequest.getId());
    }

    public ClearApiListResponse clearApiList(ClearApiListRequest clearApiListRequest) {
        ApiModelCircularMap.clear();
        ClearApiListResponse response = new ClearApiListResponse();
        response.setSuccess(true);
        response.setMessage("API list cleared successfully");
        return response;
    }
}
