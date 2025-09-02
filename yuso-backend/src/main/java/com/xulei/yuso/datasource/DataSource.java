package com.xulei.yuso.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据源接口
 * <p>
 * 新接入的数据源必须实现
 *
 * @param <T>
 */
public interface DataSource<T> {

    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}
