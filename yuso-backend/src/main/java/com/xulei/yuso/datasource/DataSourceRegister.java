package com.xulei.yuso.datasource;

import com.xulei.yuso.model.enums.SearchTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceRegister {
    @Resource
    private PostDataSource postDataSource;
    @Resource
    private UserDataSource userDataSource;
    @Resource
    private PictureDataSource pictureDataSource;
    @Resource
    private Map<String, DataSource<?>> typeDataSourceMap;

    @PostConstruct
    public void init() {
        // 初始化后加载
        typeDataSourceMap = new HashMap() {
            {
                put(SearchTypeEnum.POST.getValue(), postDataSource);
                put(SearchTypeEnum.USER.getValue(), userDataSource);
                put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
            }
        };
    }

    public DataSource<?> getDataSourceByType(String type) {
        if (typeDataSourceMap == null) {
            return null;
        }
        return typeDataSourceMap.get(type);
    }

}