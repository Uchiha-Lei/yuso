package com.xulei.yuso.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xulei.yuso.common.BaseResponse;
import com.xulei.yuso.common.ErrorCode;
import com.xulei.yuso.common.ResultUtils;
import com.xulei.yuso.exception.ThrowUtils;
import com.xulei.yuso.model.dto.picture.PictureQueryRequest;
import com.xulei.yuso.model.entity.Picture;
import com.xulei.yuso.model.entity.Post;
import com.xulei.yuso.service.PictureService;
import com.xulei.yuso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;

    @Resource
    private UserService userService;

    /**
     * 分页获取列表（封装类）
     *
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureVOByPage(@RequestBody PictureQueryRequest pictureQueryRequest, HttpServletRequest request) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        String searchText = pictureQueryRequest.getSearchText();
        Page<Picture> picturePage = pictureService.searchPicture(searchText, current, size);
        return ResultUtils.success(picturePage);
    }


}
