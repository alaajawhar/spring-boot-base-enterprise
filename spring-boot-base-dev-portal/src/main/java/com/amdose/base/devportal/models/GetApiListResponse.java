package com.amdose.base.devportal.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alaa Jawhar
 */
@Data
public class GetApiListResponse {
    private Integer totalCount;
    private List<GetApiListItem> list;

    public void addApiItem(GetApiListItem apiItem) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(apiItem);
    }
}
