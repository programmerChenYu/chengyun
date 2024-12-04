package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 分页搜索博文
 * Created by 爱吃小鱼的橙子 on 2024-12-02 13:53
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingQuerySearchBlogPostVO extends PageInfoVO {

    /**
     * 文章题目
     */
    private String title;
    /**
     * 文章状态
     */
    private String status;

}
