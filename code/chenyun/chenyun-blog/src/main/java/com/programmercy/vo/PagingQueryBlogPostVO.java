package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description: 分页查询博文列表VO
 * Created by 爱吃小鱼的橙子 on 2024-12-02 10:27
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingQueryBlogPostVO extends PageInfoVO {

    /**
     * 博文列表
     */
    List<BlogPostVO> blogPostList;

}
