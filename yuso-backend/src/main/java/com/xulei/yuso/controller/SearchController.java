package com.xulei.yuso.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xulei.yuso.common.BaseResponse;
import com.xulei.yuso.common.ErrorCode;
import com.xulei.yuso.common.ResultUtils;
import com.xulei.yuso.exception.ThrowUtils;
import com.xulei.yuso.model.dto.picture.PictureQueryRequest;
import com.xulei.yuso.model.dto.post.PostQueryRequest;
import com.xulei.yuso.model.dto.search.SearchQueryRequest;
import com.xulei.yuso.model.dto.user.UserQueryRequest;
import com.xulei.yuso.model.entity.Picture;
import com.xulei.yuso.model.vo.PostVO;
import com.xulei.yuso.model.vo.SearchVO;
import com.xulei.yuso.model.vo.UserVO;
import com.xulei.yuso.service.PictureService;
import com.xulei.yuso.service.PostService;
import com.xulei.yuso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.SearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private UserService userService;

    @Resource
    private PictureService pictureService;

    @Resource
    private PostService postService;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchQueryRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();

        Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);

        SearchVO searchVO = new SearchVO();
        searchVO.setUserList(userVOPage.getRecords());
        searchVO.setPostList(postVOPage.getRecords());
        searchVO.setPictureList(picturePage.getRecords());

        return ResultUtils.success(searchVO);
    }


}
