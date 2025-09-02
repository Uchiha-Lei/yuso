package com.xulei.yuso.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xulei.yuso.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合搜索
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class SearchVO implements Serializable {

    private List<PostVO> postList;

    private List<Picture> pictureList;

    private List<UserVO> userList;

    private List<?> dataList;

    private static final long serialVersionUID = 1L;
}
