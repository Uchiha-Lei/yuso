    package com.xulei.yuso.model.dto.search;

import com.xulei.yuso.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchQueryRequest extends PageRequest implements Serializable {
    /**
     * 搜索词
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}
