package com.xulei.yuso.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xulei.yuso.common.BaseResponse;
import com.xulei.yuso.common.ErrorCode;
import com.xulei.yuso.common.ResultUtils;
import com.xulei.yuso.exception.BusinessException;
import com.xulei.yuso.exception.ThrowUtils;
import com.xulei.yuso.manager.SearchFacade;
import com.xulei.yuso.model.dto.picture.PictureQueryRequest;
import com.xulei.yuso.model.dto.post.PostQueryRequest;
import com.xulei.yuso.model.dto.search.SearchQueryRequest;
import com.xulei.yuso.model.dto.user.UserQueryRequest;
import com.xulei.yuso.model.entity.Picture;
import com.xulei.yuso.model.entity.Post;
import com.xulei.yuso.model.enums.SearchTypeEnum;
import com.xulei.yuso.model.vo.PostVO;
import com.xulei.yuso.model.vo.SearchVO;
import com.xulei.yuso.model.vo.UserVO;
import com.xulei.yuso.service.PictureService;
import com.xulei.yuso.service.PostService;
import com.xulei.yuso.service.UserService;
import io.netty.util.concurrent.CompleteFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 帖子接口
 *
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchQueryRequest searchRequest, HttpServletRequest request) {
        return ResultUtils.success(searchFacade.searchAll(searchRequest, request));
    }
}
