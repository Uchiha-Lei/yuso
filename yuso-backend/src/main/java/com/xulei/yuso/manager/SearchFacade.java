package com.xulei.yuso.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xulei.yuso.common.ErrorCode;
import com.xulei.yuso.datasource.*;
import com.xulei.yuso.exception.BusinessException;
import com.xulei.yuso.exception.ThrowUtils;
import com.xulei.yuso.model.dto.post.PostQueryRequest;
import com.xulei.yuso.model.dto.search.SearchQueryRequest;
import com.xulei.yuso.model.dto.user.UserQueryRequest;
import com.xulei.yuso.model.entity.Picture;
import com.xulei.yuso.model.enums.SearchTypeEnum;
import com.xulei.yuso.model.vo.PostVO;
import com.xulei.yuso.model.vo.SearchVO;
import com.xulei.yuso.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 搜索门面模式
 */
@Component
@Slf4j
public class SearchFacade {

    @Resource
    private PostDataSource postDataSource;
    @Resource
    private UserDataSource userDataSource;
    @Resource
    private PictureDataSource pictureDataSource;
    @Resource
    private DataSourceRegister dataSourceRegister;

    public SearchVO searchAll(@RequestBody SearchQueryRequest searchRequest, HttpServletRequest request) {
        // 搜索词
        String searchText = searchRequest.getSearchText();
        // 获取到查询的类型
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        long current = searchRequest.getCurrent();
        long size = searchRequest.getPageSize();

        // 搜索出所有数据
        if (searchTypeEnum == null) {
            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, size);
                return userVOPage;
            });
            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, size);
                return postVOPage;
            });
            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureDataSource.doSearch(searchText, 1, 10);
                return picturePage;
            });

            CompletableFuture.allOf(userTask, postTask, pictureTask).join();
            try {
                Page<UserVO> userVOPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                Page<Picture> picturePage = pictureTask.get();
                SearchVO searchVO = new SearchVO();
                searchVO.setUserList(userVOPage.getRecords());
                searchVO.setPostList(postVOPage.getRecords());
                searchVO.setPictureList(picturePage.getRecords());
                return searchVO;
            } catch (Exception e) {
                log.error("查询异常:::", e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }
        } else {
            // 注册器模式
            SearchVO searchVO = new SearchVO();
            DataSource<?> dataSource = dataSourceRegister.getDataSourceByType(type);
            Page<?> page = dataSource.doSearch(searchText, current, size);
            searchVO.setDataList(page.getRecords());
            return searchVO;
        }
    }
}


/*
    普通写法
    switch (searchTypeEnum) {
         case POST:
             PostQueryRequest postQueryRequest = new PostQueryRequest();
             postQueryRequest.setSearchText(searchText);
             Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
             searchVO.setPostList(postVOPage.getRecords());
             break;
        case USER:
             UserQueryRequest userQueryRequest = new UserQueryRequest();
             userQueryRequest.setUserName(searchText);
             Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
             searchVO.setUserList(userVOPage.getRecords());
             break;
        case PICTURE:
             Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
             searchVO.setPictureList(picturePage.getRecords());
             break;
         default:
     }
    return searchVO;
 */

/*
    适配器模式
    SearchVO searchVO = new SearchVO();
            DataSource dataSource = null;
            switch (searchTypeEnum) {
                case POST:
                    dataSource = new PostDataSource();
                    break;
                case USER:
                    dataSource = new UserDataSource();
                    break;
                case PICTURE:
                    dataSource = new PictureDataSource();
                    break;
            }
     dataSource.doSearch(searchText, current, size);
     return searchVO;

*/